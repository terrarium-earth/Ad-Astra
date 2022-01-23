package net.mrscauthd.beyond_earth.entity.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.entity.RoverEntity;
import net.mrscauthd.beyond_earth.entity.VehicleEntity;

@OnlyIn(Dist.CLIENT)
public abstract class VehicleRenderer<T extends VehicleEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
    protected M model;
    protected final List<RenderLayer<T, M>> layers = Lists.newArrayList();

    public VehicleRenderer(EntityRendererProvider.Context p_174289_, M p_174290_, float p_174291_) {
        super(p_174289_);
        this.model = p_174290_;
        this.shadowRadius = p_174291_;
    }

    public final boolean addLayer(RenderLayer<T, M> p_115327_) {
        return this.layers.add(p_115327_);
    }

    public M getModel() {
        return this.model;
    }

    public void render(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        p_115311_.pushPose();

        boolean shouldSit = p_115308_.isPassenger() && (p_115308_.getVehicle() != null && p_115308_.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        float f = Mth.rotLerp(p_115310_, p_115308_.yRotO, p_115308_.getYRot());
        float f1 = Mth.rotLerp(p_115310_, p_115308_.yRotO, p_115308_.getYRot());
        float f2 = f1 - f;
        if (shouldSit && p_115308_.getVehicle() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)p_115308_.getVehicle();
            f = Mth.rotLerp(p_115310_, livingentity.yBodyRotO, livingentity.yBodyRot);
            f2 = f1 - f;
            float f3 = Mth.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = Mth.lerp(p_115310_, p_115308_.xRotO, p_115308_.getXRot());

        float f7 = this.getBob(p_115308_, p_115310_);
        this.setupRotations(p_115308_, p_115311_, f7, f, p_115310_);
        p_115311_.scale(-1.0F, -1.0F, 1.0F);
        this.scale(p_115308_, p_115311_, p_115310_);
        p_115311_.translate(0.0D, (double)-1.501F, 0.0D);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (p_115308_ instanceof RoverEntity) {
            f8 = Mth.lerp(p_115310_, ((RoverEntity) p_115308_).animationSpeedOld, ((RoverEntity) p_115308_).animationSpeed);
            f5 = ((RoverEntity) p_115308_).animationPosition - ((RoverEntity) p_115308_).animationSpeed * (1.0F - p_115310_);

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.model.prepareMobModel(p_115308_, f5, f8, p_115310_);
        this.model.setupAnim(p_115308_, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isBodyVisible(p_115308_);
        boolean flag1 = !flag && !p_115308_.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(p_115308_);
        RenderType rendertype = this.getRenderType(p_115308_, flag, flag1, flag2);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = p_115312_.getBuffer(rendertype);
            int i = getOverlayCoords(p_115308_, this.getWhiteOverlayProgress(p_115308_, p_115310_));
            this.model.renderToBuffer(p_115311_, vertexconsumer, p_115313_, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }

        if (!p_115308_.isSpectator()) {
            for(RenderLayer<T, M> renderlayer : this.layers) {
                renderlayer.render(p_115311_, p_115312_, p_115313_, p_115308_, f5, f8, p_115310_, f7, f2, f6);
            }
        }

        p_115311_.popPose();
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }

    @Nullable
    protected RenderType getRenderType(T p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation resourcelocation = this.getTextureLocation(p_115322_);
        if (p_115324_) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (p_115323_) {
            return this.model.renderType(resourcelocation);
        } else {
            return p_115325_ ? RenderType.outline(resourcelocation) : null;
        }
    }

    protected boolean isBodyVisible(T p_115341_) {
        return !p_115341_.isInvisible();
    }

    protected boolean isShaking(T p_115304_) {
        return p_115304_.isFullyFrozen();
    }

    public static int getOverlayCoords(Entity p_115339_, float p_115340_) {
        return OverlayTexture.pack(OverlayTexture.u(p_115340_), OverlayTexture.v(false));
    }

    protected float getWhiteOverlayProgress(T p_115334_, float p_115335_) {
        return 0.0F;
    }

    protected void setupRotations(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
        if (this.isShaking(p_115317_)) {
            p_115320_ += (float)(Math.cos((double)p_115317_.tickCount * 3.25D) * Math.PI * (double)0.4F);
        }

        Pose pose = p_115317_.getPose();
        if (pose != Pose.SLEEPING) {
            p_115318_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_115320_));
        }
    }

    protected float getBob(T p_115305_, float p_115306_) {
        return (float)p_115305_.tickCount + p_115306_;
    }

    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
    }
}