package earth.terrarium.adastra.common.handlers;

public final class PlanetData {
    public static final int OXYGEN_BIT = 0;
    public static final int OXYGEN_BIT_LENGTH = Byte.SIZE;
    public static final int TEMPERATURE_BIT = OXYGEN_BIT + OXYGEN_BIT_LENGTH;
    public static final int TEMPERATURE_BIT_LENGTH = Short.SIZE;
    public static final int GRAVITY_BIT = TEMPERATURE_BIT + TEMPERATURE_BIT_LENGTH;
    public static final int GRAVITY_BIT_LENGTH = Float.SIZE;

    private boolean oxygen;
    private short temperature;
    private float gravity;

    public PlanetData(boolean oxygen, short temperature, float gravity) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.gravity = gravity;
    }

    public boolean oxygen() {
        return oxygen;
    }

    public void setOxygen(boolean oxygen) {
        this.oxygen = oxygen;
    }

    public short temperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public float gravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public long pack() {
        long packedData = 0;

        packedData |= (this.oxygen ? 1L : 0L) << OXYGEN_BIT;
        packedData |= (this.temperature & ((1 << TEMPERATURE_BIT_LENGTH) - 1)) << TEMPERATURE_BIT;
        packedData |= (Float.floatToIntBits(this.gravity) & ((1L << GRAVITY_BIT_LENGTH) - 1)) << GRAVITY_BIT;

        return packedData;
    }

    public static PlanetData unpack(long packedData) {
        boolean oxygen = ((packedData >> OXYGEN_BIT) & ((1L << OXYGEN_BIT_LENGTH) - 1)) == 1;
        short temperature = (short) ((packedData >> TEMPERATURE_BIT) & ((1 << TEMPERATURE_BIT_LENGTH) - 1));
        float gravity = Float.intBitsToFloat((int) ((packedData >> GRAVITY_BIT) & ((1L << GRAVITY_BIT_LENGTH) - 1)));

        return new PlanetData(oxygen, temperature, gravity);
    }
}
