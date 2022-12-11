package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractMachineMenu<T extends AbstractMachineBlockEntity> extends AbstractContainerMenu {

    protected final T machine;
    protected final Level level;
    protected final Player player;
    protected long energyAmount;
    protected List<FluidHolder> fluids;

    public AbstractMachineMenu(MenuType<?> type, int syncId, Inventory inventory, T entity) {
        this(type, syncId, inventory, entity, new Slot[]{});
    }

    // Add additional slots.
    public AbstractMachineMenu(MenuType<?> type, int syncId, Inventory inventory, T entity, Slot[] slots) {
        super(type, syncId);
        this.machine = entity;
        this.level = entity.getLevel();
        this.player = inventory.player;

        checkContainerSize(inventory, this.machine.getInventorySize());

        this.machine.startOpen(inventory.player);

        for (Slot slot : slots) {
            this.addSlot(slot);
        }

        this.setPlayerInventory(inventory);
    }

    public T getMachine() {
        return this.machine;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.machine.stillValid(player);
    }

    protected void setPlayerInventory(Inventory inventory) {
        int m;
        int l;

        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + this.getPlayerInventoryOffset() + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            addSlot(new Slot(inventory, m, 8 + m * 18, 142 + this.getPlayerInventoryOffset()));
        }
    }

    public int getPlayerInventoryOffset() {
        return 0;
    }

    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (index < this.machine.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.machine.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.machine.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    public long getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(long energyAmount) {
        this.energyAmount = energyAmount;
    }

    public List<FluidHolder> getFluids() {
        return fluids == null ? List.of(FluidHooks.emptyFluid(), FluidHooks.emptyFluid()) : fluids;
    }

    public void setFluids(List<FluidHolder> fluids) {
        this.fluids = fluids;
    }

    // Fixes a client sync issue.
    @Override
    public void clicked(int slotIndex, int button, @NotNull ClickType actionType, @NotNull Player player) {
        super.clicked(slotIndex, button, actionType, player);
        this.broadcastFullState();
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        syncClientScreen();
    }

    public void onRecipeTransfer(Recipe<?> recipe) {

    }

    public abstract void syncClientScreen();
}