package net.mrscauthd.beyond_earth.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;

public class ClientMethods {

    public static final ResourceLocation ARM_SPACE_SUIT = new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/arm/space_suit.png");
    public static final ResourceLocation ARM_NETHERITE_SPACE_SUIT = new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/arm/netherite_space_suit.png");

    public static void renderArm(PoseStack poseStack, MultiBufferSource bufferSource, int light, ResourceLocation texture, AbstractClientPlayer player, PlayerModel<AbstractClientPlayer> playermodel, PlayerRenderer renderer, ModelPart arm) {
        renderer.setModelProperties(player);

        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        arm.xRot = 0.0F;

        ItemStack item = player.getItemBySlot(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, 2));

        VertexConsumer vertex = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(texture), false, item.isEnchanted());
        arm.render(poseStack, vertex, light, OverlayTexture.NO_OVERLAY);
    }

    public static boolean checkSound(SoundSource sound) {
        return sound == SoundSource.BLOCKS || sound == SoundSource.NEUTRAL || sound == SoundSource.RECORDS || sound == SoundSource.WEATHER || sound == SoundSource.HOSTILE || sound == SoundSource.PLAYERS || sound == SoundSource.AMBIENT;
    }

    public static void bobView(PoseStack poseStack, float tick) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.getCameraEntity() instanceof Player) {
            Player player = (Player) mc.getCameraEntity();
            float f = player.walkDist - player.walkDistO;
            float f1 = -(player.walkDist + f * tick);
            float f2 = Mth.lerp(tick, 0.075F, -0.075F);
            poseStack.translate((double) (Mth.sin(f1 * (float) Math.PI) * f2 * 0.5F), (double) (-Math.abs(Mth.cos(f1 * (float) Math.PI) * f2)), 0.0D);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f1 * (float) Math.PI) * f2 * 3.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(Math.abs(Mth.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F));
        }
    }

    public static boolean armRenderer(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, PlayerModel<AbstractClientPlayer> playerModel, PlayerRenderer renderer) {
        if (Methods.checkArmor(player, 2, ModInit.SPACE_SUIT.get())) {

            ClientMethods.renderArm(poseStack, multiBufferSource, light, ARM_SPACE_SUIT, player, playerModel, renderer, playerModel.rightArm);
            return true;
        }

        if (Methods.checkArmor(player, 2, ModInit.NETHERITE_SPACE_SUIT.get())) {

            ClientMethods.renderArm(poseStack, multiBufferSource, light, ARM_NETHERITE_SPACE_SUIT, player, playerModel, renderer, playerModel.rightArm);
            return true;
        }

        return false;
    }
}
