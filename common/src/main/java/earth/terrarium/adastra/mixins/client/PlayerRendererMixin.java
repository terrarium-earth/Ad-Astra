package earth.terrarium.adastra.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.renderers.items.base.CustomGeoArmorRenderer;
import earth.terrarium.adastra.common.items.base.CustomGeoArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.cache.object.BakedGeoModel;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    @Shadow
    protected abstract void setupRotations(AbstractClientPlayer entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks);

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void adastra$renderHand(PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, ModelPart rendererArm, ModelPart rendererArmwear, CallbackInfo ci) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.getItem() instanceof CustomGeoArmorItem spaceSuit) {
            ci.cancel();
            boolean isRightHand = rendererArm == this.model.rightArm;

            CustomGeoArmorRenderer renderer = (CustomGeoArmorRenderer) AdAstraClient.getArmorRenderer(spaceSuit);

            ResourceLocation texture = renderer.getTextureLocation(spaceSuit);
            RenderType renderType = renderer.getRenderType(spaceSuit, texture, buffer, Minecraft.getInstance().getDeltaFrameTime());
            float partialTicks = Minecraft.getInstance().getDeltaFrameTime();
            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
            BakedGeoModel model = renderer.getGeoModel().getBakedModel(renderer.getGeoModel().getModelResource(spaceSuit));

            renderer.prepForRender(Minecraft.getInstance().player, chestplate, EquipmentSlot.CHEST, this.model);
            renderer.preRender(matrixStack, spaceSuit, model, buffer, vertexConsumer, true, partialTicks, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            renderer.getBodyBone().setHidden(true);
            renderer.getLeftArmBone().setHidden(isRightHand);
            renderer.getRightArmBone().setHidden(!isRightHand);

            renderer.actuallyRender(matrixStack, spaceSuit, model, renderType, buffer, vertexConsumer, true, partialTicks, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
