package com.github.alexnijjar.beyond_earth.items.vehicles;

import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;

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