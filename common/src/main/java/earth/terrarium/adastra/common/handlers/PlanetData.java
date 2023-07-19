package earth.terrarium.adastra.common.handlers;

public final class PlanetData {
    private static final int OXYGEN_BIT_LENGTH = 1;
    private static final int TEMPERATURE_BIT_LENGTH = 16;
    private static final int GRAVITY_BIT_LENGTH = 15; // unsigned

    private static final float GRAVITY_PRECISION = 100.0f;

    private static final int OXYGEN_BIT = 0;
    private static final int TEMPERATURE_BIT = OXYGEN_BIT + OXYGEN_BIT_LENGTH;
    private static final int GRAVITY_BIT = TEMPERATURE_BIT + TEMPERATURE_BIT_LENGTH;

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

    public int pack() {
        int packedData = 0;
        packedData |= (this.oxygen ? 1 : 0) << OXYGEN_BIT;
        packedData |= (this.temperature & ((1 << TEMPERATURE_BIT_LENGTH) - 1)) << TEMPERATURE_BIT;
        packedData |= (int) (this.gravity * GRAVITY_PRECISION) << GRAVITY_BIT;

        return packedData;
    }

    public static PlanetData unpack(int packedData) {
        boolean oxygen = ((packedData >> OXYGEN_BIT) & 1) == 1;
        short temperature = (short) ((packedData >> TEMPERATURE_BIT) & ((1 << TEMPERATURE_BIT_LENGTH) - 1));
        float gravity = ((packedData >> GRAVITY_BIT) & ((1 << GRAVITY_BIT_LENGTH) - 1)) / GRAVITY_PRECISION;

        return new PlanetData(oxygen, temperature, gravity);
    }
}