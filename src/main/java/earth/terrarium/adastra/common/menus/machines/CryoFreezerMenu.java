package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.menus.content.PositionContent;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class CryoFreezerMenu extends MachineMenu<CryoFreezerBlockEntity> {

    private final ContainerData data;

    public CryoFreezerMenu(int id, Inventory inventory, CryoFreezerBlockEntity entity) {
        super(ModMenus.CRYO_FREEZER.get(), id, inventory, entity);
        checkContainerDataCount(entity.getDataAccess(), 2);
        this.data = entity.getDataAccess();
        addDataSlots(this.data);
    }

    public CryoFreezerMenu(int id, Inventory inv, Optional<PositionContent> content) {
        this(id, inv, PositionContent.getOrNull(content, inv.player.level(), CryoFreezerBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 4;
    }

    @Override
    protected int getInventoryStart() {
        return 4;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 102;
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
        addSlot(new Slot(entity, 1, 26, 70));
//        addSlot(PredicateSlot.ofRecipeInput(entity, 1, 26, 70, this.level, ModRecipeTypes.CRYO_FREEZING.get()));

        addSlot(new Slot(entity, 2, 113, 42));
        addSlot(CustomSlot.noPlace(entity, 3, 113, 70));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 26, 70));
        addConfigSlot(new SlotConfiguration(1, 113, 42));
        addConfigSlot(new SlotConfiguration(2, 113, 70));

        addConfigSlot(new EnergyConfiguration(3, 149, 27, entity.getEnergyStorage()));
        addConfigSlot(new FluidConfiguration(4, 86, 38, entity.getFluidContainer(), 0));
    }
}
