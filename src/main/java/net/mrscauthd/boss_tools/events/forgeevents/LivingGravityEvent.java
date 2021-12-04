package net.mrscauthd.boss_tools.events.forgeevents;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LivingGravityEvent extends LivingEvent {

    public LivingGravityEvent(LivingEntity entity) {
        super(entity);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

}