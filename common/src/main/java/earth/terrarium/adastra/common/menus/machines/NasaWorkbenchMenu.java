package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.NasaWorkbenchBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class NasaWorkbenchMenu extends BaseContainerMenu<NasaWorkbenchBlockEntity> {

    public NasaWorkbenchMenu(int id, Inventory inventory, NasaWorkbenchBlockEntity entity) {
        super(ModMenus.NASA_WORKBENCH.get(), id, inventory, entity);
    }

    @Override
    protected int getContainerInputEnd() {
        return 14;
    }

    @Override
    protected int getInventoryStart() {
        return 14;
    }

    @Override
    protected int startIndex() {
        return 0;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 8;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 142;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new Slot(entity, 0, 56, 20));
        addSlot(new Slot(entity, 1, 47, 38));
        addSlot(new Slot(entity, 2, 65, 38));
        addSlot(new Slot(entity, 3, 47, 56));
        addSlot(new Slot(entity, 4, 65, 56));
        addSlot(new Slot(entity, 5, 47, 74));
        addSlot(new Slot(entity, 6, 65, 74));
        addSlot(new Slot(entity, 7, 29, 92));
        addSlot(new Slot(entity, 8, 47, 92));
        addSlot(new Slot(entity, 9, 65, 92));
        addSlot(new Slot(entity, 10, 83, 92));
        addSlot(new Slot(entity, 11, 29, 110));
        addSlot(new Slot(entity, 12, 56, 110));
        addSlot(new Slot(entity, 13, 83, 110));

        addSlot(new FurnaceResultSlot(player, entity, 14, 129, 56) {
            @Override
            public boolean mayPickup(Player player) {
                return false;
            }
        });
    }

    @Override
    public void clicked(int slotIndex, int button, @NotNull ClickType actionType, @NotNull Player player) {
        super.clicked(slotIndex, button, actionType, player);
        if (slotIndex == 14) {
            if (entity.canCraft()) {
                entity.craft();
            }
        }
    }
}
