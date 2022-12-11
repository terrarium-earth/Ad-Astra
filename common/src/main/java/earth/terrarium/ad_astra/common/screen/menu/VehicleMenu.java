package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.registry.ModMenuTypes;
import earth.terrarium.ad_astra.common.screen.NoInventorySlot;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class VehicleMenu extends AbstractVehicleMenu {

    public VehicleMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (Vehicle) inventory.player.level.getEntity(buf.readInt()));
    }

    public VehicleMenu(int syncId, Inventory inventory, Vehicle entity) {
        super(ModMenuTypes.VEHICLE_MENU.get(), syncId, inventory, entity, new Slot[]{

                // Left input slot.
                new NoInventorySlot(entity.getInventory(), 0, 20, 24) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        if (!super.mayPlace(stack)) {
                            return false;
                        }
                        return FluidHooks.isFluidContainingItem(stack);
                    }
                },
                // Left output slot.
                new Slot(entity.getInventory(), 1, 20, 54) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                },

                // Inventory
                new NoInventorySlot(entity.getInventory(), 2, 86, 31),
                //
                new NoInventorySlot(entity.getInventory(), 3, 86 + 18, 31),
                //
                new NoInventorySlot(entity.getInventory(), 4, 86 + 18 * 2, 31),
                //
                new NoInventorySlot(entity.getInventory(), 5, 86 + 18 * 3, 31),
                //
                new NoInventorySlot(entity.getInventory(), 6, 86, 49),
                //
                new NoInventorySlot(entity.getInventory(), 7, 86 + 18, 49),
                //
                new NoInventorySlot(entity.getInventory(), 8, 86 + 18 * 2, 49),
                //
                new NoInventorySlot(entity.getInventory(), 9, 86 + 18 * 3, 49)});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 8;
    }
}