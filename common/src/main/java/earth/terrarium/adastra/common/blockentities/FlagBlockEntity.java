package earth.terrarium.adastra.common.blockentities;

import com.mojang.authlib.GameProfile;
import dev.architectury.injectables.annotations.PlatformOnly;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
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
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {

    @Nullable
    private GameProfile owner;
    @Nullable
    private String id;

    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FLAG.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (owner != null) {
            CompoundTag compound = new CompoundTag();
            NbtUtils.writeGameProfile(compound, owner);
            tag.put("FlagOwner", compound);
        }
        if (id != null) {
            tag.putString("FlagUrl", id);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("FlagOwner", Tag.TAG_COMPOUND)) {
            setOwner(NbtUtils.readGameProfile(tag.getCompound("FlagOwner")));
        }
        if (tag.contains("FlagUrl", Tag.TAG_STRING)) {
            setId(tag.getString("FlagUrl"));
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

    public void setId(@Nullable String id) {
        this.id = id;
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
        return "https://i.imgur.com/%s.png".formatted(id);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @PlatformOnly("forge")
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox() {
        return new AABB(this.getBlockPos()).inflate(2);
    }
}
