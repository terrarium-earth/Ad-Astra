package com.github.alexnijjar.ad_astra.entities.vehicles;

import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RocketEntityTier1 extends RocketEntity {

	public RocketEntityTier1(EntityType<?> type, World world) {
		super(type, world, 1);
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
		return ModItems.TIER_1_ROCKET.get().getDefaultStack();
	}
}