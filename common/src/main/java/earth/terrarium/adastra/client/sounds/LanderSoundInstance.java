package earth.terrarium.adastra.client.sounds;

import earth.terrarium.adastra.common.entities.vehicles.Vehicle;

public class LanderSoundInstance extends RocketSoundInstance {
    public LanderSoundInstance(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void tick() {
        this.canPlay = vehicle.yya() > 0;
        if (!vehicle.isRemoved() && !vehicle.onGround()) {
            x = vehicle.getX();
            y = vehicle.getY();
            z = vehicle.getZ();
        } else stop();
    }
}
