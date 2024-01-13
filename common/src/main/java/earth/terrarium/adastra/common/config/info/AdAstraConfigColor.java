package earth.terrarium.adastra.common.config.info;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColorGradient;

public class AdAstraConfigColor implements ResourcefulConfigColorGradient {

    public static final AdAstraConfigColor INSTANCE = new AdAstraConfigColor();

    @Override
    public String first() {
        return "#7F4DEE";
    }

    @Override
    public String second() {
        return "#E7797A";
    }

    @Override
    public String degree() {
        return "45deg";
    }
}
