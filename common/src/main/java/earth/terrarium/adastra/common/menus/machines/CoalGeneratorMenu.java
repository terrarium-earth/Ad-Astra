package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CoalGeneratorBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CoalGeneratorMenu extends BaseContainerMenu<CoalGeneratorBlockEntity> {

    public CoalGeneratorMenu(int id, Inventory inventory, CoalGeneratorBlockEntity entity) {
        super(ModMenus.COAL_GENERATOR.get(), id, inventory, entity);
    }

    public CoalGeneratorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.COAL_GENERATOR.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, CoalGeneratorBlockEntity.class));
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
        return 107;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, -25, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 69, 71));
    }
}
