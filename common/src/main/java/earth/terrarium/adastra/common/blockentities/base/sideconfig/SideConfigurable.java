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
     * Resets the side configuration of the block entity to the default configuration.
     */
    default void resetToDefault(int index) {
        List<ConfigurationEntry> entries = this.getSideConfig();
        entries.get(index).sides().clear();
        entries.get(index).sides().putAll(this.getDefaultConfig().get(index).sides());
    }
}
