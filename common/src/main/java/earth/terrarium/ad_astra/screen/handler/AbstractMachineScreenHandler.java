package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractMachineScreenHandler<T extends AbstractMachineBlockEntity> extends ScreenHandler {

    protected final T machine;
    protected final World world;
    protected final PlayerEntity player;
    protected long energyAmount;
    protected List<FluidHolder> fluids;

    public AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, T entity) {
        this(type, syncId, inventory, entity, new Slot[]{});
    }

    // Add additional slots.
    public AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, T entity, Slot[] slots) {
        super(type, syncId);
        this.machine = entity;
        this.world = entity.getWorld();
        this.player = inventory.player;

        checkSize(inventory, this.machine.getInventorySize());

        this.machine.onOpen(inventory.player);

        for (Slot slot : slots) {
            this.addSlot(slot);
        }

        this.setPlayerInventory(inventory);
    }

    public T getMachine() {
        return this.machine;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.machine.canPlayerUse(player);
    }

    protected void setPlayerInventory(PlayerInventory inventory) {
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
    public ItemStack quickTransfer(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (index < this.machine.size()) {
                if (!this.insertItem(originalStack, this.machine.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.machine.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
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
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        this.updateToClient();
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();
        syncClientScreen();
    }

    public abstract void syncClientScreen();
}