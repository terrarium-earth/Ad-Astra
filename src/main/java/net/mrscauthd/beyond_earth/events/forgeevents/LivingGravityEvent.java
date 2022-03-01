package net.mrscauthd.beyond_earth.events.forgeevents;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.mrscauthd.beyond_earth.events.Gravity;

@Cancelable
public class LivingGravityEvent extends LivingEvent {

    private double gravity;
    private Gravity.GravityType type;

    public LivingGravityEvent(LivingEntity entity, double gravity, Gravity.GravityType type) {
        super(entity);
        this.gravity = gravity;
        this.type = type;
    }

    public double getGravity() {
        return gravity;
    }

    public Gravity.GravityType getType() {
        return type;
    }
}