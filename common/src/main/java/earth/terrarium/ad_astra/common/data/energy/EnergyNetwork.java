package earth.terrarium.ad_astra.common.data.energy;

import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;

import java.util.UUID;

public class EnergyNetwork {
    private final EnergyContainer energy = new SimpleEnergyContainer(Long.MAX_VALUE);
    private EnergyNetworkVisibility visibility;
    private DyeColor color;
    private Component name;
    private final UUID ownerId;
    private final UUID id;

    public EnergyNetwork(Component name, UUID ownerId) {
        this.name = name;
        this.ownerId = ownerId;

        id = UUID.randomUUID();
        color = DyeColor.WHITE;
        visibility = EnergyNetworkManager.PRIVATE_NETWORK;
    }

    public EnergyNetwork(CompoundTag data) {
        name = Component.Serializer.fromJson(data.getString("Name"));
        ownerId = data.getUUID("Owner");

        id = data.getUUID("ID");
        color = DyeColor.byName(data.getString("Color"), DyeColor.WHITE);
        visibility = EnergyNetworkManager.getVisibilityRegistry().getOptional(new ResourceLocation(data.getString("Visibility"))).orElse(EnergyNetworkManager.PRIVATE_NETWORK);

        energy.deserialize(data.getCompound("Energy"));
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", Component.Serializer.toJson(name));
        tag.putUUID("Owner", ownerId);
        tag.putUUID("ID", id);
        tag.putString("Color", color.getName());
        tag.putString("Visibility", EnergyNetworkManager.getVisibilityRegistry().getKey(visibility).toString());
        tag.put("Energy", energy.serialize(new CompoundTag()));

        return tag;
    }

    public EnergyContainer getEnergy() {
        return energy;
    }

    public DyeColor getColor() {
        return color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    public Component getName() {
        return name;
    }

    public void setName(Component name) {
        this.name = name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public UUID getId() {
        return id;
    }

    public boolean isVisibleTo(Player player) {
        return visibility.isVisible(this, player);
    }

    public void setVisibility(EnergyNetworkVisibility visibility) {
        this.visibility = visibility;
    }
}
