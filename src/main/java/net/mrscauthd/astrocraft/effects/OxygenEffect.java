package net.mrscauthd.astrocraft.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class OxygenEffect extends MobEffect {
    public OxygenEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setAirSupply(300);
    }
}