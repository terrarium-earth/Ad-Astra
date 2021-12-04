package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;

public class Gravity {
    public static void Gravity(LivingEntity entity, GravityType type, World world) {
        double moon = 0.03;
        double mars = 0.04;
        double mercury = 0.03;
        double venus = 0.04;
        double orbit = 0.02;

        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"moon"))) {
            gravityMath(type, entity, moon, -2.5f);
        }

        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mars"))) {
            gravityMath(type, entity, mars, -2.0f);
        }

        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mercury"))) {
            gravityMath(type, entity, mercury, -2.5f);
        }

        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"venus"))) {
            gravityMath(type, entity, venus, -2.0f);
        }

        if (Methodes.isOrbitWorld(world)) {
            gravityMath(type, entity, orbit, -2.5f);
        }
    }

    public enum GravityType {
        PLAYER,LIVING
    }

    public static boolean playerGravityCheck(PlayerEntity player) {
        if (!player.abilities.isFlying && !player.isElytraFlying() && !player.isInWater() && !player.isInLava() && !player.hasNoGravity()) {
            return true;
        }

        return false;
    }

    public static boolean livingGravityCheck(LivingEntity entity) {
        if (!entity.isElytraFlying() && !entity.isInWater() && !entity.isInLava() && !entity.hasNoGravity() && !(entity instanceof PlayerEntity) && !Methodes.AllVehiclesOr(entity)) {
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

    	entity.setMotion(entity.getMotion().getX(), entity.getMotion().getY() / 0.98 + 0.08 - gravity, entity.getMotion().getZ());
    	fallDamage(entity, fallDistance);
	}

    public static boolean checkType(GravityType type, LivingEntity entity) {
    	if (type == GravityType.PLAYER && playerGravityCheck((PlayerEntity) entity)) {
    		return true;
    	} else if (type == GravityType.LIVING && livingGravityCheck(entity)) {
    		return true;
    	}

    	return false;
    }

    public static void fallDamage (LivingEntity entity, float fallDistance) {
        if (entity.getMotion().getY() < -0.1) {
            entity.fallDistance = (float) entity.getMotion().getY() * fallDistance;
        }
    }

}