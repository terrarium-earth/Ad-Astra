package net.mrscauthd.beyond_earth.events;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class TickableSpaceSoundSystem implements TickableSoundInstance {
    private final TickableSoundInstance delegate;

    public TickableSpaceSoundSystem(TickableSoundInstance delegate) {
        this.delegate = delegate;
    }

    @Override
    public ResourceLocation getLocation() {
        return delegate.getLocation();
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager p_119841_) {
        return delegate.resolve(p_119841_);
    }

    @Override
    public Sound getSound() {
        return delegate.getSound();
    }

    @Override
    public SoundSource getSource() {
        return delegate.getSource();
    }

    @Override
    public boolean isLooping() {
        return delegate.isLooping();
    }

    @Override
    public boolean isRelative() {
        return delegate.isRelative();
    }

    @Override
    public int getDelay() {
        return delegate.getDelay();
    }

    @Override
    public float getVolume() {
        return delegate.getVolume() * 0.3F;
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
    public Attenuation getAttenuation() {
        return delegate.getAttenuation();
    }

    @Override
    public boolean isStopped() {
        return delegate.isStopped();
    }

    @Override
    public void tick() {
        delegate.tick();
    }
}
