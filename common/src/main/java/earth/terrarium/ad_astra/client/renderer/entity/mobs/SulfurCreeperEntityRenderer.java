package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.renderer.entity.feature.SulfurCreeperChargeFeatureRenderer;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.SulfurCreeperEntityModel;
import earth.terrarium.ad_astra.entities.mobs.SulfurCreeperEntity;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class SulfurCreeperEntityRenderer extends MobRenderer<SulfurCreeperEntity, SulfurCreeperEntityModel<SulfurCreeperEntity>> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/sulfur_creeper.png");

    public SulfurCreeperEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SulfurCreeperEntityModel<>(context.bakeLayer(SulfurCreeperEntityModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new SulfurCreeperChargeFeatureRenderer(this, context.getModelSet()));
    }

    @Override
    protected void scale(SulfurCreeperEntity creeperEntity, PoseStack poseStack, float f) {
        float g = creeperEntity.getSwelling(f);
        float h = 1.0f + Mth.sin(g * 100.0f) * g * 0.01f;
        g = Mth.clamp(g, 0.0f, 1.0f);
        g *= g;
        g *= g;
        float i = (1.0f + g * 0.4f) * h;
        float j = (1.0f + g * 0.1f) / h;
        poseStack.scale(i, j, i);
    }

    @Override
    protected float getBob(SulfurCreeperEntity creeperEntity, float f) {
        float g = creeperEntity.getSwelling(f);
        if ((int) (g * 10.0f) % 2 == 0) {
            return 0.0f;
        }
        return Mth.clamp(g, 0.5f, 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(SulfurCreeperEntity entity) {
        return TEXTURE;
    }
}
