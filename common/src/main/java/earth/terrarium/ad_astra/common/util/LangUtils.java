package earth.terrarium.ad_astra.common.util;

import java.util.HashMap;
import java.util.Map;

public class LangUtils {
    public static final Map<String, String> LANG_MAP = new HashMap<>();

    public static final String MAIN = lang("itemGroup.ad_astra.main", "Ad Astra");

    public static final String EMPTY_TANK = lang("tooltip.ad_astra.empty_tank", "Empty Tank");
    public static final String ENERGY_BAR = lang("tooltip.ad_astra.energy_bar", "%s ⚡ /§a %s ⚡");
    public static final String FLUID_TANK = lang("tooltip.ad_astra.fluid_tank", "%s 🪣 / %s 🪣 %s");
    public static final String FLUID_PIPE_TRANSDER_RATE = lang("tooltip.ad_astra.fluid_pipe_transfer_rate", "Fluid Transfer: %s 🪣 / t");
    public static final String CABLE_TRANSFER_RATE = lang("tooltip.ad_astra.cable_transfer_rate", "Energy Transfer: %s ⚡ / t");

    public static final String PIPE_NORMAL = lang("message.ad_astra.pipe_normal", "Normal");
    public static final String PIPE_INSERT = lang("message.ad_astra.pipe_insert", "Insert");
    public static final String PIPE_EXTRACT = lang("message.ad_astra.pipe_extract", "Extract");
    public static final String PIPE_NONE = lang("message.ad_astra.pipe_none", "None");

    public static final String CAPACITOR_MODE = lang("message.ad_astra.capacitor_mode", "Mode: %s");
    public static final String CAPACITOR_ON = lang("message.ad_astra.capacitor_on", "Capacitor On");
    public static final String CAPACITOR_OFF = lang("message.ad_astra.capacitor_off", "Capacitor Off");

    private static String lang(String key, String value) {
        LANG_MAP.put(key, value);
        return key;
    }
}