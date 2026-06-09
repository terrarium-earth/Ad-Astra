package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CoalGeneratorBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.menus.content.PositionContent;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class CoalGeneratorMenu extends MachineMenu<CoalGeneratorBlockEntity> {

    private final ContainerData data;

    public CoalGeneratorMenu(int id, Inventory inventory, CoalGeneratorBlockEntity entity) {
        super(ModMenus.COAL_GENERATOR.get(), id, inventory, entity);
        checkContainerDataCount(entity.getDataAccess(), 2);
        this.data = entity.getDataAccess();
        addDataSlots(this.data);
    }

    public CoalGeneratorMenu(int id, Inventory inv, Optional<PositionContent> content) {
        this(id, inv, PositionContent.getOrNull(content, inv.player.level(), CoalGeneratorBlockEntity.class));
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
        return 107;
    }

    public int cookTime() {
        return data.get(0);
    }

    public int cookTimeTotal() {
        return data.get(1);
    }

    @Override
    protected void addMenuSlots() {
        super.addMenuSlots();
        addSlot(new Slot(entity, 1, 77, 71));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 77, 71));
        addConfigSlot(new EnergyConfiguration(1, 146, 32, entity.getEnergyStorage()));
    }
}
