package earth.terrarium.ad_astra.common.data.energy;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface EnergyNetworkVisibility {
    boolean isVisible(EnergyNetwork network, Player player);
}
