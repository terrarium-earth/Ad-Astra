package earth.terrarium.ad_astra.common.compat.jei.transfer;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import java.util.List;
import java.util.Optional;

public class MachineTransferInfo<MENU extends AbstractMachineMenu<? extends AbstractMachineBlockEntity>, RECIPE> implements IRecipeTransferInfo<MENU, RECIPE> {

    private final Class<MENU> menuClass;
    private final MenuType<MENU> menuType;
    private final RecipeType<RECIPE> recipeType;

    public MachineTransferInfo(Class<MENU> menuClass, MenuType<MENU> menuType, RecipeType<RECIPE> recipeType) {
        this.menuClass = menuClass;
        this.menuType = menuType;
        this.recipeType = recipeType;
    }

    @Override
    public Class<? extends MENU> getContainerClass() {
        return this.menuClass;
    }

    @Override
    public Optional<MenuType<MENU>> getMenuType() {
        return Optional.ofNullable(this.menuType);
    }

    @Override
    public RecipeType<RECIPE> getRecipeType() {
        return this.recipeType;
    }

    @Override
    public boolean canHandle(MENU container, RECIPE recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(MENU menu, RECIPE recipe) {
        int inputSlotCount = this.getInputSlotCount(menu);
        return menu.slots.subList(0, inputSlotCount);
    }

    protected int getInputSlotCount(MENU menu) {
        return menu.getMachine().getContainerSize();
    }

    @Override
    public List<Slot> getInventorySlots(MENU menu, RECIPE recipe) {
        int containerSize = menu.getMachine().getContainerSize();
        return menu.slots.subList(containerSize, containerSize + 36);
    }

}
