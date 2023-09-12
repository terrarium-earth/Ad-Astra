package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerMachineBlockEntity extends MachineBlockEntity implements BasicContainer, ExtraDataMenuProvider, SideConfigurable, WorldlyContainer {
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
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        ConfigurationEntry.load(tag, this.sideConfig, getDefaultConfig());
        tag.putByte("RedstoneControl", (byte) redstoneControl.ordinal());
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        ConfigurationEntry.save(tag, this.sideConfig);
        tag.putByte("RedstoneControl", (byte) redstoneControl.ordinal());
    }

    @Override
    public NonNullList<ItemStack> items() {
        return this.items;
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    public void updateFluidSlots() {
    }

    @Override
    public List<ConfigurationEntry> getSideConfig() {
        if (this.sideConfig.isEmpty()) {
            this.sideConfig.addAll(this.getDefaultConfig());
        }
        return sideConfig;
    }

    public RedstoneControl getRedstoneControl() {
        return redstoneControl;
    }

    public void setRedstoneControl(RedstoneControl redstoneControl) {
        this.redstoneControl = redstoneControl;
        this.setChanged();
    }
}
