package net.mrscauthd.beyond_earth.items.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;

public class RocketItem<T extends RocketEntity> extends Item {

    private EntityType<T> rocketEntity;
    private int tier;

    public RocketItem(EntityType<T> rocketEntity, int tier, Settings settings) {
        super(settings);
        this.rocketEntity = rocketEntity;
        this.tier = tier;
    }

    public EntityType<T> getRocketEntity() {
        return this.rocketEntity;
    }

    public int getTier() {
        return tier;
    }
}