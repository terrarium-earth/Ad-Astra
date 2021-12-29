package net.mrscauthd.beyond_earth.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorTranslucent<T extends LivingEntity, A extends HumanoidModel<T>> {

    private static ResourceLocation SpaceSuit = new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/space_suit_head.png");
    private static ResourceLocation NetheriteSpaceSuit = new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/netherite_space_suit_head.png");

    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IZLnet/minecraft/client/model/HumanoidModel;FFFLnet/minecraft/resources/ResourceLocation;)V", cancellable = true, remap = false)
    private void renderModel(PoseStack p_117107_, MultiBufferSource p_117108_, int p_117109_, boolean p_117111_, A p_117112_, float p_117114_, float p_117115_, float p_117116_, ResourceLocation armorResource, CallbackInfo info) {

        if (armorResource.equals(SpaceSuit) || armorResource.equals(NetheriteSpaceSuit)) {
            info.cancel();
            VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_117108_, RenderType.entityTranslucent(armorResource), false, p_117111_);
            p_117112_.renderToBuffer(p_117107_, ivertexbuilder, p_117109_, OverlayTexture.NO_OVERLAY, p_117114_, p_117115_, p_117116_, 1.0F);
        }
    }
}