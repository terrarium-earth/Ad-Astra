package net.mrscauthd.beyond_earth.entities.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModItems;

public class RoverEntity extends VehicleEntity {

    public RoverEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.ROVER.getDefaultStack();
    }
}