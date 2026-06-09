package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.menus.content.PositionContent;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class CompressorMenu extends MachineMenu<CompressorBlockEntity> {

    private final ContainerData data;

    public CompressorMenu(int id, Inventory inventory, CompressorBlockEntity entity) {
        super(ModMenus.COMPRESSOR.get(), id, inventory, entity);
        checkContainerDataCount(entity.getDataAccess(), 2);
        this.data = entity.getDataAccess();
        addDataSlots(this.data);
    }

    public CompressorMenu(int id, Inventory inv, Optional<PositionContent> content) {
        this(id, inv, PositionContent.getOrNull(content, inv.player.level(), CompressorBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 3;
    }

    @Override
    protected int getInventoryStart() {
        return 3;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 12;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 114;
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
        addSlot(new Slot(entity, 1, 47, 58));
        addSlot(CustomSlot.noPlace(entity, 2, 95, 58));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 47, 58));
        addConfigSlot(new SlotConfiguration(1, 95, 58));

        addConfigSlot(new EnergyConfiguration(2, 150, 42, entity.getEnergyStorage()));
    }
}
