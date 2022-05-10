package net.mrscauthd.beyond_earth.items.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.mrscauthd.beyond_earth.entities.vehicles.VehicleEntity;

public class RocketItem<T extends VehicleEntity> extends Item {

    private EntityType<T> rocketEntity;

    public RocketItem(EntityType<T> rocketEntity, Settings settings) {
        super(settings);
        this.rocketEntity = rocketEntity;
    }

    public EntityType<T> getRocketEntity() {
        return this.rocketEntity;
    }
}