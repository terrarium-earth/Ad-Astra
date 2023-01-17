package earth.terrarium.ad_astra.common.screen.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.GravityNormalizerBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class GravityNormalizerMenu extends AbstractModContainerMenu<GravityNormalizerBlockEntity> {

    public GravityNormalizerMenu(int id, Inventory inv, GravityNormalizerBlockEntity entity) {
        super(ModMenus.GRAVITY_NORMALIZER.get(), id, inv, entity);
    }

    public GravityNormalizerMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getTileFromBuf(inv.player.level, buf, GravityNormalizerBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 0;
    }

    @Override
    protected int getInventoryStart() {
        return 0;
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
