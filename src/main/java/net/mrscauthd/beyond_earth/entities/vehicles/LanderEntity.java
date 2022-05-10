package net.mrscauthd.beyond_earth.entities.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class LanderEntity extends VehicleEntity {

    public LanderEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean shouldSit() {
        return false;
    }
}