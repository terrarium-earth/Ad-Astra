package earth.terrarium.ad_astra.common.data.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class EnergyNetworkData extends SavedData {
    private final Map<UUID, EnergyNetwork> networks;

    public EnergyNetworkData() {
        networks = new HashMap<>();
    }

    public EnergyNetworkData(CompoundTag tag) {
        Map<UUID, EnergyNetwork> networks = new HashMap<>();
        ListTag networksTag = tag.getList("Networks", Tag.TAG_COMPOUND);

        for (int i = 0; i < networksTag.size(); i++) {
            EnergyNetwork network = new EnergyNetwork(networksTag.getCompound(i));

            networks.put(network.getId(), network);
        }

        this.networks = networks;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag networksTag = new ListTag();
        for (EnergyNetwork value : networks.values()) {
            networksTag.add(value.save());
        }

        compoundTag.put("Networks", networksTag);
        return compoundTag;
    }

    public Stream<EnergyNetwork> getNetworks(Player player) {
        return networks.values().stream().filter(network -> network.isVisibleTo(player));
    }

    public Optional<EnergyNetwork> getNetwork(UUID id) {
        return Optional.ofNullable(networks.get(id));
    }

    public EnergyNetwork newNetwork(Component name, Player owner) {
        EnergyNetwork network = new EnergyNetwork(name, owner.getUUID());
        networks.put(network.getId(), network);

        return network;
    }
}
