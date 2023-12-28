package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundPlayStationPacket;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RadioBlockEntity extends BlockEntity implements RadioHolder {

    private String station = "";

    public RadioBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.RADIO.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Station", Tag.TAG_STRING)) {
            this.station = tag.getString("Station");

            if (this.level == null) return;
            if (!this.level.isClientSide()) return;
            if (this.station.isBlank()) return;
            RadioHandler.play(this.station, this.level.random, this.worldPosition);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putString("Station", this.station);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull String getRadioUrl() {
        return this.station;
    }

    @Override
    public void setRadioUrl(@NotNull String url) {
        this.station = url;
        if (this.level == null) return;
        NetworkHandler.CHANNEL.sendToPlayersInRange(
            new ClientboundPlayStationPacket(url, this.worldPosition),
            this.level, this.worldPosition, RadioHolder.RANGE
        );
    }
}
