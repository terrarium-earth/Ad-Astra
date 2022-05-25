package com.github.alexnijjar.beyond_earth.blocks.globes;

import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlobeBlockEntity extends BlockEntity {

    public static final float INERTIA = 0.0075f;
    public static final float DECELERATION = 0.00003f;

    private float angularVelocity = 0.0f;
    private float yaw = 0.0f;
    private float cachedYaw = 0.0f;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLOBE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GlobeBlockEntity blockEntity) {

        if (state.get(GlobeBlock.POWERED) && blockEntity.getAngularVelocity() <= 0.05f) {
            blockEntity.setAngularVelocity(0.05f);
        }

        if (blockEntity.getAngularVelocity() > 0) {
            // Simulate an inertia effect.
            blockEntity.setAngularVelocity(blockEntity.getAngularVelocity() - INERTIA);

            blockEntity.setCachedYaw(blockEntity.getYaw());
            blockEntity.setYaw(blockEntity.getYaw() - blockEntity.getAngularVelocity());

        } else if (blockEntity.getAngularVelocity() < 0) {
            blockEntity.setAngularVelocity(0);
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();

        // Update renderer.
        if (this.world instanceof ServerWorld serverWorld) {
            serverWorld.getChunkManager().markForUpdate(this.pos);
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

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
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
}