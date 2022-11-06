package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityModel;
import earth.terrarium.ad_astra.entities.vehicles.Rover;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class RoverEntityModel extends VehicleEntityModel<Rover> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("tier_1_rover"), "main");

    public RoverEntityModel(ModelPart root) {
        super(root, "Frame");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition frame = modelPartData.addOrReplaceChild("Frame", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0f, -10.0f, -16.0f, 30.0f, 3.0f, 43.0f, new CubeDeformation(0.0f)).texOffs(88, 64).addBox(-18.0f, -9.6f, -17.0f, 36.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(88, 64).addBox(-18.0f, -9.6f, 25.0f, 36.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(0, 46).addBox(-11.0f, -13.0f, -29.0f, 22.0f, 3.0f, 22.0f, new CubeDeformation(0.0f)).texOffs(66, 53).addBox(6.0f, -24.0f, -3.0f, 4.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(7.0f, -22.0f, -17.0f, 8.0f, 12.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(139, 28).addBox(6.0f, -20.0f, -10.0f, 10.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(139, 28).addBox(-16.0f, -20.0f, -10.0f, 10.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-15.0f, -22.0f, -17.0f, 8.0f, 12.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(103, 0).addBox(-9.0f, -35.0f, -23.0f, 2.0f, 22.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(103, 24).addBox(-14.0f, -14.0f, 4.0f, 12.0f, 2.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(32, 24)
                .addBox(-9.0f, -23.0f, 17.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(0, 71).addBox(-15.0f, -33.0f, 21.0f, 30.0f, 23.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(66, 46).addBox(-15.0f, -12.0f, 23.0f, 30.0f, 2.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(64, 71).addBox(-9.0f, -22.0f, 24.0f, 18.0f, 10.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(0, 96).addBox(-9.0f, -24.0f, 24.0f, 18.0f, 2.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(32, 24).addBox(7.0f, -23.0f, 17.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(103, 24).addBox(2.0f, -14.0f, 4.0f, 12.0f, 2.0f, 12.0f, new CubeDeformation(0.0f)), PartPose.offset(2.0f, 24.0f, -4.0f));

        PartDefinition cube_r1 = frame.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 101).addBox(2.5f, -1.0f, -10.0f, 12.0f, 2.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(50, 101).addBox(-13.5f, -1.0f, -10.0f, 12.0f, 2.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -23.0f, 18.0f, 1.2217f, 0.0f, 0.0f));

        PartDefinition cube_r2 = frame.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 55).addBox(-5.0f, -5.0f, -0.5f, 10.0f, 6.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -21.0f, 1.5f, 0.2182f, 0.0f, 0.0f));

        PartDefinition cube_r3 = frame.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 71).addBox(-1.0f, -3.5f, -3.0f, 2.0f, 12.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -17.5f, 0.0f, -0.2618f, 0.0f, 0.0f));

        PartDefinition Wheels = frame.addOrReplaceChild("Wheels", CubeListBuilder.create(), PartPose.offset(-16.0f, 0.0f, 21.0f));

        PartDefinition Wheelframe = Wheels.addOrReplaceChild("Wheelframe", CubeListBuilder.create(), PartPose.offset(-2.0f, -8.0f, 5.5f));

        PartDefinition bone18 = Wheelframe.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(126, 0).addBox(-1.5f, -11.9355f, -1.3787f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(-1.5f, 17.763f, -31.0772f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(33.5f, 17.763f, -31.0772f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(33.5f, -11.9355f, -1.3787f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, -4.0f, -0.7854f, 0.0f, 0.0f));

        PartDefinition bone19 = Wheelframe.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(126, 0).addBox(-1.5f, 6.2787f, -7.0355f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(-1.5f, 35.9772f, 22.663f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(33.5f, 35.9772f, 22.663f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(33.5f, 6.2787f, -7.0355f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, -4.0f, -2.3562f, 0.0f, 0.0f));

        PartDefinition bone20 = Wheelframe.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5f, 0.0f, -4.0f, -1.5708f, 0.0f, 0.0f));

        PartDefinition bone21 = Wheelframe.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(126, 0).addBox(-1.5f, -9.9f, 0.5f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(-1.5f, -9.9f, -41.5f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(33.5f, -9.9f, -41.5f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(126, 0).addBox(33.5f, -9.9f, 0.5f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offset(-0.5f, 0.5f, -4.0f));

        PartDefinition Wheel1 = Wheels.addOrReplaceChild("Wheel1", CubeListBuilder.create(), PartPose.offset(34.0f, -8.0f, -36.5f));

        PartDefinition bone10 = Wheel1.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5f, 0.0f, 0.0f, -0.7854f, 0.0f, 0.0f));

        PartDefinition bone9 = Wheel1.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5f, 0.0f, 0.0f, -2.3562f, 0.0f, 0.0f));

        PartDefinition bone8 = Wheel1.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5f, 0.0f, 0.0f, -1.5708f, 0.0f, 0.0f));

        PartDefinition bone7 = Wheel1.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.9f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 3.9f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(0, 37).addBox(1.5f, -5.1f, -4.5f, 0.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(-2.5f, 0.5f, 0.0f));

        PartDefinition Wheel2 = Wheels.addOrReplaceChild("Wheel2", CubeListBuilder.create(), PartPose.offset(-2.0f, -8.0f, -36.5f));

        PartDefinition bone2 = Wheel2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, 0.0f, -0.7854f, 0.0f, 0.0f));

        PartDefinition bone3 = Wheel2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, 0.0f, -2.3562f, 0.0f, 0.0f));

        PartDefinition bone4 = Wheel2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, 0.0f, -1.5708f, 0.0f, 0.0f));

        PartDefinition bone5 = Wheel2.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.9f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 3.9f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(0, 37).addBox(1.5f, -5.1f, -4.5f, 0.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(-0.5f, 0.5f, 0.0f));

        PartDefinition Wheel3 = Wheels.addOrReplaceChild("Wheel3", CubeListBuilder.create(), PartPose.offset(34.0f, -8.0f, 5.5f));

        PartDefinition bone6 = Wheel3.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -11.2284f, -0.6716f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 1.5716f, -0.6716f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5f, 0.0f, -4.0f, -0.7854f, 0.0f, 0.0f));

        PartDefinition bone11 = Wheel3.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -11.2284f, -6.3284f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 1.5716f, -6.3284f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5f, 0.0f, -4.0f, -2.3562f, 0.0f, 0.0f));

        PartDefinition bone12 = Wheel3.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -12.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 0.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.5f, 0.0f, -4.0f, -1.5708f, 0.0f, 0.0f));

        PartDefinition bone13 = Wheel3.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.9f, 0.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 3.9f, 0.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(0, 37).addBox(1.5f, -5.1f, -0.5f, 0.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(-2.5f, 0.5f, -4.0f));

        PartDefinition Wheel4 = Wheels.addOrReplaceChild("Wheel4", CubeListBuilder.create(), PartPose.offset(-2.0f, -8.0f, 5.5f));

        PartDefinition bone14 = Wheel4.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -11.2284f, -0.6716f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 1.5716f, -0.6716f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, -4.0f, -0.7854f, 0.0f, 0.0f));

        PartDefinition bone15 = Wheel4.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -11.2284f, -6.3284f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 1.5716f, -6.3284f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, -4.0f, -2.3562f, 0.0f, 0.0f));

        PartDefinition bone16 = Wheel4.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -12.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 0.4f, -3.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, 0.0f, -4.0f, -1.5708f, 0.0f, 0.0f));

        PartDefinition bone17 = Wheel4.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(111, 0).addBox(-0.5f, -8.9f, 0.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(111, 0).addBox(-0.5f, 3.9f, 0.5f, 4.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(0, 37).addBox(1.5f, -5.1f, -0.5f, 0.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(-0.5f, 0.5f, -4.0f));

        PartDefinition Antenna = frame.addOrReplaceChild("Antenna", CubeListBuilder.create().texOffs(28, 0).addBox(10.9319f, -0.6943f, 0.7497f, 1.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(4.9319f, -1.1943f, 0.2497f, 1.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 40).addBox(-6.0681f, 0.8057f, 1.7497f, 17.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(66, 46).addBox(0.9319f, -0.1943f, 0.2497f, 4.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 8).addBox(4.9319f, -7.1943f, -5.7503f, 0.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-12.0f, -35.275f, -22.0f, 0.0f, 0.6545f, -0.3927f));

        PartDefinition cube_r4 = Antenna.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 40).addBox(-8.5f, 0.0f, -0.5f, 17.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(2.4319f, 0.8057f, 2.2497f, -1.6144f, 0.0f, 0.0f));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
   public void setupAnim(Rover entity, float tickDelta, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setupAnim(entity, tickDelta, limbDistance, animationProgress, headYaw, headPitch);
        this.frame.x = 0.0f;
        this.frame.y = 24.0f;
        this.frame.z = 0.0f;

        float wheelPitch = (float) Mth.lerp(tickDelta, entity.prevWheelPitch / 3.0, entity.wheelPitch / 3.0);

        ModelPart wheels = this.frame.getChild("Wheels");
        wheels.getChild("Wheel1").xRot = wheelPitch;
        wheels.getChild("Wheel2").xRot = wheelPitch;
        wheels.getChild("Wheel3").xRot = wheelPitch;
        wheels.getChild("Wheel4").xRot = wheelPitch;

        float speed = Mth.sign(entity.getSpeed());
        float wheelYaw = Mth.clamp((float) Math.toRadians(speed * entity.getTurnSpeed() * 5), -0.5f, 0.5f);
        wheels.getChild("Wheel1").yRot = wheelYaw;
        wheels.getChild("Wheel2").yRot = wheelYaw;
    }
}
