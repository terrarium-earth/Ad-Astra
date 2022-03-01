package net.mrscauthd.beyond_earth.events;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.forgeevents.LivingGravityEvent;

public class Gravity {

    public static void Gravity(LivingEntity entity, GravityType type, Level world) {
        float moon = 0.03F;
        float mars = 0.04F;
        float mercury = 0.03F;
        float venus = 0.04F;
        float orbit = 0.02F;

        if (Methods.isWorld(world, Methods.moon)) {
            gravityMath(type, entity, moon);
        }
        else if (Methods.isWorld(world, Methods.mars)) {
            gravityMath(type, entity, mars);
        }
        else if (Methods.isWorld(world, Methods.glacio)) {
            gravityMath(type, entity, mars);
        }
        else if (Methods.isWorld(world, Methods.mercury)) {
            gravityMath(type, entity, mercury);
        }
        else if (Methods.isWorld(world, Methods.venus)) {
            gravityMath(type, entity, venus);
        }
        else if (Methods.isOrbitWorld(world)) {
            gravityMath(type, entity, orbit);
        }
    }

    public enum GravityType {
        PLAYER, LIVING
    }

    public static boolean playerGravityCheck(Player player) {
        return !player.getAbilities().flying && !player.isFallFlying() && !player.isInWater() && !player.isInLava() && !player.isNoGravity() && !player.hasEffect(MobEffects.SLOW_FALLING) && !player.hasEffect(MobEffects.LEVITATION);
    }

    public static boolean livingGravityCheck(LivingEntity entity) {
        return !(entity instanceof Player) && !entity.isFallFlying() && !entity.isInWater() && !entity.isInLava() && !entity.isNoGravity() && !Methods.isVehicle(entity) && !entity.hasEffect(MobEffects.SLOW_FALLING) && !entity.hasEffect(MobEffects.LEVITATION);
    }

    public static void gravityMath(GravityType type, LivingEntity entity, float gravity) {
    	if (!checkType(type, entity)) {
    		return;
    	}

    	if (MinecraftForge.EVENT_BUS.post(new LivingGravityEvent(entity, gravity, type))) {
    		return;
    	}

    	entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y() / 0.98 + 0.08 - gravity, entity.getDeltaMovement().z());
	}

    public static boolean checkType(GravityType type, LivingEntity entity) {
    	return (type == GravityType.PLAYER && playerGravityCheck((Player) entity)) || (type == GravityType.LIVING && livingGravityCheck(entity));
    }
}