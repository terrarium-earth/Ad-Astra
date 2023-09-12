package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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

public abstract class ContainerMachineBlockEntity extends MachineBlockEntity implements BasicContainer, WorldlyContainer, ExtraDataMenuProvider, SideConfigurable, BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    private final List<ConfigurationEntry> sideConfig = new ArrayList<>();
    private final NonNullList<ItemStack> items;

    protected WrappedBlockEnergyContainer energyContainer;
    private RedstoneControl redstoneControl = RedstoneControl.ALWAYS_ON;

    private long lastEnergy;
    private long energyDifference;

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
        extractBatterySlot();
        if (time % 2 == 0 && shouldSync()) sync();
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        var energy = this.getEnergyStorage();
        this.energyDifference = energy.getStoredEnergy() - this.lastEnergy;
        this.lastEnergy = energy.getStoredEnergy();
    }

    public long energyDifference() {
        return this.energyDifference;
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

    public void extractBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(holder, this, null, energyContainer.maxInsert(), false);
        if (holder.isDirty()) {
            this.setItem(0, holder.getStack());
        }
    }

    public boolean shouldSync() {
        return false;
    }

    @Override
    public NonNullList<ItemStack> items() {
        return this.items;
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

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        if (direction == null) return false;
        return getConfigForSlot(index).get(ModUtils.relative(this, direction)).canPull();
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return getConfigForSlot(index).get(ModUtils.relative(this, direction)).canPush();
    }

    public ConfigurationEntry getConfigForSlot(int index) {
        return getSideConfig().get(0);
    }
}
