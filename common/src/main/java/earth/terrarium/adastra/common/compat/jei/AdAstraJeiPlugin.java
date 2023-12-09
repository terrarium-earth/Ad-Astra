package earth.terrarium.adastra.common.compat.jei;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.SeparatorScreen;
import earth.terrarium.adastra.common.compat.jei.categories.SeparatorCategory;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@JeiPlugin
public class AdAstraJeiPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(AdAstra.MOD_ID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new SeparatorCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Objects.requireNonNull(Minecraft.getInstance().level);

        registration.addRecipes(SeparatorCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SEPARATING.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.SEPARATOR.get().getDefaultInstance(), SeparatorCategory.RECIPE);
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SeparatorScreen.class, SeparatorScreen.CLICK_AREA.getX(), SeparatorScreen.CLICK_AREA.getY(), SeparatorScreen.CLICK_AREA.getWidth(), SeparatorScreen.CLICK_AREA.getHeight(), SeparatorCategory.RECIPE);
    }
}
