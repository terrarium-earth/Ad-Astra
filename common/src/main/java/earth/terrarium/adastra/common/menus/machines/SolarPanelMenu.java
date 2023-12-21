package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.SolarPanelBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class SolarPanelMenu extends BaseContainerMenu<SolarPanelBlockEntity> {

    public SolarPanelMenu(int id, Inventory inventory, SolarPanelBlockEntity entity) {
        super(ModMenus.SOLAR_PANEL.get(), id, inventory, entity);
    }

    public SolarPanelMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.SOLAR_PANEL.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, SolarPanelBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 2;
    }

    @Override
    protected int getInventoryStart() {
        return 2;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 0;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 146;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, -25, BATTERY_SLOT_ICON));
    }
}
