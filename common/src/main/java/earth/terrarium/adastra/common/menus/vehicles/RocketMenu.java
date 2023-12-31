package earth.terrarium.adastra.common.menus.vehicles;

import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.menus.base.BaseEntityContainerMenu;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class RocketMenu extends BaseEntityContainerMenu<Rocket> {

    public RocketMenu(int id, Inventory inventory, Rocket entity) {
        super(ModMenus.ROCKET.get(), id, inventory, entity);
    }

    public RocketMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.ROCKET.get(), id, inventory, (Rocket) inventory.player.level().getEntity(buf.readVarInt()));
    }

    @Override
    protected int getContainerInputEnd() {
        return 10;
    }

    @Override
    protected int getInventoryStart() {
        return 10;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 0;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 92;
    }

    @Override
    protected void addMenuSlots() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                addSlot(new Slot(entity.inventory(), i * 4 + j + 2, 78 + j * 18, 31 + i * 18));
            }
        }

        addSlot(new Slot(entity.inventory(), 0, 12, 24));
        addSlot(CustomSlot.noPlace(entity.inventory(), 1, 12, 54));
    }
}
