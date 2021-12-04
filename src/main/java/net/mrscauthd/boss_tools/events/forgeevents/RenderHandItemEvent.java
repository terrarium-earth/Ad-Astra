package net.mrscauthd.boss_tools.events.forgeevents;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class RenderHandItemEvent extends Event
{
    private LivingEntity livingEntity;
    private ItemStack itemStack;
    private final ItemCameraTransforms.TransformType transformType;
    private HandSide handSide;
    private final MatrixStack matrixStack;
    private final IRenderTypeBuffer renderTypeBuffer;
    private final int light;

    public RenderHandItemEvent(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
    {
        this.livingEntity = livingEntity;
        this.itemStack = itemStack;
        this.transformType = transformType;
        this.handSide = handSide;
        this.matrixStack = matrixStack;
        this.renderTypeBuffer = renderTypeBuffer;
        this.light = light;
    }

    public LivingEntity getLivingEntity()
    {
        return this.livingEntity;
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    public ItemCameraTransforms.TransformType getTransformType()
    {
        return this.transformType;
    }

    public HandSide getHandSide()
    {
        return this.handSide;
    }

    public MatrixStack getMatrixStack()
    {
        return this.matrixStack;
    }

    public IRenderTypeBuffer getRenderTypeBuffer()
    {
        return this.renderTypeBuffer;
    }

    public int getLight()
    {
        return this.light;
    }

    @Cancelable
    public static class Pre extends RenderHandItemEvent
    {
        public Pre(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
        {
            super(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
        }
    }

    public static class Post extends RenderHandItemEvent
    {
        public Post(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
        {
            super(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
        }
    }
}
