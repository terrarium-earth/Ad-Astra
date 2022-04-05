package net.mrscauthd.beyond_earth.client.renderer.globe;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class GlobeModel extends Model {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("globe"), "main");
    private final ModelPart globe;

    public GlobeModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        globe = root.getChild("globe");
    }

    public static TexturedModelData createLayer() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData globe = modelPartData.addChild("globe", ModelPartBuilder.create().uv(0, 16).cuboid(-7.0f, -16.0f, 1.0f, 8.0f, 12.0f, 0.0f, new Dilation(0.0f)).uv(0, 28).cuboid(-4.0f, -1.0f, -2.0f, 6.0f, 1.0f, 6.0f, new Dilation(0.0f)).uv(0, 35).cuboid(-3.0f, -5.0f, -1.0f, 4.0f, 1.0f, 4.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-2.0f, -4.0f, 0.0f, 2.0f, 3.0f, 2.0f, new Dilation(0.0f)), ModelTransform.pivot(1.0f, 24.0f, -1.0f));

        ModelPartData planet = globe.addChild("planet", ModelPartBuilder.create(), ModelTransform.pivot(-1.0f, -10.0f, 1.0f));

        planet.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        globe.render(matrices, vertices, light, overlay);
    }

    public void setYaw(float yaw) {
        this.globe.getChild("planet").yaw = yaw;
    }

    // Get model from client.
    public static GlobeModel getModel() {
        return new GlobeModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(GlobeModel.LAYER_LOCATION));
    }
}
