package net.mrscauthd.beyond_earth.client.renderer.spacesuit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.registry.ModArmour;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class SpaceSuitModel {

    @Environment(EnvType.CLIENT)
    static final MinecraftClient MINECRAFT_CLIENT = MinecraftClient.getInstance();

    @Environment(EnvType.CLIENT)
    public static class SPACE_SUIT_P1<T extends LivingEntity> extends BipedEntityModel<T> {

        private static final Identifier SPACE_SUIT = new ModIdentifier("textures/models/armor/space_suit_head.png");
        private static final Identifier NETHERITE_SPACE_SUIT = new ModIdentifier("textures/models/armor/netherite_space_suit_head.png");

        public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("space_suit_p1"), "main");

        public BipedEntityModel<LivingEntity> model;
        public ItemStack stack;

        public final ModelPart head;
        public final ModelPart body;
        public final ModelPart rightArm;
        public final ModelPart leftArm;
        public final ModelPart rightLeg;
        public final ModelPart leftLeg;


        public SPACE_SUIT_P1(ModelPart root) {
            super(new EntityRendererFactory.Context(
                    MINECRAFT_CLIENT.getEntityRenderDispatcher(),
                    MINECRAFT_CLIENT.getItemRenderer(),
                    MINECRAFT_CLIENT.getResourceManager(),
                    MINECRAFT_CLIENT.getEntityModelLoader(),
                    MINECRAFT_CLIENT.textRenderer).getPart(EntityModelLayers.PLAYER_INNER_ARMOR)
            );

            this.head = root.getChild("head");
            this.body = root.getChild("body");
            this.rightArm = root.getChild("right_arm");
            this.leftArm = root.getChild("left_arm");
            this.rightLeg = root.getChild("right_leg");
            this.leftLeg = root.getChild("left_leg");
        }

        public static TexturedModelData createBodyLayer() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();

            modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.5f))
                    .uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.75f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.0175f, 0.0873f, 0.0f));

            modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.25f))
                    .uv(28, 28).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.25f))
                    .uv(50, 29).cuboid(-3.0f, 5.0f, -2.5f, 6.0f, 4.0f, 1.0f, new Dilation(0.25f))
                    .uv(0, 55).cuboid(-2.5f, 1.0f, 2.55f, 5.0f, 8.0f, 1.0f, new Dilation(0.75f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

            modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(20, 44).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.26f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

            modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(32, 0).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.26f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

            modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(48, 44).cuboid(-2.0f, 6.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.4f))
                    .uv(48, 54).cuboid(-2.0f, 6.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.27f)), ModelTransform.pivot(-2.0f, 12.0f, 0.0f));

            modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(48, 44).cuboid(-2.0f, 6.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.4f))
                    .uv(48, 54).cuboid(-2.0f, 6.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.27f)), ModelTransform.pivot(2.0f, 12.0f, 0.0f));

            return TexturedModelData.of(modelData, 64, 64);
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

            this.handSwingProgress = this.model.handSwingProgress;
            this.riding = this.model.riding;
            this.child = this.model.child;
            this.leftArmPose = this.model.leftArmPose;
            this.rightArmPose = this.model.rightArmPose;
            this.sneaking = this.model.sneaking;
            this.head.copyTransform(this.model.head);
            this.body.copyTransform(this.model.body);
            this.rightArm.copyTransform(this.model.rightArm);
            this.leftArm.copyTransform(this.model.leftArm);
            this.rightLeg.copyTransform(this.model.rightLeg);
            this.leftLeg.copyTransform(this.model.leftLeg);

            this.body.render(matrices, vertices, light, overlay);
            this.rightArm.render(matrices, vertices, light, overlay);
            this.leftArm.render(matrices, vertices, light, overlay);
            this.rightLeg.render(matrices, vertices, light, overlay);
            this.leftLeg.render(matrices, vertices, light, overlay);

            if (stack.getItem().equals(ModArmour.OXYGEN_MASK)) {
                this.head.render(matrices, getVertex(RenderLayer.getEntityTranslucent(SPACE_SUIT), stack.hasEnchantments()), light, overlay);
            } else if (stack.getItem().equals(ModArmour.NETHERITE_OXYGEN_MASK)) {
                this.head.render(matrices, getVertex(RenderLayer.getEntityTranslucent(NETHERITE_SPACE_SUIT), stack.hasEnchantments()), light, overlay);
            } else {
                this.head.render(matrices, vertices, light, overlay);
            }
        }

        public static VertexConsumer getVertex(RenderLayer renderLayer, boolean hasEnchantments) {
            VertexConsumerProvider vertexConsumerProvider = MINECRAFT_CLIENT.getBufferBuilders().getEntityVertexConsumers();
            return hasEnchantments ? VertexConsumers.union(vertexConsumerProvider.getBuffer(RenderLayer.getEntityGlint()), vertexConsumerProvider.getBuffer(renderLayer)) : vertexConsumerProvider.getBuffer(renderLayer);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class SPACE_SUIT_P2<T extends LivingEntity> extends BipedEntityModel<T> {

        public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("space_suit_p2"), "main");

        public BipedEntityModel<LivingEntity> model;

        public final ModelPart rightLeg;
        public final ModelPart leftLeg;

        public SPACE_SUIT_P2(ModelPart root) {
            super(new EntityRendererFactory.Context(
                    MINECRAFT_CLIENT.getEntityRenderDispatcher(),
                    MINECRAFT_CLIENT.getItemRenderer(),
                    MINECRAFT_CLIENT.getResourceManager(),
                    MINECRAFT_CLIENT.getEntityModelLoader(),
                    MINECRAFT_CLIENT.textRenderer).getPart(EntityModelLayers.PLAYER_INNER_ARMOR)
            );
            this.rightLeg = root.getChild("right_leg");
            this.leftLeg = root.getChild("left_leg");
        }

        public static TexturedModelData createBodyLayer() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();

            modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));

            modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)).mirrored(false), ModelTransform.pivot(1.9f, 12.0f, 0.0f));

            return TexturedModelData.of(modelData, 64, 32);
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

            this.handSwingProgress = this.model.handSwingProgress;
            this.riding = this.model.riding;
            this.child = this.model.child;
            this.leftArmPose = this.model.leftArmPose;
            this.rightArmPose = this.model.rightArmPose;
            this.sneaking = this.model.sneaking;
            this.head.copyTransform(this.model.head);
            this.body.copyTransform(this.model.body);
            this.rightArm.copyTransform(this.model.rightArm);
            this.leftArm.copyTransform(this.model.leftArm);
            this.rightLeg.copyTransform(this.model.rightLeg);
            this.leftLeg.copyTransform(this.model.leftLeg);

            this.rightLeg.render(matrices, vertices, light, overlay);
            this.leftLeg.render(matrices, vertices, light, overlay);
        }
    }
}
