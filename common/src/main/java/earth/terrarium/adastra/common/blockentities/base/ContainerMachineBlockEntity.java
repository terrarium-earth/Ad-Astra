package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ContainerMachineBlockEntity extends MachineBlockEntity implements BasicContainer, ExtraDataMenuProvider {
    private final NonNullList<ItemStack> items;

    public ContainerMachineBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state);
        items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    public void updateFluidSlots() {
    }

    @Override
    public abstract AbstractContainerMenu createMenu(int id, Inventory inventory, Player player);
}
