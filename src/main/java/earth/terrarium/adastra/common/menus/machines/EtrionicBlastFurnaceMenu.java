package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
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

public class EtrionicBlastFurnaceMenu extends MachineMenu<EtrionicBlastFurnaceBlockEntity> {

    private final ContainerData data;

    public EtrionicBlastFurnaceMenu(int id, Inventory inventory, EtrionicBlastFurnaceBlockEntity entity) {
        super(ModMenus.ETRIONIC_BLAST_FURNACE.get(), id, inventory, entity);
        checkContainerDataCount(entity.getDataAccess(), 2);
        this.data = entity.getDataAccess();
        addDataSlots(this.data);
    }

    public EtrionicBlastFurnaceMenu(int id, Inventory inv, Optional<PositionContent> content) {
        this(id, inv, PositionContent.getOrNull(content, inv.player.level(), EtrionicBlastFurnaceBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 8;
    }

    @Override
    protected int getInventoryStart() {
        return 8;
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
        addSlot(new Slot(entity, 1, 29, 38));
        addSlot(new Slot(entity, 2, 47, 38));
        addSlot(new Slot(entity, 3, 29, 58));
        addSlot(new Slot(entity, 4, 47, 58));

        addSlot(CustomSlot.noPlace(entity, 5, 101, 38));
        addSlot(CustomSlot.noPlace(entity, 6, 119, 38));
        addSlot(CustomSlot.noPlace(entity, 7, 101, 58));
        addSlot(CustomSlot.noPlace(entity, 8, 119, 58));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 29, 38, 34, 36));
        addConfigSlot(new SlotConfiguration(1, 101, 38, 34, 36));

        addConfigSlot(new EnergyConfiguration(2, 152, 35, entity.getEnergyStorage()));
    }
}
