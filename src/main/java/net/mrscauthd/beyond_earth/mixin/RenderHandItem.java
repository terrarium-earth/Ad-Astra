package net.mrscauthd.beyond_earth.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.events.forgeevents.RenderHandItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class RenderHandItem {

    @Inject(at = @At(value = "HEAD"), method = "renderArmWithItem", cancellable = true)
    private void setRotationAnglesPre(LivingEntity p_117185_, ItemStack p_117186_, ItemTransforms.TransformType p_117187_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_, CallbackInfo info) {

        if (MinecraftForge.EVENT_BUS.post(new RenderHandItemEvent.Pre(p_117185_, p_117186_, p_117187_, p_117188_, p_117189_, p_117190_, p_117191_))) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "renderArmWithItem", cancellable = true)
    private void setRotationAnglesPost(LivingEntity p_117185_, ItemStack p_117186_, ItemTransforms.TransformType p_117187_, HumanoidArm p_117188_, PoseStack p_117189_, MultiBufferSource p_117190_, int p_117191_, CallbackInfo info) {

        MinecraftForge.EVENT_BUS.post(new RenderHandItemEvent.Post(p_117185_, p_117186_, p_117187_, p_117188_, p_117189_, p_117190_, p_117191_));
    }
}