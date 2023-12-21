package earth.terrarium.adastra.client.sounds;

import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class RocketSoundInstance extends AbstractTickableSoundInstance {
    protected final Vehicle vehicle;
    protected boolean canPlay = true;

    public RocketSoundInstance(Vehicle vehicle) {
        super(ModSoundEvents.ROCKET.get(), SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.vehicle = vehicle;
        this.looping = true;
    }

    @Override
    public float getVolume() {
        return canPlay ? 10 : 0;
    }

    @Override
    public void tick() {
        if (!vehicle.isRemoved()) {
            x = vehicle.getX();
            y = vehicle.getY();
            z = vehicle.getZ();
        } else stop();
    }
}
