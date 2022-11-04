package earth.terrarium.ad_astra.client.renderer.block.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class GlobeModel extends Model {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("globe"), "main");
    private final ModelPart globe;

    public GlobeModel(ModelPart root) {
        super(RenderType::entityCutout);
        globe = root.getChild("globe");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition globe = modelPartData.addOrReplaceChild("globe", CubeListBuilder.create().texOffs(0, 16).addBox(-7.0f, -16.0f, 1.0f, 8.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 28).addBox(-4.0f, -1.0f, -2.0f, 6.0f, 1.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(0, 35).addBox(-3.0f, -5.0f, -1.0f, 4.0f, 1.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-2.0f, -4.0f, 0.0f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(1.0f, 24.0f, -1.0f));

        PartDefinition planet = globe.addOrReplaceChild("planet", CubeListBuilder.create(), PartPose.offset(-1.0f, -10.0f, 1.0f));

        planet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        return LayerDefinition.create(modelData, 64, 64);
    }

    // Get model from client.
    public static GlobeModel getModel() {
        return new GlobeModel(Minecraft.getInstance().getEntityModels().bakeLayer(GlobeModel.LAYER_LOCATION));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        globe.render(poseStack, vertices, packedLight, packedOverlay);
    }

    public void setYaw(float yaw) {
        this.globe.getChild("planet").yRot = yaw;
    }
}