package earth.terrarium.ad_astra.common.block.flag;

import com.mojang.authlib.GameProfile;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
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
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {

    @Nullable
    private GameProfile owner;
    @Nullable
    private String id;

    public FlagBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.FLAG.get(), blockPos, blockState);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        CompoundTag compound = new CompoundTag();
        if (owner != null) {
            NbtUtils.writeGameProfile(compound, owner);
            tag.put("FlagOwner", compound);
        }
        if (id != null) {
            tag.putString("FlagUrl", id);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("FlagOwner", Tag.TAG_COMPOUND)) {
            setOwner(NbtUtils.readGameProfile(tag.getCompound("FlagOwner")));
        }
        if (tag.contains("FlagUrl", Tag.TAG_STRING)) {
            setId(tag.getString("FlagUrl"));
        }
    }

    @Nullable
    public GameProfile getOwner() {
        return owner;
    }

    public void setOwner(GameProfile profile) {
        synchronized (this) {
            owner = profile;
        }
        loadOwnerProperties();
    }

    private void loadOwnerProperties() {
        SkullBlockEntity.updateGameprofile(owner, (owner) -> {
            this.owner = owner;
            setChanged();
        });
    }

    @Nullable
    public String getUrl() {
        if (id == null) {
            return null;
        }
        return "https://i.imgur.com/" + id + ".png";
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
