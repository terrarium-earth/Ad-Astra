package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class ContainerMachineBlockEntity extends MachineBlockEntity implements BasicContainer, WorldlyContainer, ExtraDataMenuProvider, SideConfigurable {
    private final List<ConfigurationEntry> sideConfig = new ArrayList<>();
    private final NonNullList<ItemStack> items;

    private RedstoneControl redstoneControl = RedstoneControl.ALWAYS_ON;

    public ContainerMachineBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state);
        items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    public void firstTick(Level level, BlockPos pos, BlockState state) {
        super.firstTick(level, pos, state);
        update();
    }

    @Override
    public void internalServerTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (time % 30 == 0 && shouldUpdate()) {
            update();
        }
    }

    public boolean shouldUpdate() {
        return true;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        ConfigurationEntry.load(tag, this.sideConfig, getDefaultConfig());
        this.redstoneControl = RedstoneControl.values()[tag.getByte("RedstoneControl")];
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        ConfigurationEntry.save(tag, this.sideConfig);
        tag.putByte("RedstoneControl", (byte) redstoneControl.ordinal());
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public NonNullList<ItemStack> items() {
        return this.items;
    }

    /**
     * Checks if the machine's redstone configuration allows it to function.
     *
     * @return True if the machine can function, false otherwise.
     */
    public boolean canFunction() {
        return getRedstoneControl().canPower(isRedstonePowered());
    }

    @Override
    public List<ConfigurationEntry> getSideConfig() {
        if (this.sideConfig.isEmpty()) {
            this.sideConfig.addAll(this.getDefaultConfig());
        }
        return sideConfig;
    }

    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter) {}

    public RedstoneControl getRedstoneControl() {
        return redstoneControl;
    }

    public void setRedstoneControl(RedstoneControl redstoneControl) {
        this.redstoneControl = redstoneControl;
        this.setChanged();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return true;
    }
}
