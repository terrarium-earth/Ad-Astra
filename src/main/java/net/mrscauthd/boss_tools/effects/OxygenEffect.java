package net.mrscauthd.boss_tools.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class OxygenEffect extends Effect {
    public OxygenEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        entity.setAir(300);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}