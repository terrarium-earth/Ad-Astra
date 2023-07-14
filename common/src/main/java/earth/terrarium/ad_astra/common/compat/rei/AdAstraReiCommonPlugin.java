package earth.terrarium.ad_astra.common.compat.rei;

import earth.terrarium.ad_astra.common.compat.rei.compressor.CompressorDisplay;
import earth.terrarium.ad_astra.common.compat.rei.cryo_freezer.CryoFreezerConversionDisplay;
import earth.terrarium.ad_astra.common.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import earth.terrarium.ad_astra.common.recipe.CompressingRecipe;
import earth.terrarium.ad_astra.common.recipe.CryoFuelConversionRecipe;
import earth.terrarium.ad_astra.common.recipe.NasaWorkbenchRecipe;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import earth.terrarium.ad_astra.common.screen.menu.CompressorMenu;
import earth.terrarium.ad_astra.common.screen.menu.CryoFreezerMenu;
import earth.terrarium.ad_astra.common.screen.menu.NasaWorkbenchMenu;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoProvider;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Function;

public class AdAstraReiCommonPlugin implements REIServerPlugin {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(REICategories.COMPRESSOR_CATEGORY, new RecipeCodecDisplaySerializer<>(CompressorDisplay::recipe, CompressorDisplay::new, CompressingRecipe::codec));
        registry.register(REICategories.NASA_WORKBENCH_CATEGORY, new RecipeCodecDisplaySerializer<>(NasaWorkbenchDisplay::recipe, NasaWorkbenchDisplay::new, NasaWorkbenchRecipe::codec));
        registry.register(REICategories.CRYO_FREEZER_CONVERSION_CATEGORY, new RecipeCodecDisplaySerializer<>(CryoFreezerConversionDisplay::recipe, CryoFreezerConversionDisplay::new, CryoFuelConversionRecipe::codec));
    }

    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(REICategories.COMPRESSOR_CATEGORY, CompressorMenu.class, this.createMachineMenuInfoProvider(CompressorDisplay::recipe));
        registry.register(REICategories.NASA_WORKBENCH_CATEGORY, NasaWorkbenchMenu.class, this.createMenuInfoProvider(display -> new MachineMenuInfo<>(display, NasaWorkbenchDisplay::recipe) {
            @Override
            protected int getInputSlotCount(NasaWorkbenchMenu menu) {
                return 14;
            }
        }));
        registry.register(REICategories.CRYO_FREEZER_CONVERSION_CATEGORY, CryoFreezerMenu.class, this.createMachineMenuInfoProvider(CryoFreezerConversionDisplay::recipe));
    }

    private <MENU extends AbstractMachineMenu<?>, DISPLAY extends Display> MenuInfoProvider<MENU, DISPLAY> createMenuInfoProvider(Function<DISPLAY, MachineMenuInfo<MENU, DISPLAY>> func) {
        return (SimpleMenuInfoProvider<MENU, DISPLAY>) func::apply;
    }

    private <MENU extends AbstractMachineMenu<?>, DISPLAY extends Display> MenuInfoProvider<MENU, DISPLAY> createMachineMenuInfoProvider(Function<DISPLAY, Recipe<?>> func) {
        return this.createMenuInfoProvider(display -> new MachineMenuInfo<>(display, func));
    }
}
