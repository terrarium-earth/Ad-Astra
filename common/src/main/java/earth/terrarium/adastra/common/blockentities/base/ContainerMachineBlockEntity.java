package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class ContainerMachineBlockEntity extends MachineBlockEntity implements BasicContainer, ExtraDataMenuProvider {
    private final NonNullList<ItemStack> items;

    public ContainerMachineBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state);
        items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
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
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    public void updateFluidSlots() {
    }
}
