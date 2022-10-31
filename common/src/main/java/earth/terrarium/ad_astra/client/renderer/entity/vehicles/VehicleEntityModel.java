package earth.terrarium.ad_astra.client.renderer.entity.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class VehicleEntityModel<T extends VehicleEntity> extends EntityModel<T> {

    protected final ModelPart frame;

    public VehicleEntityModel(ModelPart root, String child) {
        this.frame = root.getChild(child);
    }

    @Override
   public void setupAnim(T entity, float tickDelta, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float newYaw = Mth.lerp(tickDelta, entity.previousYaw, entity.getYRot());
        this.frame.yRot = (float) Math.toRadians(newYaw);
        this.frame.zRot = 0.0f;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        frame.render(matrices, vertices, light, overlay);
    }
}
