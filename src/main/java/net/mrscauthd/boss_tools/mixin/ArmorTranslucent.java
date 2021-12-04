package net.mrscauthd.boss_tools.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.mrscauthd.boss_tools.BossToolsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorTranslucent<T extends LivingEntity, A extends HumanoidModel<T>> {

    private static ResourceLocation SpaceSuit = new ResourceLocation(BossToolsMod.ModId, "textures/models/armor/space_suit_head.png");
    private static ResourceLocation NetheriteSpaceSuit = new ResourceLocation(BossToolsMod.ModId, "textures/models/armor/netherite_space_suit_head.png");

    @Inject(at = @At(value = "HEAD"), method = "renderModel", cancellable = true, remap = false)
    private void renderModel(PoseStack p_241738_1_, MultiBufferSource p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource, CallbackInfo info) {

        if (armorResource.equals(SpaceSuit) || armorResource.equals(NetheriteSpaceSuit)) {
            info.cancel();
            VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_241738_2_, RenderType.entityTranslucent(armorResource), false, p_241738_5_);
            p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
        }

    }

}