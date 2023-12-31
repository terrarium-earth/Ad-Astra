package earth.terrarium.adastra.mixins.client;

import earth.terrarium.adastra.client.screens.base.AbstractContainerScreenExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin implements AbstractContainerScreenExtension {

    @Inject(
        method = "renderSlot",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
            shift = At.Shift.AFTER
        )
    )
    private void adastra$renderPreSlot(GuiGraphics graphics, Slot slot, CallbackInfo ci) {
        this.adastra$renderPreSlot(graphics, slot);
    }
}
