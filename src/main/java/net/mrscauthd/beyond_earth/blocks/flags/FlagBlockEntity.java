package net.mrscauthd.beyond_earth.blocks.flags;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.registry.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {

    public static final String FLAG_OWNER_KEY = "FlagOwner";
    @Nullable
    private GameProfile owner;

    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.FLAG_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.owner != null) {
            NbtCompound compound = new NbtCompound();
            NbtHelper.writeGameProfile(compound, this.owner);
            nbt.put(FLAG_OWNER_KEY, compound);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        String string;
        super.readNbt(nbt);
        if (nbt.contains(FLAG_OWNER_KEY, 10)) {
            this.setOwner(NbtHelper.toGameProfile(nbt.getCompound(FLAG_OWNER_KEY)));
        } else if (nbt.contains("ExtraType", 8) && !StringHelper.isEmpty(string = nbt.getString("ExtraType"))) {
            this.setOwner(new GameProfile(null, string));
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Nullable
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
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
}
