package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.GlacianRamEntityModel;
import earth.terrarium.ad_astra.entities.mobs.GlacianRam;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacianRamEntityRenderer extends MobRenderer<GlacianRam, GlacianRamEntityModel<GlacianRam>> {
    private static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/glacian_ram/glacian_ram.png");
    private static final ResourceLocation SHEARED_TEXTURE = new ModResourceLocation("textures/entity/glacian_ram/sheared_glacian_ram.png");

    public GlacianRamEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new GlacianRamEntityModel<>(context.bakeLayer(GlacianRamEntityModel.LAYER_LOCATION)), 0.7f);
    }

    public ResourceLocation getTextureLocation(GlacianRam entity) {
        return entity.isSheared() ? SHEARED_TEXTURE : TEXTURE;
    }
}
