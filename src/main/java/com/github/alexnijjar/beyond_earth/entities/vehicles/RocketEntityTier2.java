package com.github.alexnijjar.beyond_earth.entities.vehicles;

import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RocketEntityTier2 extends RocketEntity {

    public RocketEntityTier2(EntityType<?> type, World world) {
        super(type, world, 2);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 1.0f;
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_2_ROCKET.getDefaultStack();
    }
}