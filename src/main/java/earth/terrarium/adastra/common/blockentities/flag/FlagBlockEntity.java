package earth.terrarium.adastra.common.blockentities.flag;

import com.mojang.authlib.GameProfile;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.flag.content.FlagContent;
import earth.terrarium.adastra.common.blockentities.flag.content.UrlContent;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.adastra.mixins.common.SkullBlockEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {

    @Nullable
    private ResolvableProfile owner;

    @Nullable
    private FlagContent content;

    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FLAG.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (owner != null) {
            tag.put("FlagOwner", ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, this.owner).getOrThrow());
        }
        if (content != null) {
            tag.put("FlagContent", content.toFullTag());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("FlagOwner", Tag.TAG_COMPOUND)) {
            ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, tag.get("FlagOwner")).resultOrPartial((s) ->
                AdAstra.LOGGER.error("Failed to load flag owner: {}", s)).ifPresent(this::setOwner);
        }
        if (tag.contains("FlagUrl", Tag.TAG_STRING)) {
            this.content = UrlContent.of("https://imgur.com/" + tag.getString("FlagUrl"));
        }
        if (tag.contains("FlagContent", Tag.TAG_COMPOUND)) {
            this.content = FlagContent.fromTag(tag.getCompound("FlagContent"));
        }
    }

    @Nullable
    public ResolvableProfile getOwner() {
        return this.owner;
    }

    public void setOwner(GameProfile profile) {
        this.setOwner(new ResolvableProfile(profile));
    }

    public void setOwner(ResolvableProfile profile) {
        synchronized (this) {
            this.owner = profile;
        }
        this.loadOwnerProperties();
    }

    private void loadOwnerProperties() {
        if (this.owner != null && !this.owner.isResolved()) {
            this.owner.resolve().thenAcceptAsync((p_332638_) -> {
                this.owner = p_332638_;
                this.setChanged();
            }, SkullBlockEntityInvoker.getCheckedMainThreadExecutor());
        } else {
            this.setChanged();
        }
    }

    @Nullable
    public FlagContent getContent() {
        return this.content;
    }

    public void setContent(@Nullable FlagContent content) {
        this.content = content;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }
}
