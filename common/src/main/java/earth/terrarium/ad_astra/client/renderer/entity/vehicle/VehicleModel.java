package earth.terrarium.ad_astra.client.renderer.entity.vehicle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;


public class VehicleModel<T extends Vehicle> extends EntityModel<T> {

    protected final ModelPart frame;

    public VehicleModel(ModelPart root, String child) {
        this.frame = root.getChild(child);
    }

    @Override
    public void setupAnim(T entity, float tickDelta, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float newYaw = Mth.lerp(tickDelta, entity.previousYaw, entity.getYRot());
        this.frame.yRot = (float) Math.toRadians(newYaw);
        this.frame.zRot = 0.0f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        frame.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
