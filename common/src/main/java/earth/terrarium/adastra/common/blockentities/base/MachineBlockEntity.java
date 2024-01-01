package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.blocks.base.BasicEntityBlock;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class MachineBlockEntity extends BlockEntity implements TickableBlockEntity {
    private boolean initialized;

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        this(((BasicEntityBlock) state.getBlock()).entity(state), pos, state);
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void firstTick(Level level, BlockPos pos, BlockState state) {
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void sync() {
        level().sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    // level getter that's specifically not null so avoid the annoying warning
    @SuppressWarnings("DataFlowIssue")
    @NotNull
    public Level level() {
        return super.getLevel();
    }

    public boolean isLit() {
        return this.getBlockState().getValue(MachineBlock.LIT);
    }

    public void setLit(boolean lit) {
        this.level().setBlock(this.worldPosition, this.getBlockState().setValue(MachineBlock.LIT, lit), 3);
    }

    public boolean isRedstonePowered() {
        return this.getBlockState().getValue(MachineBlock.POWERED);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
