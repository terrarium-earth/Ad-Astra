package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class CompressorMenu extends BaseContainerMenu<CompressorBlockEntity> {

    public CompressorMenu(int id, Inventory inventory, CompressorBlockEntity entity) {
        super(ModMenus.COMPRESSOR.get(), id, inventory, entity);
    }

    public CompressorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.COMPRESSOR.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, CompressorBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 3;
    }

    @Override
    protected int getInventoryStart() {
        return 3;
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
        return 114;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, -25, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 45, 56));
        addSlot(new FurnaceResultSlot(player, entity, 2, 92, 56));
    }
}
