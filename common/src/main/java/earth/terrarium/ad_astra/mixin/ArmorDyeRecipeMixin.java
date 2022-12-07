package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.registry.ModItems;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.ArmorDyeRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorDyeRecipe.class)
public abstract class ArmorDyeRecipeMixin {

    @Inject(method = "matches*", at = @At("HEAD"), cancellable = true)
    public void adastra_matches(CraftingContainer craftingInventory, Level level, CallbackInfoReturnable<Boolean> cir) {
        for (int i = 0; i < craftingInventory.getContainerSize(); i++) {
            Item item = craftingInventory.getItem(i).getItem();
            if (item instanceof SpaceSuit) {
                if (item.equals(ModItems.NETHERITE_SPACE_SUIT.get()) || item.equals(ModItems.NETHERITE_SPACE_PANTS.get()) || item.equals(ModItems.NETHERITE_SPACE_BOOTS.get())) {
                    cir.setReturnValue(false);
                }
                if (item.equals(ModItems.JET_SUIT.get()) || item.equals(ModItems.JET_SUIT_PANTS.get()) || item.equals(ModItems.JET_SUIT_BOOTS.get())) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
