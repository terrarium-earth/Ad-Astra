package net.mrscauthd.beyond_earth.blocks.globes;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class GlobeBlockEntity extends BlockEntity {

    // How fast the globe slows down.
    public static final float INERTIA = 0.0075f;
    public static final float DECELERATION = 0.00003f;

    private float angularVelocity = 0.0f;
    private float yaw = 0.0f;
    private float cachedYaw = 0.0f;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLOBE_BLOCK_ENTITY, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, GlobeBlockEntity blockEntity) {

        // Simulate an inertia effect.
        if (blockEntity.getAngularVelocity() > 0) {
            blockEntity.setAngularVelocity(blockEntity.getAngularVelocity() - INERTIA);
            blockEntity.setCachedYaw(blockEntity.getYaw());
            blockEntity.setYaw(blockEntity.getYaw() - blockEntity.getAngularVelocity());
            blockEntity.markDirty();
        } else if (blockEntity.getAngularVelocity() < 0) {
            blockEntity.setAngularVelocity(0);
            blockEntity.markDirty();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.angularVelocity = nbt.getFloat("AngularVelocity");
        this.yaw = nbt.getFloat("Yaw");
        this.cachedYaw = nbt.getFloat("CachedYaw");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("AngularVelocity", this.angularVelocity);
        nbt.putFloat("Yaw", this.yaw);
        nbt.putFloat("CachedYaw", this.cachedYaw);
    }

    public float getAngularVelocity() {
        return this.angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getCachedYaw() {
        return this.cachedYaw;
    }

    public void setCachedYaw(float yaw) {
        this.cachedYaw = yaw;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        // Update renderer.
        if (this.world instanceof ServerWorld world) {
            world.getChunkManager().markForUpdate(this.pos);
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
