package earth.terrarium.adastra.common.utils;

import com.mojang.serialization.Codec;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

import java.util.Locale;
import java.util.Optional;

public final class ModUtils {

    public static <T extends ParticleOptions> void sendParticles(ServerLevel level, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayer player : level.players()) {
            level.sendParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    /**
     * Gets the machine from a menu packet, if the player is within 8 blocks of the machine and has the menu open.
     *
     * @param pos    the position of the machine
     * @param player the player sending the packet
     * @param level  the level the machine is in
     * @return the machine, if the player is within 8 blocks of the machine and has the menu open
     */
    public static Optional<ContainerMachineBlockEntity> getMachineFromMenuPacket(BlockPos pos, Player player, Level level) {
        if (!(player.containerMenu instanceof BaseContainerMenu<?>))
            return Optional.empty(); // ensure the sender has the menu open
        if (player.distanceToSqr(pos.getCenter()) > 64)
            return Optional.empty(); // ensure the sender within 8 blocks of the machine
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ContainerMachineBlockEntity container)) return Optional.empty();
        return Optional.of(container);
    }

    /**
     * Checks if the player is able to teleport to a planet. This is successful if the player is in creative, spectator,
     * OP, or is in a rocket that is above the atmosphere leave config value and with a high enough tier to reach the
     * target planet.
     *
     * @param player       the player to check
     * @param targetPlanet the planet to teleport to
     * @return true if the player can teleport to the planet, false otherwise
     */
    public static boolean canTeleportToPlanet(Player player, Planet targetPlanet) {
        if (!(player.containerMenu instanceof PlanetsMenu)) return false;
        if (player.isCreative() || player.isSpectator() || player.hasPermissions(2)) return true;

        String[] planets = AdAstraConfig.disabledPlanets.split(",");
        for (var planet : planets) {
            if (planet.equals(targetPlanet.dimension().location().toString())) return false;
        }

        if (!(player.getVehicle() instanceof Rocket rocket)) return false;
        if (rocket.getY() < AdAstraConfig.atmosphereLeave) return false;
        return rocket.tier() >= targetPlanet.tier();
    }

    public static <T extends Enum<T>> Codec<T> createEnumCodec(Class<T> enumClass) {
        return Codec.STRING.xmap(s -> Enum.valueOf(enumClass, s.toUpperCase(Locale.ROOT)), Enum::name);
    }

    public static Direction relative(BlockEntity from, Direction to) {
        return relative(from.getBlockState().getValue(MachineBlock.FACING), to);
    }

    public static Direction relative(Direction from, Direction to) {
        if (to.getAxis().isVertical()) return to;
        return switch (from) {
            case EAST -> to.getClockWise();
            case SOUTH -> to.getOpposite();
            case WEST -> to.getCounterClockWise();
            default -> to;
        };
    }

    public static Entity teleportToDimension(Entity entity, ServerLevel level) {
        PortalInfo target = new PortalInfo(entity.position(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
        return PlatformUtils.teleportToDimension(entity, level, target);
    }

    public static void land(ServerPlayer player, ServerLevel targetLevel, Vec3 pos) {
        Entity vehicle = player.getVehicle();
        player.stopRiding();
        player.moveTo(pos);
        var teleportedPlayer = teleportToDimension(player, targetLevel);

        if (!(vehicle instanceof Rocket rocket)) return;
        Lander lander = ModEntityTypes.LANDER.get().create(targetLevel);
        if (lander == null) return;
        lander.setPos(pos);
        targetLevel.addFreshEntity(lander);
        teleportedPlayer.startRiding(lander);

        var rocketInventory = rocket.inventory();
        var landerInventory = lander.inventory();
        for (int i = 0; i < rocketInventory.getContainerSize(); i++) {
            landerInventory.setItem(i + 1, rocketInventory.getItem(i));
        }
        landerInventory.setItem(0, rocket.getDropStack());
        rocket.discard();
    }
}
