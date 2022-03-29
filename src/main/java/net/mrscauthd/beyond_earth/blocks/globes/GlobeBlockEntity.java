package net.mrscauthd.beyond_earth.blocks.globes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    public static final double INERTIA = 0.1;
    public static final double DECELERATION = 0.2;

    private float angularVelocity = 0.0f;

    @Environment(EnvType.CLIENT)
    public float oldYaw = 0.0f;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLOBE_BLOCK_ENTITY, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, GlobeBlockEntity blockEntity) {
        // Simulate an inertia effect.
        if (blockEntity.angularVelocity > 0) {
            blockEntity.angularVelocity -= INERTIA;
            blockEntity.markDirty();
            // Reset the angular velocity if it goes below zero.
        } else if (blockEntity.angularVelocity < 0) {
            blockEntity.angularVelocity = 0;
            blockEntity.markDirty();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.angularVelocity = nbt.getFloat("AngularVelocity");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("AngularVelocity", this.angularVelocity);
    }

    public float getAngularVelocity() {
        return this.angularVelocity;
    }

    public void setAngularVelocity(float value) {
        this.angularVelocity = value;
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
