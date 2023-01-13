package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.RecyclerBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class RecyclerMenu extends AbstractModContainerMenu<RecyclerBlockEntity> {

    public RecyclerMenu(int id, Inventory inv, RecyclerBlockEntity entity) {
        super(ModMenus.RECYCLER.get(), id, inv, entity);
    }

    public RecyclerMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, RecyclerBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 1;
    }

    @Override
    protected int getInventoryStart() {
        return 9;
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
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 1, 0, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 2, 18, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 3, 36, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 4, 54, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 5, 72, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 6, 90, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 7, 108, 36));
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 8, 126, 36));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
