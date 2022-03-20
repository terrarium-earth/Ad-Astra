package net.mrscauthd.beyond_earth.gui.screens.energyrouter;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.cable.EnergyCableTileEntity;
import net.mrscauthd.beyond_earth.gui.helper.ContainerHelper;

public class EnergyRouterGui {

    public static class GuiContainerFactory implements IContainerFactory<EnergyRouterGui.GuiContainer> {
        public EnergyRouterGui.GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
            BlockPos pos = extraData.readBlockPos();
            EnergyCableTileEntity blockEntity = (EnergyCableTileEntity) inv.player.level.getBlockEntity(pos);
            return new EnergyRouterGui.GuiContainer(id, inv, blockEntity);
        }
    }

    public static class GuiContainer extends AbstractContainerMenu {
        private final EnergyCableTileEntity blockEntity;

        public GuiContainer(int id, Inventory inv, EnergyCableTileEntity blockEntity) {
            super(ModInit.ENERGY_ROUTER_GUI.get(), id);
            this.blockEntity = blockEntity;

            ContainerHelper.addInventorySlots(this, inv, 8, 90, this::addSlot);
        }

        public EnergyCableTileEntity getBlockEntity() {
            return this.blockEntity;
        }

        @Override
        public boolean stillValid(Player p_38874_) {
            return !this.getBlockEntity().isRemoved();
        }

        @Override
        public ItemStack quickMoveStack(Player playerIn, int index) {
            return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getBlockEntity(), this::moveItemStackTo);
        }
    }
}