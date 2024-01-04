package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Unique
    private int adastra$dismountTicks;

    @Inject(method = "wantsToStopRiding", at = @At("HEAD"), cancellable = true)
    private void adastra$wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
        var player = (Player) (Object) this;
        if (player.getVehicle() instanceof Vehicle vehicle) {
            if (player.isShiftKeyDown() && !vehicle.isSafeToDismount(player)) {
                adastra$dismountTicks++;
                if (adastra$dismountTicks < 40) {
                    cir.setReturnValue(false);
                    float seconds = Math.round((40 - adastra$dismountTicks) / 20.0f * 10) / 10f;
                    player.displayClientMessage(Component.translatable("message.ad_astra.hold_to_dismount", seconds).withStyle(ChatFormatting.RED), true);
                } else {
                    player.displayClientMessage(CommonComponents.EMPTY, true);
                    adastra$dismountTicks = 0;
                }
            } else if (adastra$dismountTicks > 0) {
                player.displayClientMessage(CommonComponents.EMPTY, true);
                adastra$dismountTicks = 0;
            }
        }
    }
}
