package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.items.armour.JetSuit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {

    // Cancel firework boost with jet suit
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void adastra_use(Level level, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (JetSuit.hasFullSet(user)) {
            cir.setReturnValue(InteractionResultHolder.pass(user.getItemInHand(hand)));
        }
    }
}
