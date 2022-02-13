package net.mrscauthd.beyond_earth.events.forgeevents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class RenderHandItemEvent extends Event {
    private LivingEntity livingEntity;
    private ItemStack itemStack;
    private final ItemTransforms.TransformType transformType;
    private HumanoidArm handSide;
    private final PoseStack poseStack;
    private final MultiBufferSource renderTypeBuffer;
    private final int light;

    public RenderHandItemEvent(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
        this.livingEntity = livingEntity;
        this.itemStack = itemStack;
        this.transformType = transformType;
        this.handSide = handSide;
        this.poseStack = matrixStack;
        this.renderTypeBuffer = renderTypeBuffer;
        this.light = light;
    }

    public LivingEntity getLivingEntity() {
        return this.livingEntity;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public ItemTransforms.TransformType getTransformType() {
        return this.transformType;
    }

    public HumanoidArm getHandSide() {
        return this.handSide;
    }

    public PoseStack getMatrixStack() {
        return this.poseStack;
    }

    public MultiBufferSource getRenderTypeBuffer() {
        return this.renderTypeBuffer;
    }

    public int getLight() {
        return this.light;
    }

    @Cancelable
    public static class Pre extends RenderHandItemEvent {
        public Pre(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
            super(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
        }
    }

    public static class Post extends RenderHandItemEvent {
        public Post(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
            super(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
        }
    }
}
