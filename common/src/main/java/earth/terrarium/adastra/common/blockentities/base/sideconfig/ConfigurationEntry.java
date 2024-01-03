package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

import java.util.EnumMap;
import java.util.List;

public record ConfigurationEntry(
    ConfigurationType type,
    EnumMap<Direction, Configuration> sides,
    Component title) {

    public ConfigurationEntry(ConfigurationType type, Configuration defaultValue, Component title) {
        this(type, createConfiguration(defaultValue), title);
    }

    public Configuration get(Direction direction) {
        return this.sides.get(direction);
    }

    public void set(Direction direction, Configuration value) {
        this.sides.replace(direction, value);
    }

    public ConfigurationEntry copy() {
        return new ConfigurationEntry(type, new EnumMap<>(sides), title);
    }


    public static void save(CompoundTag tag, List<ConfigurationEntry> sideConfig) {
        ListTag list = new ListTag();

        for (var entry : sideConfig) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putByte("Type", (byte) entry.type.ordinal());

            entry.sides.forEach((direction, configuration) ->
                entryTag.putByte(direction.getName(), (byte) configuration.ordinal()));

            list.add(entryTag);
        }

        tag.put("SideConfig", list);
    }

    public static List<ConfigurationEntry> load(CompoundTag tag, List<ConfigurationEntry> sideConfig, List<ConfigurationEntry> defaultConfig) {
        ListTag list = tag.getList("SideConfig", Tag.TAG_COMPOUND);

        sideConfig.clear();
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entryTag = list.getCompound(i);
            ConfigurationType type = ConfigurationType.values()[entryTag.getByte("Type")];

            EnumMap<Direction, Configuration> sides = new EnumMap<>(Direction.class);
            for (var direction : Direction.values()) {
                sides.put(direction, Configuration.values()[entryTag.getByte(direction.getName())]);
            }

            sideConfig.add(new ConfigurationEntry(type, sides, defaultConfig.get(i).title()));
        }

        return sideConfig;
    }

    private static EnumMap<Direction, Configuration> createConfiguration(Configuration value) {
        EnumMap<Direction, Configuration> configurations = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            configurations.put(direction, value);
        }
        return configurations;
    }
}
