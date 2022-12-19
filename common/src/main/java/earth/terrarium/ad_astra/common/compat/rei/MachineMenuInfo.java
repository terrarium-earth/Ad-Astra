package earth.terrarium.ad_astra.common.compat.rei;

import java.util.function.Function;

import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

public class MachineMenuInfo<MENU extends AbstractMachineMenu<?>, DISPLAY extends Display> implements SimplePlayerInventoryMenuInfo<MENU, DISPLAY> {

    private final DISPLAY display;
    private final Function<DISPLAY, Recipe<?>> display2RecipeFunc;

    public MachineMenuInfo(DISPLAY display, Function<DISPLAY, Recipe<?>> display2RecipeFunc) {
        this.display = display;
        this.display2RecipeFunc = display2RecipeFunc;
    }

    @Override
    public DISPLAY getDisplay() {
        return this.display;
    }

    @Override
    public void markDirty(MenuInfoContext<MENU, ? extends ServerPlayer, DISPLAY> context) {
        SimplePlayerInventoryMenuInfo.super.markDirty(context);
        context.getMenu().onRecipeTransfer(this.display2RecipeFunc.apply(context.getDisplay()));
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<MENU, ?, DISPLAY> context) {
        int inputSlotCount = this.getInputSlotCount(context.getMenu());
        return context.getMenu().slots.subList(0, inputSlotCount).stream().map(SlotAccessor::fromSlot).toList();
    }

    protected int getInputSlotCount(MENU menu) {
        return menu.getMachine().getContainerSize();
    }
}
