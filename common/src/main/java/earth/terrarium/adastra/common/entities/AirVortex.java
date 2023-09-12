package earth.terrarium.adastra.common.entities;

import earth.terrarium.adastra.common.blockentities.machines.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class AirVortex extends Entity {
    public static final int LIFE = 600;

    @Nullable
    private final BlockPos source;
    @Nullable
    private final Set<BlockPos> positions;

    public AirVortex(EntityType<?> type, Level level) {
        super(type, level);
        setLevelCallback(EntityInLevelCallback.NULL);
        this.source = null;
        this.positions = null;
    }

    public AirVortex(Level level, BlockPos source, Set<BlockPos> positions) {
        super(ModEntityTypes.AIR_VORTEX.get(), level);
        setLevelCallback(EntityInLevelCallback.NULL);
        this.source = source;
        this.positions = positions;
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.level() instanceof ServerLevel level)) return;
        if (this.source == null ||
            this.positions == null ||
            this.tickCount >= LIFE ||
            level.getBlockState(this.source).isAir()) {
            this.discard();
            return;
        }
        if (level.getBlockEntity(this.source) instanceof OxygenDistributorBlockEntity e && e.lastDistributedBlocks().size() < OxygenDistributorBlockEntity.MAX_BLOCKS) {
            this.discard();
            return;
        }
        level.getAllEntities().forEach(entity -> {
            if (entity != null && this.positions.contains(entity.blockPosition())) {
                this.applyForce(entity);
            }
        });
    }

    protected void applyForce(Entity entity) {
        if (entity instanceof ServerPlayer player && player.getAbilities().flying) return;
        int time = this.tickCount;
        BlockPos targetPosition = this.blockPosition().below().below();
        double altitude = 1000 * Math.exp(-0.005 * time) + 600;

        double power = Math.max(0.30, Math.pow(altitude, 2) / Math.pow(600, 2));
        Vec3 scale = Vec3.atCenterOf(targetPosition).subtract(entity.position()).normalize().scale(power);
        entity.push(scale.x, scale.y, scale.z);

        if (entity instanceof ServerPlayer player) {
            ModUtils.sendUpdatePacket(player);
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
