package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.PortalInfo;

import java.util.Optional;

public final class ModUtils {

    public static <T extends ParticleOptions> void sendParticles(ServerLevel level, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayer player : level.players()) {
            level.sendParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    public static Optional<ContainerMachineBlockEntity> getMachineFromMenuPacket(BlockPos pos, Player player, Level level) {
        if (!(player.containerMenu instanceof BaseContainerMenu<?>))
            return Optional.empty(); // ensure the sender has the menu open
        if (player.distanceToSqr(pos.getCenter()) > Mth.square(8))
            return Optional.empty(); // ensure the sender is close enough to the block
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ContainerMachineBlockEntity container)) return Optional.empty();
        return Optional.of(container);
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
}
