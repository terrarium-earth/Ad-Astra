package net.mrscauthd.beyond_earth.events.forgeevents;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LivingSetFireInHotPlanetEvent extends LivingEvent {

    public LivingSetFireInHotPlanetEvent(LivingEntity entity) {
        super(entity);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

}