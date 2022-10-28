package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.recipes.ModRecipeType;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    // Hides the annoying "Unknown recipe category" warning
    @Inject(at = @At(value = "HEAD"), method = "getGroupForRecipe", cancellable = true)
    private static void adastra_getGroupForRecipe(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> ci) {
        if (recipe.getType() instanceof ModRecipeType) {
            ci.setReturnValue(RecipeBookGroup.UNKNOWN);
        }
    }
}
