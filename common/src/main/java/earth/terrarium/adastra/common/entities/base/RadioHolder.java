package earth.terrarium.adastra.common.entities.base;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a vehicle that has a radio, all urls must be a valid string empty meaning no station is playing.
 */
public interface RadioHolder {

    @NotNull String getRadioUrl();

    void setRadioUrl(@NotNull String url);
}
