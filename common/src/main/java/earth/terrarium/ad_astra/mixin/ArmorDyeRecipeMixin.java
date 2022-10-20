package earth.terrarium.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.registry.ModItems;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.recipe.ArmorDyeRecipe;
import net.minecraft.world.World;

@Mixin(ArmorDyeRecipe.class)
public class ArmorDyeRecipeMixin {

    @Inject(method = "matches*", at = @At("HEAD"), cancellable = true)
    public void adastra_matches(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> ci) {
        for (int i = 0; i < craftingInventory.size(); i++) {
            Item item = craftingInventory.getStack(i).getItem();
            if (item instanceof SpaceSuit) {
                if (item.equals(ModItems.NETHERITE_SPACE_SUIT.get()) || item.equals(ModItems.NETHERITE_SPACE_PANTS.get()) || item.equals(ModItems.NETHERITE_SPACE_BOOTS.get())) {
                    ci.setReturnValue(false);
                }
                if (item.equals(ModItems.JET_SUIT.get()) || item.equals(ModItems.JET_SUIT_PANTS.get()) || item.equals(ModItems.JET_SUIT_BOOTS.get())) {
                    ci.setReturnValue(false);
                }
            }
        }
    }
}
