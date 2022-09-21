package com.github.alexnijjar.ad_astra.blocks.flags;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {

	@Nullable
	private GameProfile owner;

	public FlagBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.FLAG_BLOCK_ENTITY.get(), pos, state);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (this.owner != null) {
			NbtCompound compound = new NbtCompound();
			NbtHelper.writeGameProfile(compound, this.owner);
			nbt.put("flagOwner", compound);
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains("flagOwner", 10)) {
			this.setOwner(NbtHelper.toGameProfile(nbt.getCompound("flagOwner")));
		}
	}

	@Nullable
	public GameProfile getOwner() {
		return this.owner;
	}

	public void setOwner(GameProfile profile) {
		synchronized (this) {
			this.owner = profile;
		}
		this.loadOwnerProperties();
	}

	private void loadOwnerProperties() {
		SkullBlockEntity.loadProperties(this.owner, (owner) -> {
			this.owner = owner;
			this.markDirty();
		});
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