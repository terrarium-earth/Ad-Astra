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

    private float rotationalInertia = 0.0f;
    private float yaw = 0.0f;

    public GlobeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLOBE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.rotationalInertia = nbt.getFloat("inertia");
        this.yaw = nbt.getFloat("yaw");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("inertia", this.rotationalInertia);
        nbt.putFloat("yaw", this.yaw);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, GlobeBlockEntity blockEntity) {
        // Decrease inertia.
        if (blockEntity.rotationalInertia > 0) {
            blockEntity.rotationalInertia -= 0.0075f;
            blockEntity.markDirty();
            blockEntity.yaw -= blockEntity.rotationalInertia;
        }
    }

    public float getRotationalInertia() {
        return this.rotationalInertia;
    }

    public void setRotationalInertia(float value) {
        this.rotationalInertia = value;
    }

    public float getYaw() {
        return this.yaw;
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
