package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.ElectrolyzerBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class ElecrolyzerMenu extends AbstractModContainerMenu<ElectrolyzerBlockEntity> {

    public ElecrolyzerMenu(int id, Inventory inv, ElectrolyzerBlockEntity entity) {
        super(ModMenus.ELECTROLYZER.get(), id, inv, entity);
    }

    public ElecrolyzerMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, ElectrolyzerBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 3;
    }

    @Override
    protected int getInventoryStart() {
        return 6;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 0;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 60;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new Slot(entity, 0, 30, 18));
        addSlot(new Slot(entity, 2, 48, 18));
        addSlot(new Slot(entity, 4, 66, 18));
        addSlot(new FurnaceResultSlot(player, entity, 1, 30, 36));
        addSlot(new FurnaceResultSlot(player, entity, 3, 48, 36));
        addSlot(new FurnaceResultSlot(player, entity, 5, 66, 36));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
