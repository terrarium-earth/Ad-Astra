package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.blocks.base.BasicEntityBlock;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class MachineBlockEntity extends BlockEntity {
    public MachineBlockEntity(BlockPos pos, BlockState state) {
        this(((BasicEntityBlock) state.getBlock()).entity(state), pos, state);
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tick(Level level, long time, BlockState state, BlockPos pos) {
    }

    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
    }

    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
    }

    public void onRemoved() {
    }

    @SuppressWarnings("DataFlowIssue")
    @NotNull
    public Level level() {
        return super.getLevel();
    }

    public boolean isOn() {
        return this.getBlockState().getValue(MachineBlock.LIT);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void sync() {
        if (this.level() instanceof ServerLevel l) {
            l.getChunkSource().blockChanged(this.worldPosition);
        }
    }
}
