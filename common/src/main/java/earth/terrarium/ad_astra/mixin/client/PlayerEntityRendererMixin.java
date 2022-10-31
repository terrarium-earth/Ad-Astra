package earth.terrarium.ad_astra.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.client.renderer.armour.JetSuitModel;
import earth.terrarium.ad_astra.client.renderer.armour.NetheriteSpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.armour.SpaceSuitModel;
import earth.terrarium.ad_astra.items.armour.JetSuit;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.items.vehicles.VehicleItem;
import earth.terrarium.ad_astra.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "renderRightHand", at = @At("HEAD"), cancellable = true)
    public void adastra_renderRightHand(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer player, CallbackInfo ci) {
        if (player.getOffhandItem().getItem() instanceof VehicleItem) {
            ci.cancel();
            return;
        }
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {
            ci.cancel();
            this.adastra_renderArm(matrices, vertexConsumers, light, player, true);
        }
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"), cancellable = true)
    public void adastra_renderLeftHand(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer player, CallbackInfo ci) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {
            ci.cancel();
            this.adastra_renderArm(matrices, vertexConsumers, light, player, false);
        }
    }

    // Render space suit arm in first person
    @SuppressWarnings({"unchecked", "rawtypes"})

    @Unique
    private void adastra_renderArm(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer player, boolean right) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (stack.getItem() instanceof SpaceSuit) {

            PlayerRenderer renderer = (PlayerRenderer) (Object) (this);

            EntityModelSet modelLoader = Minecraft.getInstance().getEntityModels();
            SpaceSuitModel model;

            if (stack.is(ModItems.JET_SUIT.get())) {
                model = new SpaceSuitModel(modelLoader.bakeLayer(JetSuitModel.LAYER_LOCATION), (HumanoidModel) renderer.getModel(), player, EquipmentSlot.CHEST, stack);
            } else if (stack.is(ModItems.NETHERITE_SPACE_SUIT.get())) {
                model = new SpaceSuitModel(modelLoader.bakeLayer(NetheriteSpaceSuitModel.LAYER_LOCATION), (HumanoidModel) renderer.getModel(), player, EquipmentSlot.CHEST, stack);
            } else {
                model = new SpaceSuitModel(modelLoader.bakeLayer(SpaceSuitModel.LAYER_LOCATION), (HumanoidModel) renderer.getModel(), player, EquipmentSlot.CHEST, stack);
            }

            matrices.pushPose();
            matrices.mulPose(Vector3f.ZP.rotationDegrees(4));

            int decimal = ((SpaceSuit) stack.getItem()).getColor(stack);
            float r = (float) (decimal >> 16 & 0xFF) / 255.0f;
            float g = (float) (decimal >> 8 & 0xFF) / 255.0f;
            float b = (float) (decimal & 0xFF) / 255.0f;

            if (JetSuit.hasFullSet(player)) {
                JetSuit.spawnParticles(player.level, player, model);
            }

            VertexConsumer vertex = SpaceSuitModel.getVertex(RenderType.entityTranslucent(model.getTextureLocation()), player.getItemBySlot(EquipmentSlot.CHEST).isEnchanted());
            if (right) {
                model.rightArm.render(matrices, vertex, light, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
            } else {
                model.leftArm.render(matrices, vertex, light, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
            }
            matrices.popPose();
        }
    }
}
