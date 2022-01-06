package net.mrscauthd.beyond_earth.entity.renderer.iceshard;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.IceSpitEntity;

@OnlyIn(Dist.CLIENT)
public class IceShardRenderer extends EntityRenderer<IceSpitEntity> {
    private final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(BeyondEarthMod.MODID, "textures/entities/ice_shard.png");
    private final IceShardModel<IceSpitEntity> model;

    public IceShardRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        this.model = new IceShardModel<>(renderManagerIn.bakeLayer(IceShardModel.LAYER_LOCATION));
    }

    @Override
    public void render(IceSpitEntity p_114528_, float p_114529_, float p_114530_, PoseStack p_114531_, MultiBufferSource p_114532_, int p_114533_) {
        p_114531_.pushPose();
        p_114531_.scale(0.5f,0.5f,0.5f);
        p_114531_.translate(0, -0.5f, 0);
        VertexConsumer vertexconsumer = p_114532_.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(p_114531_, vertexconsumer, p_114533_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_114531_.popPose();

        super.render(p_114528_, p_114529_, p_114530_, p_114531_, p_114532_, p_114533_);
    }

    @Override
    public ResourceLocation getTextureLocation(IceSpitEntity p_114482_) {
        return TEXTURE_LOCATION;
    }
}