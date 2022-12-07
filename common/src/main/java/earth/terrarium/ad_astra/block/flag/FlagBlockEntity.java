package earth.terrarium.ad_astra.block.flag;

import com.mojang.authlib.GameProfile;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {

    @Nullable
    private GameProfile owner;
    @Nullable
    private String id;

    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLAG_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.owner != null) {
            CompoundTag compound = new CompoundTag();
            NbtUtils.writeGameProfile(compound, this.owner);
            tag.put("flagOwner", compound);
        }
        if (this.id != null) {
            tag.putString("FlagUrl", this.id);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("flagOwner", 10)) {
            this.setOwner(NbtUtils.readGameProfile(tag.getCompound("flagOwner")));
        }
        if (tag.contains("FlagUrl", Tag.TAG_STRING)) {
            this.setId(tag.getString("FlagUrl"));
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
        SkullBlockEntity.updateGameprofile(this.owner, (owner) -> {
            this.owner = owner;
            this.setChanged();
        });
    }

    @Nullable
    public String getUrl() {
        if (this.id == null) {
            return null;
        }
        return "https://i.imgur.com/" + this.id + ".png";
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}