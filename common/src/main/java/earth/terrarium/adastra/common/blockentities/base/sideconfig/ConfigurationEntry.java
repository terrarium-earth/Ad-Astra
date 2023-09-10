package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ConfigurationEntry(ConfigurationType type, Map<Direction, Configuration> sides) {

    public ConfigurationEntry(ConfigurationType type, Configuration defaultValue) {
        this(type, getDefault(defaultValue));
    }

    public Configuration get(Direction direction) {
        return this.sides.get(direction);
    }

    public void set(Direction direction, Configuration value) {
        this.sides.put(direction, value);
    }


    public static void save(CompoundTag tag, List<ConfigurationEntry> sideConfig) {
        ListTag list = new ListTag();

        for (ConfigurationEntry entry : sideConfig) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putByte("Type", (byte) entry.type.ordinal());

            entry.sides.forEach((direction, configuration) -> {
                CompoundTag sideTag = new CompoundTag();
                sideTag.putByte("Side", (byte) direction.ordinal());
                sideTag.putByte("Configuration", (byte) configuration.ordinal());
                entryTag.put("Side", sideTag);
            });

            list.add(entryTag);
        }

        tag.put("SideConfig", list);
    }

    public static List<ConfigurationEntry> load(CompoundTag tag, List<ConfigurationEntry> sideConfig) {
        ListTag list = tag.getList("SideConfig", Tag.TAG_COMPOUND);

        for (int i = 0; i < list.size(); i++) {
            CompoundTag entryTag = list.getCompound(i);
            ConfigurationType type = ConfigurationType.values()[entryTag.getByte("Type")];

            Map<Direction, Configuration> sides = new HashMap<>();
            CompoundTag sideTag = entryTag.getCompound("Side");
            Direction direction = Direction.values()[sideTag.getByte("Side")];
            Configuration configuration = Configuration.values()[sideTag.getByte("Configuration")];
            sides.put(direction, configuration);

            sideConfig.add(new ConfigurationEntry(type, sides));
        }

        return sideConfig;
    }

    private static Map<Direction, Configuration> getDefault(Configuration value) {
        HashMap<Direction, Configuration> sides = new HashMap<>();
        for (Direction direction : Direction.values()) {
            sides.put(direction, value);
        }
        return sides;
    }
}
