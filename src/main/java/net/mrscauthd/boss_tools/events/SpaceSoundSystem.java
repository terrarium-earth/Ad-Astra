package net.mrscauthd.boss_tools.events;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SpaceSoundSystem implements ISound {
    private final ISound delegate;

    public SpaceSoundSystem(ISound delegate) {
        this.delegate = delegate;
    }

    @Override
    public ResourceLocation getSoundLocation() {
        return delegate.getSoundLocation();
    }

    @Nullable
    @Override
    public SoundEventAccessor createAccessor(SoundHandler handler) {
        return delegate.createAccessor(handler);
    }

    @Override
    public Sound getSound() {
        return delegate.getSound();
    }

    @Override
    public SoundCategory getCategory() {
        return delegate.getCategory();
    }

    @Override
    public boolean canRepeat() {
        return delegate.canRepeat();
    }

    @Override
    public boolean isGlobal() {
        return delegate.isGlobal();
    }

    @Override
    public int getRepeatDelay() {
        return delegate.getRepeatDelay();
    }

    @Override
    public float getVolume() {
        return 0.09F;
    }

    @Override
    public float getPitch() {
        return delegate.getPitch();
    }

    @Override
    public double getX() {
        return delegate.getX();
    }

    @Override
    public double getY() {
        return delegate.getY();
    }

    @Override
    public double getZ() {
        return delegate.getZ();
    }

    @Override
    public AttenuationType getAttenuationType() {
        return delegate.getAttenuationType();
    }
}
