package earth.terrarium.adastra.common.menus.base;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.menus.configuration.MenuConfiguration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConfigurableContainerMenu<T extends BlockEntity & SideConfigurable> extends BaseContainerMenu<T> {

    private final List<MenuConfiguration> configurations = new ArrayList<>();

    public BaseConfigurableContainerMenu(@Nullable MenuType<?> type, int id, Inventory inventory, T entity) {
        super(type, id, inventory, entity);

        addConfigSlots();
    }

    protected void addConfigSlot(MenuConfiguration configuration) {
        this.configurations.add(configuration);
    }

    public List<MenuConfiguration> getConfigurations() {
        return this.configurations;
    }

    protected abstract void addConfigSlots();
}
