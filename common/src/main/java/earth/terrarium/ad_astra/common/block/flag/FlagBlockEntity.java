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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
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
        if (this.owner != null) {
            NbtUtils.writeGameProfile(compound, this.owner);
            tag.put("FlagOwner", compound);
        }
        if (this.id != null) {
            tag.putString("FlagUrl", this.id);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("FlagOwner", Tag.TAG_COMPOUND)) {
            this.setOwner(NbtUtils.readGameProfile(tag.getCompound("FlagOwner")));
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

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
