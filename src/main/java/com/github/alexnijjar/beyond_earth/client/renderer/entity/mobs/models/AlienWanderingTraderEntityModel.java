package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models;

import com.github.alexnijjar.beyond_earth.entities.mobs.AlienWanderingTraderEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;

public class AlienWanderingTraderEntityModel extends VillagerResemblingModel<AlienWanderingTraderEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("alien_wandering_trader"), "main");

    public AlienWanderingTraderEntityModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head",
                ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(32, 0).mirrored().cuboid(-4.5f, -18.0f, -4.5f, 9.0f, 10.0f, 9.0f, new Dilation(0.0f)).mirrored(false)
                        .uv(96, 0).mirrored().cuboid(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new Dilation(0.5f)).mirrored(false).uv(24, 0).mirrored().cuboid(-1.0f, -3.0f, -6.0f, 2.0f, 4.0f, 2.0f, new Dilation(0.0f)).mirrored(false),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        ModelPartData hat = head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);
        hat.addChild(EntityModelPartNames.HAT_RIM, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);
        head.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 20).mirrored().cuboid(0.0f, -12.0f, -5.0f, 8.0f, 12.0f, 6.0f, new Dilation(0.0f)).mirrored(false).uv(0, 38).mirrored()
                .cuboid(0.0f, -12.0f, -5.0f, 8.0f, 18.0f, 6.0f, new Dilation(0.5f)).mirrored(false).uv(0, 62).mirrored().cuboid(0.0f, -12.0f, -5.0f, 8.0f, 18.0f, 6.0f, new Dilation(0.7f)).mirrored(false), ModelTransform.pivot(-4.0f, 12.0f, 2.0f));

        ModelPartData rightLeg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(2.0f, 12.0f, 0.0f));

        ModelPartData leftLeg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.0f, 12.0f, 0.0f));

        ModelPartData arms = modelPartData.addChild("arms", ModelPartBuilder.create().uv(40, 38).mirrored().cuboid(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(44, 22).mirrored()
                .cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(44, 22).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 3.0f, -0.75f, -0.7854f, 0.0f, 0.0f));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));
        return TexturedModelData.of(modelData, 128, 128);
    }
}
