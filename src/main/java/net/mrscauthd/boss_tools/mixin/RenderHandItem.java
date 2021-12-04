package net.mrscauthd.boss_tools.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.RenderHandItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemLayer.class)
public abstract class RenderHandItem {

    @Inject(at = @At(value = "HEAD"), method = "func_229135_a_", cancellable = true)
    private void setRotationAnglesPre(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int p_229135_7_, CallbackInfo info) {

        if (MinecraftForge.EVENT_BUS.post(new RenderHandItemEvent.Pre(p_229135_1_, p_229135_2_, p_229135_3_, p_229135_4_, p_229135_5_, p_229135_6_, p_229135_7_))) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "func_229135_a_", cancellable = true)
    private void setRotationAnglesPost(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int p_229135_7_, CallbackInfo info) {

        MinecraftForge.EVENT_BUS.post(new RenderHandItemEvent.Post(p_229135_1_, p_229135_2_, p_229135_3_, p_229135_4_, p_229135_5_, p_229135_6_, p_229135_7_));
    }
}