package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import java.util.List;

public interface SideConfigurable {

    /**
     * Gets the side configuration of the block entity.
     *
     * @return The side configuration of the block entity.
     */
    List<ConfigurationEntry> getSideConfig();

    /**
     * Gets the default side configuration of the block entity. This should be cached by the block entity.
     *
     * @return The default side configuration of the block entity.
     */
    List<ConfigurationEntry> getDefaultConfig();

    /**
     * Creates a deep copy of the default configuration.
     *
     * @return A deep copy of the default configuration.
     */
    default List<ConfigurationEntry> createDefaultConfig() {
        return getDefaultConfig().stream().map(ConfigurationEntry::copy).toList();
    }

    /**
     * Resets the side configuration of the block entity to the default configuration.
     */
    default void resetToDefault(int index) {
        List<ConfigurationEntry> entries = getSideConfig();
        if (entries.size() <= index) return;
        var sides = entries.get(index).sides();
        sides.clear();
        sides.putAll(getDefaultConfig().get(index).sides());
    }
}
