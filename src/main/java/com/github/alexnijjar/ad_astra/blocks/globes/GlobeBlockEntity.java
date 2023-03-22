package com.github.alexnijjar.ad_astra.blocks.globes;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class GlobeBlockEntity extends BlockEntity {

	private float torque;
    private float yaw;
    public float prevYaw;

    public GlobeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.GLOBE_BLOCK_ENTITY, blockPos, blockState);
    }

    public void serverTick() {
        if (getCachedState().get(GlobeBlock.POWERED) && torque <= 3f) {
            torque = 3f;
        }

        if (torque > 0) {
            torque -= 0.75f;
            prevYaw = yaw;
            yaw -= torque;
        } else if (torque < 0) {
            torque = 0;
        }
    }

    public void rotateGlobe() {
        torque = (float) ((MathHelper.PI * 15) / (1 + Math.pow(0.00003f, torque)));
        markDirty();
    }

    public float getYaw() {
        return yaw;
    }

    @Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		torque = tag.getFloat("Torque");
		yaw = tag.getFloat("Yaw");
        prevYaw = yaw;
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		tag.putFloat("Torque", torque);
        tag.putFloat("Yaw", yaw);
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return this.toNbt();
	}
}