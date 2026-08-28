package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.SolarPanelBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.content.PositionContent;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class SolarPanelMenu extends MachineMenu<SolarPanelBlockEntity> {

    public SolarPanelMenu(int id, Inventory inventory, SolarPanelBlockEntity entity) {
        super(ModMenus.SOLAR_PANEL.get(), id, inventory, entity);
    }

    public SolarPanelMenu(int id, Inventory inv, Optional<PositionContent> content) {
        this(id, inv, PositionContent.getOrNull(content, inv.player.level(), SolarPanelBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 2;
    }

    @Override
    protected int getInventoryStart() {
        return 2;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 148;
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new EnergyConfiguration(0, 108, 69, entity.getEnergyStorage()));
    }
}
