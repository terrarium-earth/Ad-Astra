package earth.terrarium.ad_astra.common.entity;

import earth.terrarium.ad_astra.common.block.machine.entity.OxygenDistributorBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AirVortex extends Entity {
    private final Set<BlockPos> affectedPositions = new HashSet<>();
    private int life = 600;
    protected BlockPos distributorPos = BlockPos.ZERO;

    public AirVortex(EntityType<?> entityType, Level level) {
        super(entityType, level);
        setLevelCallback(EntityInLevelCallback.NULL);
    }

    public AirVortex(Level level, BlockPos distributorPos, Set<BlockPos> affectedPositions) {
        super(ModEntityTypes.AIR_VORTEX.get(), level);
        this.distributorPos = distributorPos;
        this.affectedPositions.addAll(affectedPositions);
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) return;
        life--;
        if (life <= 0 || (level.getBlockEntity(distributorPos) instanceof OxygenDistributorBlockEntity entity && entity.getSources().size() < 2000)) {
            discard();
            return;
        }

        List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(blockPosition()).inflate(20));
        for (Entity entity : entities) {
            if (entity instanceof Player p && p.getAbilities().flying) continue;

            if (!(affectedPositions.contains(entity.blockPosition()))) continue;
            double power = Math.max(0.15, Mth.square(life) / Mth.square(600.0));
            Vec3 scale = Vec3.atCenterOf(blockPosition()).subtract(entity.position()).normalize().scale(power);
            entity.push(scale.x, scale.y, scale.z);
            if (entity instanceof ServerPlayer) {
                // Just a simple way to sync the movement packet
                entity.hurtMarked = true;
            }
        }
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }
}
