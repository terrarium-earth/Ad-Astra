package earth.terrarium.adastra.mixins.client.multipart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.common.entities.multipart.MultipartEntity;
import earth.terrarium.adastra.common.entities.multipart.MultipartPartEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(
        method = "renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;FFFF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/phys/AABB;FFFF)V",
            ordinal = 0,
            shift = At.Shift.AFTER
        )
    )
    private static void adastra$renderHitbox(PoseStack poseStack, VertexConsumer buffer, Entity entity, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if (entity instanceof MultipartEntity multipartEntity) {
            double d = -Mth.lerp(red, entity.xOld, entity.getX());
            double e = -Mth.lerp(red, entity.yOld, entity.getY());
            double f = -Mth.lerp(red, entity.zOld, entity.getZ());

            for (MultipartPartEntity<?> part : multipartEntity.getMultiParts()) {
                Entity asEntity = (Entity) part;
                poseStack.pushPose();
                double g = d + Mth.lerp(red, asEntity.xOld, asEntity.getX());
                double h = e + Mth.lerp(red, asEntity.yOld, asEntity.getY());
                double i = f + Mth.lerp(red, asEntity.zOld, asEntity.getZ());
                poseStack.translate(g, h, i);
                LevelRenderer.renderLineBox(poseStack, buffer, asEntity.getBoundingBox().move(-asEntity.getX(), -asEntity.getY(), -asEntity.getZ()), 0.25F, 1.0F, 0.0F, 1.0F);
                poseStack.popPose();
            }
        }
    }
}
