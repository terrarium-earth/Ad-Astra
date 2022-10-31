package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.recipes.ModRecipeType;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    // Hides the annoying "Unknown recipe category" warning
    @Inject(at = @At(value = "HEAD"), method = "getCategory", cancellable = true)
    private static void adastra_getCategory(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> ci) {
        if (recipe.getType() instanceof ModRecipeType) {
            ci.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }
}
