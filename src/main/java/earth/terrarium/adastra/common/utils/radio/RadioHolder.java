package earth.terrarium.adastra.common.utils.radio;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a vehicle that has a radio, all urls must be a valid string empty meaning no station is playing.
 */
public interface RadioHolder {

    int RANGE = 3072;
    int RANGE_DROPOFF = 1024;

    @NotNull String getRadioUrl();

    void setRadioUrl(@NotNull String url);
}
