package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.registry.ModMenuTypes;
import earth.terrarium.ad_astra.common.screen.NoInventorySlot;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LanderMenu extends AbstractVehicleMenu {

    public LanderMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (Vehicle) inventory.player.level.getEntity(buf.readInt()));
    }

    public LanderMenu(int syncId, Inventory inventory, Vehicle entity) {
        super(ModMenuTypes.LANDER_MENU.get(), syncId, inventory, entity, new Slot[]{

                // Left input slot
                new NoInventorySlot(entity.getInventory(), 0, 19, 58) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        if (!super.mayPlace(stack)) {
                            return false;
                        }
                        return FluidHooks.isFluidContainingItem(stack);
                    }
                },
                // Left output slot
                new NoInventorySlot(entity.getInventory(), 1, 48, 58) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                },

                // Inventory
                new NoInventorySlot(entity.getInventory(), 2, 85, 31),
                //
                new NoInventorySlot(entity.getInventory(), 3, 85 + 18, 31),
                //
                new NoInventorySlot(entity.getInventory(), 4, 85 + 18 * 2, 31),
                //
                new NoInventorySlot(entity.getInventory(), 5, 85 + 18 * 3, 31),
                //
                new NoInventorySlot(entity.getInventory(), 6, 85, 49),
                //
                new NoInventorySlot(entity.getInventory(), 7, 85 + 18, 49),
                //
                new NoInventorySlot(entity.getInventory(), 8, 85 + 18 * 2, 49),
                //
                new NoInventorySlot(entity.getInventory(), 9, 85 + 18 * 3, 49),

                // Rocket slot.
                new Slot(entity.getInventory(), 10, 34, 27) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                },});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 8;
    }
}