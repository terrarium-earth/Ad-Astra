package earth.terrarium.adastra.client.models.entities.mobs;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.AbstractVillager;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class LunarianModel<T extends AbstractVillager> extends VillagerModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "lunarian"), "main");

    public LunarianModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 0).mirror().addBox(-4.5f, -18.0f, -4.5f, 9.0f, 10.0f, 9.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(80, 18).mirror().addBox(-8.0f, -14.0f, -8.0f, 16.0f, 0.0f, 16.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(36, 0).addBox(-4.5f, -18.0f, -4.5f, 9.0f, 10.0f, 9.0f, new CubeDeformation(0.5f)).texOffs(32, 19).addBox(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(0.5f)).texOffs(0, 20).mirror().addBox(-1.0f, -3.0f, -6.0f, 2.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition hat = head.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);
        hat.addOrReplaceChild(PartNames.HAT_RIM, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);
        head.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(0.0f, -12.0f, -5.0f, 8.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 36).mirror().addBox(0.0f, -12.0f, -5.0f, 8.0f, 19.0f, 6.0f, new CubeDeformation(0.5f)).mirror(false).texOffs(28, 36).mirror().addBox(0.0f, -12.0f, -5.0f, 8.0f, 19.0f, 6.0f, new CubeDeformation(0.7f)).mirror(false), PartPose.offset(-4.0f, 12.0f, 2.0f));

        PartDefinition left_leg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 81).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(16, 81).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)).mirror(false), PartPose.offset(2.0f, 12.0f, 0.0f));

        PartDefinition right_leg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 81).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(16, 81).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset(-2.0f, 12.0f, 0.0f));

        PartDefinition arms = modelPartData.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(0, 73).mirror().addBox(-4.0f, 2.1434f, -1.7952f, 8.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 61).mirror().addBox(4.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 61).addBox(-8.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(16, 61).mirror().addBox(4.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new CubeDeformation(0.5f)).mirror(false).texOffs(16, 61).addBox(-8.0f, -1.8566f, -1.7952f, 4.0f, 8.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offsetAndRotation(0.0f, 2.0f, 0.0f, -0.9599f, 0.0f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }
}
