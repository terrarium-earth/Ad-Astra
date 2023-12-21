package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.client.screens.machines.NasaWorkbenchBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
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

    public NasaWorkbenchMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.NASA_WORKBENCH.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, NasaWorkbenchBlockEntity.class));
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
        return 0;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 142;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new Slot(entity, 0, 48, 20));
        addSlot(new Slot(entity, 1, 39, 38));
        addSlot(new Slot(entity, 2, 57, 38));
        addSlot(new Slot(entity, 3, 39, 56));
        addSlot(new Slot(entity, 4, 57, 56));
        addSlot(new Slot(entity, 5, 39, 74));
        addSlot(new Slot(entity, 6, 57, 74));
        addSlot(new Slot(entity, 7, 21, 92));
        addSlot(new Slot(entity, 8, 39, 92));
        addSlot(new Slot(entity, 9, 57, 92));
        addSlot(new Slot(entity, 10, 75, 92));
        addSlot(new Slot(entity, 11, 21, 110));
        addSlot(new Slot(entity, 12, 48, 110));
        addSlot(new Slot(entity, 13, 75, 110));


        addSlot(new FurnaceResultSlot(player, entity, 14, 121, 56) {
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
