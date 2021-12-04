package net.mrscauthd.boss_tools.mixin;

import net.mrscauthd.boss_tools.BossToolsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

@Mixin(BipedArmorLayer.class)
public abstract class ArmorTranslucent<T extends LivingEntity, A extends BipedModel<T>> {

    private static ResourceLocation SpaceSuit = new ResourceLocation(BossToolsMod.ModId, "textures/models/armor/space_suit_head.png");
    private static ResourceLocation NetheriteSpaceSuit = new ResourceLocation(BossToolsMod.ModId, "textures/models/armor/netherite_space_suit_head.png");

    @Inject(at = @At(value = "HEAD"), method = "renderModel", cancellable = true, remap = false)
    private void renderModel(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource, CallbackInfo info) {

        if (armorResource.equals(SpaceSuit) || armorResource.equals(NetheriteSpaceSuit)) {
            info.cancel();
            IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(p_241738_2_, RenderType.getEntityTranslucent(armorResource), false, p_241738_5_);
            p_241738_6_.render(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
        }

    }

}