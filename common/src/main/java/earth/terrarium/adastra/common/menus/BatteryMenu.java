package earth.terrarium.adastra.common.menus;

import earth.terrarium.adastra.common.blockentities.BatteryBlockEntity;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class BatteryMenu extends BasicContainerMenu<BatteryBlockEntity> {

    public BatteryMenu(int id, Inventory inventory, BatteryBlockEntity entity) {
        super(ModMenus.BATTERY.get(), id, inventory, entity);
    }

    public BatteryMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.BATTERY.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, BatteryBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 5;
    }

    @Override
    protected int getInventoryStart() {
        return 5;
    }

    @Override
    protected int startIndex() {
        return 1;
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
        // Battery
        addSlot(new ImageSlot(entity, 0, 184, 7, BATTERY_SLOT_ICON));

        // Charging
        addSlot(new Slot(entity, 1, 66, 68));
        addSlot(new Slot(entity, 2, 96, 68));
        addSlot(new Slot(entity, 3, 66, 98));
        addSlot(new Slot(entity, 4, 96, 98));

        // Player Armor
        addSlot(new ArmorSlot(inventory, 39, -2, 137, EquipmentSlot.HEAD));
        addSlot(new ArmorSlot(inventory, 38, -2, 155, EquipmentSlot.CHEST));
        addSlot(new ArmorSlot(inventory, 37, -2, 173, EquipmentSlot.LEGS));
        addSlot(new ArmorSlot(inventory, 36, -2, 191, EquipmentSlot.FEET));
        addSlot(new ImageSlot(inventory, 40, -2, 209, ARMOR_SLOT_SHIELD));
    }
}
