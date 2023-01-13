package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.CombustionGeneratorBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class CombustionGeneratorMenu extends AbstractModContainerMenu<CombustionGeneratorBlockEntity> {

    public CombustionGeneratorMenu(int id, Inventory inv, CombustionGeneratorBlockEntity entity) {
        super(ModMenus.COMBUSTION_GENERATOR.get(), id, inv, entity);
    }

    public CombustionGeneratorMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, CombustionGeneratorBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 1;
    }

    @Override
    protected int getInventoryStart() {
        return 2;
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
        this.addSlot(new FurnaceResultSlot(this.player, this.entity, 1, 30, 36));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
