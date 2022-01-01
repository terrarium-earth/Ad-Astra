package net.mrscauthd.beyond_earth.events;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.forgeevents.LivingGravityEvent;

public class Gravity {
    public static void Gravity(LivingEntity entity, GravityType type, Level world) {
        double moon = 0.03;
        double mars = 0.04;
        double mercury = 0.03;
        double venus = 0.04;
        double orbit = 0.02;

        if (Methodes.isWorld(world, Methodes.moon)) {
            gravityMath(type, entity, moon, -2.5f);
        }

        if (Methodes.isWorld(world, Methodes.mars)) {
            gravityMath(type, entity, mars, -2.0f);
        }

        if (Methodes.isWorld(world, Methodes.mercury)) {
            gravityMath(type, entity, mercury, -2.5f);
        }

        if (Methodes.isWorld(world, Methodes.venus)) {
            gravityMath(type, entity, venus, -2.0f);
        }

        if (Methodes.isOrbitWorld(world)) {
            gravityMath(type, entity, orbit, -2.5f);
        }
    }

    public enum GravityType {
        PLAYER,LIVING
    }

    public static boolean playerGravityCheck(Player player) {
        if (!player.getAbilities().flying && !player.isFallFlying() && !player.isInWater() && !player.isInLava() && !player.isNoGravity() && !player.hasEffect(MobEffects.SLOW_FALLING) && !player.hasEffect(MobEffects.LEVITATION)) {
            return true;
        }

        return false;
    }

    public static boolean livingGravityCheck(LivingEntity entity) {
        if (!entity.isFallFlying() && !entity.isInWater() && !entity.isInLava() && !entity.isNoGravity() && !(entity instanceof Player) && !Methodes.AllVehiclesOr(entity) && !entity.hasEffect(MobEffects.SLOW_FALLING) && !entity.hasEffect(MobEffects.LEVITATION)) {
            return true;
        }

        return false;
    }

    public static void gravityMath(GravityType type, LivingEntity entity, double gravity, float fallDistance) {
    	if (!checkType(type, entity)) {
    		return;
    	}

    	if (MinecraftForge.EVENT_BUS.post(new LivingGravityEvent(entity))) {
    		return;
    	}

    	entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y() / 0.98 + 0.08 - gravity, entity.getDeltaMovement().z());
    	fallDamage(entity, fallDistance);
	}

    public static boolean checkType(GravityType type, LivingEntity entity) {
    	if (type == GravityType.PLAYER && playerGravityCheck((Player) entity)) {
    		return true;
    	} else if (type == GravityType.LIVING && livingGravityCheck(entity)) {
    		return true;
    	}

    	return false;
    }

    public static void fallDamage (LivingEntity entity, float fallDistance) {
        if (entity.getDeltaMovement().y() < -0.1) {
            entity.fallDistance = (float) entity.getDeltaMovement().y() * fallDistance;
        }
    }
}