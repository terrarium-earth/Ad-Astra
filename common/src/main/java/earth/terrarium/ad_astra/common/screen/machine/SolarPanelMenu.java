package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class SolarPanelMenu extends AbstractModContainerMenu<SolarPanelBlockEntity> {

    public SolarPanelMenu(int id, Inventory inv, SolarPanelBlockEntity entity) {
        super(ModMenus.SOLAR_PANEL.get(), id, inv, entity);
    }

    public SolarPanelMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, SolarPanelBlockEntity.class));
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
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
