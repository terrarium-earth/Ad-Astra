package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class SulfurCreeperEntityModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("sulfur_creeper"), "main");

    private final ModelPart body;
    private final ModelPart bb_main;

    public SulfurCreeperEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, -18.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, -18.0f, 0.0f));

        ModelPartData leg0 = body.addChild("leg0", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.0f, -6.0f, 4.0f));

        ModelPartData leg1 = body.addChild("leg1", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(2.0f, -6.0f, 4.0f));

        ModelPartData leg2 = body.addChild("leg2", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.0f, -6.0f, -4.0f));

        ModelPartData leg3 = body.addChild("leg3", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(2.0f, -6.0f, -4.0f));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(50, 9).cuboid(0.0f, -17.0f, 0.0f, 0.0f, 9.0f, 7.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(50, 0).cuboid(14.4317f, -8.4874f, 0.0952f, 6.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(50, 0).cuboid(3.4317f, -7.4874f, -1.9048f, 10.0f, 7.0f, 0.0f, new Dilation(0.0f)),
                ModelTransform.of(11.0f, -17.0f, 2.0f, 0.0983f, 0.0831f, 2.9853f));

        ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(50, 0).cuboid(-5.5f, -4.0f, -4.5f, 10.0f, 7.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(-4.0f, -25.0f, 1.0f, 0.1259f, -0.3419f, 0.4257f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.getChild("head").yaw = headYaw * ((float)Math.PI / 180);
        this.body.getChild("head").pitch = headPitch * ((float)Math.PI / 180);
        this.body.getChild("leg0").pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
        this.body.getChild("leg1").pitch = MathHelper.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;
        this.body.getChild("leg2").pitch = MathHelper.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;
        this.body.getChild("leg3").pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}