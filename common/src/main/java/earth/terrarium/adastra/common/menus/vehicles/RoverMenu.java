package earth.terrarium.adastra.common.menus.vehicles;

import earth.terrarium.adastra.common.entities.vehicles.Rover;
import earth.terrarium.adastra.common.menus.base.BaseEntityContainerMenu;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class RoverMenu extends BaseEntityContainerMenu<Rover> {

    public RoverMenu(int id, Inventory inventory, Rover entity) {
        super(ModMenus.ROVER.get(), id, inventory, entity);
    }

    public RoverMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.ROVER.get(), id, inventory, (Rover) inventory.player.level().getEntity(buf.readVarInt()));
    }

    @Override
    protected int getContainerInputEnd() {
        return 18;
    }

    @Override
    protected int getInventoryStart() {
        return 18;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 0;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 99;
    }

    @Override
    protected void addMenuSlots() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                addSlot(new Slot(entity.inventory(), i * 4 + j + 2, 78 + j * 18, 16 + i * 18));
            }
        }

        addSlot(new Slot(entity.inventory(), 0, 12, 26));
        addSlot(CustomSlot.noPlace(entity.inventory(), 1, 12, 56));
    }
}
