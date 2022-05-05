package net.mrscauthd.beyond_earth.entities.mobs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;

public class AlienEntity extends VillagerEntity {

    public AlienEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
    }
}
