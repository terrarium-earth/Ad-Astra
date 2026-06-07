package earth.terrarium.adastra.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.client.models.armor.SpaceSuitModel;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.component.DyedItemColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer clientPlayer);

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void adastra$renderHand(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, ModelPart rendererArm, ModelPart rendererArmwear, CallbackInfo ci) {
        var stack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!(stack.getItem() instanceof SpaceSuitItem spaceSuit)) return;
        ci.cancel();

        var playerModel = getModel();
        setModelProperties(player);
        playerModel.attackTime = 0.0F;
        playerModel.crouching = false;
        playerModel.swimAmount = 0.0F;
        playerModel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        rendererArm.xRot = 0.0F;

        var layer = SpaceSuitModel.getLayerLocation(stack);
        var texture = SpaceSuitModel.getTextureLocation(stack);
        if (layer == null || texture == null) return;
        var root = Minecraft.getInstance().getEntityModels().bakeLayer(layer);

        var spaceSuitModel = new SpaceSuitModel(root, EquipmentSlot.CHEST, stack, null);
        boolean isRightHand = rendererArm == spaceSuitModel.rightArm;

        int color = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        if (isRightHand) {
            spaceSuitModel.rightArm.copyFrom(rendererArm);
            spaceSuitModel.rightArm.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), combinedLight, OverlayTexture.NO_OVERLAY, color);
        } else {
            spaceSuitModel.leftArm.copyFrom(rendererArm);
            spaceSuitModel.leftArm.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(texture)), combinedLight, OverlayTexture.NO_OVERLAY, color);
        }
    }
}
