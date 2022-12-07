package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rover;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import earth.terrarium.ad_astra.entity.vehicle.Rover;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class RoverRenderer extends VehicleRenderer<Rover, RoverModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/vehicles/tier_1_rover.png");

    public RoverRenderer(EntityRendererProvider.Context context) {
        super(context, new RoverModel(context.bakeLayer(RoverModel.LAYER_LOCATION)), 1.0f);
    }

    @Override
    public void render(Rover entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, yaw, tickDelta, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Rover entity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(Rover entity, Frustum frustum, double x, double y, double z) {
        return frustum.isVisible(entity.getBoundingBox().inflate(4));
    }
}
