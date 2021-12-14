package net.mrscauthd.boss_tools.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.RenderPlayerArmEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class RenderPlayerArm {

    @Inject(at = @At(value = "HEAD"), method = "renderRightHand", cancellable = true)
    private void renderRightArm(PoseStack p_117771_, MultiBufferSource p_117772_, int p_117773_, AbstractClientPlayer p_117774_, CallbackInfo info) {
        PlayerRenderer w = (PlayerRenderer) ((Object) this);

        PlayerModel<AbstractClientPlayer> playerModel = w.getModel();

        if (MinecraftForge.EVENT_BUS.post(new RenderPlayerArmEvent.RightArm(p_117771_, p_117772_, p_117773_, p_117774_, HumanoidArm.RIGHT, playerModel))) {
            info.cancel();
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "renderLeftHand", cancellable = true)
    private void renderLeftArm(PoseStack p_117814_, MultiBufferSource p_117815_, int p_117816_, AbstractClientPlayer p_117817_, CallbackInfo info) {
        PlayerRenderer w = (PlayerRenderer) ((Object) this);

        PlayerModel<AbstractClientPlayer> playerModel = w.getModel();

        if (MinecraftForge.EVENT_BUS.post(new RenderPlayerArmEvent.LeftArm(p_117814_, p_117815_, p_117816_, p_117817_, HumanoidArm.LEFT, playerModel))) {
            info.cancel();
        }
    }
}