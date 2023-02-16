package earth.terrarium.ad_astra.common.data.energy;

import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Objects;

public class EnergyNetworkManager {
    public static final ResourceLocation VISIBILITY_REGISTRY_ID = new ResourceLocation(AdAstra.MOD_ID, "energy_network_visibility");
    public static final ResourceLocation PRIVATE_NETWORK_ID = new ResourceLocation(AdAstra.MOD_ID, "private");
    public static final ResourceLocation PUBLIC_NETWORK_ID = new ResourceLocation(AdAstra.MOD_ID, "public");
    public static final EnergyNetworkVisibility PRIVATE_NETWORK = (network, player) -> network.getOwnerId().equals(player.getUUID());
    public static final EnergyNetworkVisibility PUBLIC_NETWORK = (network, player) -> true;

    public static EnergyNetworkData getNetworkData(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(EnergyNetworkData::new, EnergyNetworkData::new, AdAstra.MOD_ID + "_energy_networks");
    }

    @ExpectPlatform
    public static DefaultedRegistry<EnergyNetworkVisibility> getVisibilityRegistry() {
        throw new NotImplementedException();
    }
}
