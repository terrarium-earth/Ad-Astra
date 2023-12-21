package earth.terrarium.adastra.common.menus.vehicles;

import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.menus.base.BaseEntityContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;

public class LanderMenu extends BaseEntityContainerMenu<Lander> {

    public LanderMenu(int id, Inventory inventory, Lander entity) {
        super(ModMenus.LANDER.get(), id, inventory, entity);
    }

    public LanderMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.LANDER.get(), id, inventory, (Lander) inventory.player.level().getEntity(buf.readVarInt()));
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
                addSlot(new FurnaceResultSlot(player, entity.inventory(), i * 4 + j + 3, 77 + j * 18, 31 + i * 18));
            }
        }

        addSlot(new FurnaceResultSlot(player, entity.inventory(), 0, 26, 27));
        addSlot(new FurnaceResultSlot(player, entity.inventory(), 1, 11, 58));
        addSlot(new FurnaceResultSlot(player, entity.inventory(), 2, 40, 58));
    }
}
