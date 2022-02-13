package net.mrscauthd.beyond_earth.events.forgeevents;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.mrscauthd.beyond_earth.events.Gravity;

@Cancelable
public class LivingGravityEvent extends LivingEvent {

    private double gravity;
    private float fallDistance;
    private Gravity.GravityType type;

    public LivingGravityEvent(LivingEntity entity, double gravity, float fallDistance, Gravity.GravityType type) {
        super(entity);
        this.gravity = gravity;
        this.fallDistance = fallDistance;
        this.type = type;
    }

    public double getGravity() {
        return gravity;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public Gravity.GravityType getType() {
        return type;
    }
}