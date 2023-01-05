package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class PlanetSelectionMenu extends AbstractContainerMenu {

    private final Player player;
    private final int tier;

    public PlanetSelectionMenu(int syncId, Player player, FriendlyByteBuf buf) {
        this(syncId, player, buf.readInt());
    }

    public PlanetSelectionMenu(int syncId, Player player, int tier) {
        super(ModMenus.PLANET_SELECTION_SCREEN_HANDLER.get(), syncId);
        this.tier = tier;
        this.player = player;
    }

    public int getTier() {
        return tier;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }
}