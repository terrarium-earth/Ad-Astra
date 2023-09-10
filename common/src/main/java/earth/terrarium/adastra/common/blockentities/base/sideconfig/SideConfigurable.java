package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import java.util.List;

public interface SideConfigurable {

    List<ConfigurationEntry> getConfigurableEntries();

    List<ConfigurationEntry> defaultConfig();
}
