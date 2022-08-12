package com.github.alexnijjar.ad_astra.blocks.globes;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class GlobeBlockEntity extends BlockEntity {

	public static final float INERTIA = 0.0075f;
	public static final float DECELERATION = 0.00003f;

	private float angularVelocity = 0.0f;
	private float yaw = 0.0f;
	private float cachedYaw = 0.0f;

	public GlobeBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.GLOBE_BLOCK_ENTITY, pos, state);
	}

	public void tick() {
		if (this.getCachedState().get(GlobeBlock.POWERED) && this.getAngularVelocity() <= 0.05f) {
			this.setAngularVelocity(0.05f);
		}

		if (this.getAngularVelocity() > 0) {
			// Simulate an inertia effect.
			this.setAngularVelocity(this.getAngularVelocity() - INERTIA);

			this.setCachedYaw(this.getYaw());
			this.setYaw(this.getYaw() - this.getAngularVelocity());

		} else if (this.getAngularVelocity() < 0) {
			this.setAngularVelocity(0);
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
		this.angularVelocity = nbt.getFloat("angularVelocity");
		this.yaw = nbt.getFloat("yaw");
		this.cachedYaw = nbt.getFloat("cachedYaw");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putFloat("angularVelocity", this.angularVelocity);
		nbt.putFloat("yaw", this.yaw);
		nbt.putFloat("cachedYaw", this.cachedYaw);
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return this.toNbt();
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