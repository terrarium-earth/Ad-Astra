package net.mrscauthd.boss_tools.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.SetupLivingBipedAnimEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.model.BipedModel;

@Mixin(BipedModel.class)
public abstract class SetupLivingBipedAnim {

    @Inject(at = @At(value = "HEAD"), method = "setRotationAngles", cancellable = true)
    private void setRotationAnglesPre(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        BipedModel w = (BipedModel) ((Object) this);

        if (MinecraftForge.EVENT_BUS.post(new SetupLivingBipedAnimEvent.Pre(entityIn, w, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch))) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "setRotationAngles", cancellable = true)
    private void setRotationAnglesPost(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        BipedModel w = (BipedModel) ((Object) this);

        MinecraftForge.EVENT_BUS.post(new SetupLivingBipedAnimEvent.Post(entityIn, w, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch));
    }
}