package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.EtrionicGeneratorBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class EtrionicGeneratorMenu extends AbstractModContainerMenu<EtrionicGeneratorBlockEntity> {

    public EtrionicGeneratorMenu(int id, Inventory inv, EtrionicGeneratorBlockEntity entity) {
        super(ModMenus.ETRIONIC_GENERATOR_MENU.get(), id, inv, entity);
    }

    public EtrionicGeneratorMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, EtrionicGeneratorBlockEntity.class));
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
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
