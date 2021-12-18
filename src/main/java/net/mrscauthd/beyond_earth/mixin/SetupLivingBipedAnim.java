package net.mrscauthd.beyond_earth.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.forgeevents.SetupLivingBipedAnimEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class SetupLivingBipedAnim {

    @Inject(at = @At(value = "HEAD"), method = "setupAnim", cancellable = true)
    private void setRotationAnglesPre(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        HumanoidModel w = (HumanoidModel) ((Object) this);

        if (MinecraftForge.EVENT_BUS.post(new SetupLivingBipedAnimEvent.Pre(entityIn, w, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch))) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "setupAnim", cancellable = true)
    private void setRotationAnglesPost(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        HumanoidModel w = (HumanoidModel) ((Object) this);

        MinecraftForge.EVENT_BUS.post(new SetupLivingBipedAnimEvent.Post(entityIn, w, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch));
    }
}