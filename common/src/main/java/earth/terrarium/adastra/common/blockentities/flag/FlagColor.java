package earth.terrarium.adastra.common.blockentities.flag;

public enum FlagColor {
    NONE(0x000000),
    WHITE(0xFFFFFF),
    ORANGE(0xD87F33),
    MAGENTA(0xB24CD8),
    LIGHT_BLUE(0x6699D8),
    YELLOW(0xE5E533),
    LIME(0x7FCC19),
    PINK(0xF27FA5),
    GRAY(0x4C4C4C),
    LIGHT_GRAY(0x999999),
    CYAN(0x4C7F99),
    PURPLE(0x7F3FB2),
    BLUE(0x334CB2),
    BROWN(0x664C33),
    GREEN(0x667F33),
    RED(0x993333),
    BLACK(0x191919);

    private final int color;

    FlagColor(int color) {
        this.color = color;
    }

    public int color() {
        return color;
    }

    public static FlagColor[] fromBytes(byte[] bytes) {
        FlagColor[] colors = new FlagColor[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            colors[i * 2] = FlagColor.values()[bytes[i] >> 4];
            colors[i * 2 + 1] = FlagColor.values()[bytes[i] & 0b00001111];
        }
        return colors;
    }
}
