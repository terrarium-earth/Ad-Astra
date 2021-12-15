package net.mrscauthd.boss_tools.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.SetupFirstPersonAnimEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class SetupFirstPersonAnim {

    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", cancellable = true)
    private void renderRightArm(PoseStack p_104302_, VertexConsumer p_104303_, int p_104304_, int p_104305_, CallbackInfo info) {
        ModelPart w = (ModelPart) ((Object) this);

        MinecraftForge.EVENT_BUS.post(new SetupFirstPersonAnimEvent(w, p_104302_, p_104303_, p_104304_, p_104305_));
    }
}