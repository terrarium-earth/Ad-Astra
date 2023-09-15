package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.SolarPanelBlockEntity;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class SolarPanelMenu extends BasicContainerMenu<SolarPanelBlockEntity> {

    public SolarPanelMenu(int id, Inventory inventory, SolarPanelBlockEntity entity) {
        super(ModMenus.SOLAR_PANEL.get(), id, inventory, entity);
    }

    public SolarPanelMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.SOLAR_PANEL.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, SolarPanelBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 1;
    }

    @Override
    protected int getInventoryStart() {
        return 1;
    }

    @Override
    protected int startIndex() {
        return 0;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 35;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 145;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ArmorSlot(inventory, 39, -2, 137, EquipmentSlot.HEAD));
        addSlot(new ArmorSlot(inventory, 38, -2, 155, EquipmentSlot.CHEST));
        addSlot(new ArmorSlot(inventory, 37, -2, 173, EquipmentSlot.LEGS));
        addSlot(new ArmorSlot(inventory, 36, -2, 191, EquipmentSlot.FEET));
        addSlot(new ImageSlot(inventory, 40, -2, 209, ARMOR_SLOT_SHIELD));

        addSlot(new Slot(entity, 0, 80, 94));
    }
}
