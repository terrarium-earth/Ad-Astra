package earth.terrarium.ad_astra.blocks.flags;

import com.mojang.authlib.GameProfile;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
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

    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLAG_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (this.owner != null) {
            CompoundTag compound = new CompoundTag();
            NbtUtils.writeGameProfile(compound, this.owner);
            nbt.put("flagOwner", compound);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("flagOwner", 10)) {
            this.setOwner(NbtUtils.readGameProfile(nbt.getCompound("flagOwner")));
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
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}