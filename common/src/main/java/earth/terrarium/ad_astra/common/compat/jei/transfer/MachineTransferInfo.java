package earth.terrarium.ad_astra.common.compat.jei.transfer;

import java.util.List;
import java.util.Optional;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public class MachineTransferInfo<M extends AbstractMachineMenu<? extends AbstractMachineBlockEntity>, RECIPE> implements IRecipeTransferInfo<M, RECIPE> {

    private final Class<M> menuClass;
    private final MenuType<M> menuType;
    private final RecipeType<RECIPE> recipeType;

    public MachineTransferInfo(Class<M> menuClass, MenuType<M> menuType, RecipeType<RECIPE> recipeType) {
        this.menuClass = menuClass;
        this.menuType = menuType;
        this.recipeType = recipeType;
    }

    @Override
    public Class<? extends M> getContainerClass() {
        return this.menuClass;
    }

    @Override
    public Optional<MenuType<M>> getMenuType() {
        return Optional.ofNullable(this.menuType);
    }

    @Override
    public RecipeType<RECIPE> getRecipeType() {
        return this.recipeType;
    }

    @Override
    public boolean canHandle(M container, RECIPE recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(M menu, RECIPE recipe) {
        int inputSlotCount = this.getInputSlotCount(menu);
        return menu.slots.subList(0, inputSlotCount);
    }

    protected int getInputSlotCount(M menu) {
        return menu.getMachine().getContainerSize();
    }

    @Override
    public List<Slot> getInventorySlots(M menu, RECIPE recipe) {
        int containerSize = menu.getMachine().getContainerSize();
        return menu.slots.subList(containerSize, containerSize + 36);
    }

}
