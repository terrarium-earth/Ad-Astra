package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.OilRefineryBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class OilRefineryMenu extends AbstractModContainerMenu<OilRefineryBlockEntity> {

    public OilRefineryMenu(int id, Inventory inv, OilRefineryBlockEntity entity) {
        super(ModMenus.OIL_REFINERY.get(), id, inv, entity);
    }

    public OilRefineryMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, OilRefineryBlockEntity.class));
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
        this.addSlot(new Slot(this.entity, 0, 30, 18));
        this.addSlot(new Slot(this.entity, 2, 48, 18));
        this.addSlot(new Slot(this.entity, 4, 66, 18));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 1, 30, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 3, 48, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 5, 66, 36));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
