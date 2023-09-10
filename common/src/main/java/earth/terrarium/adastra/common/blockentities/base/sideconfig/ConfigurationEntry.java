package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ConfigurationEntry(ConfigurationType type, Map<Direction, Configuration> sides, Component title) {

    public ConfigurationEntry(ConfigurationType type, Configuration defaultValue, Component title) {
        this(type, getDefault(defaultValue), title);
    }

    public Configuration get(Direction direction) {
        return this.sides.get(direction);
    }

    public void set(Direction direction, Configuration value) {
        this.sides.replace(direction, value);
    }


    public static void save(CompoundTag tag, List<ConfigurationEntry> sideConfig) {
        ListTag list = new ListTag();

        for (ConfigurationEntry entry : sideConfig) {
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

            Map<Direction, Configuration> sides = new HashMap<>();
            for (var direction : Direction.values()) {
                sides.put(direction, Configuration.values()[entryTag.getByte(direction.getName())]);
            }

            sideConfig.add(new ConfigurationEntry(type, sides, defaultConfig.get(i).title()));
        }

        if (sideConfig.isEmpty()) {
            sideConfig.addAll(defaultConfig);
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
