package earth.terrarium.adastra.common.menus.vehicles;

import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.menus.base.BaseEntityContainerMenu;
import earth.terrarium.adastra.common.menus.content.EntityContent;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class LanderMenu extends BaseEntityContainerMenu<Lander> {

    public LanderMenu(int id, Inventory inventory, Lander entity) {
        super(ModMenus.LANDER.get(), id, inventory, entity);
    }

    public LanderMenu(int id, Inventory inventory, Optional<EntityContent> content) {
        super(ModMenus.LANDER.get(), id, inventory, (Lander) inventory.player.level().getEntity(content.get().entityId()));
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
                addSlot(CustomSlot.noPlace(entity.inventory(), i * 4 + j + 3, 77 + j * 18, 31 + i * 18));
            }
        }

        addSlot(CustomSlot.noPlace(entity.inventory(), 0, 26, 27));
        addSlot(CustomSlot.noPlace(entity.inventory(), 1, 11, 58));
        addSlot(CustomSlot.noPlace(entity.inventory(), 2, 40, 58));
    }
}
