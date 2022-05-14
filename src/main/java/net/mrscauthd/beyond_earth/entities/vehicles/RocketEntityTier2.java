package net.mrscauthd.beyond_earth.entities.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModItems;

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