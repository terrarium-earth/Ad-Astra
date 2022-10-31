package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;

@Environment(EnvType.CLIENT)
public class PygroEntityModel<T extends AbstractPiglin> extends PiglinModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("pygro"), "main");

    public PygroEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition getModelData() {
        MeshDefinition modelData = PygroEntityModel.createMesh(CubeDeformation.NONE);
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0f, -8.0f, -4.0f, 10.0f, 8.0f, 8.0f, CubeDeformation.NONE).texOffs(31, 1).addBox(-2.0f, -4.0f, -5.0f, 4.0f, 4.0f, 1.0f, CubeDeformation.NONE).texOffs(2, 4).addBox(2.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, CubeDeformation.NONE).texOffs(2, 0).addBox(-3.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, CubeDeformation.NONE), PartPose.ZERO);

        // Left Ear.
        modelPartData1.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(39, 6).addBox(0.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, CubeDeformation.NONE), PartPose.offsetAndRotation(4.5f, -6.0f, 0.0f, 0.0f, 0.0f, (-(float) Math.PI / 6f)));

        // eyes.
        PartDefinition eyes = modelPartData1.addOrReplaceChild("eyesg", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
        eyes.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(46, 0).addBox(-4.5f, -4.5f, -0.75f, 9.0f, 7.0f, 0.0f, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0f, -7.5f, -4.0f, 0.3054f, 0.0f, 0.0f));

        // nose 1.
        PartDefinition fang = modelPartData1.addOrReplaceChild("noseg1", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));
        fang.addOrReplaceChild("nose1", CubeListBuilder.create().texOffs(33, 2).addBox(-1.25f, -1.35f, -0.5f, 3.0f, 3.0f, 1.0f, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0f, -25.0f, -4.5f, 0.0631f, 0.3435f, 0.1855f));

        // nose 2.
        PartDefinition fang2 = modelPartData1.addOrReplaceChild("noseg2", CubeListBuilder.create(), PartPose.offset(4.5f, 24.0f, 0.0f));
        fang2.addOrReplaceChild("nose2", CubeListBuilder.create().texOffs(33, 2).addBox(-2.15f, -1.45f, -0.35f, 3.0f, 3.0f, 1.0f, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.0f, -25.0f, -4.5f, 0.0631f, -0.3435f, -0.1855f));

        return LayerDefinition.create(modelData, 64, 64);
    }
}