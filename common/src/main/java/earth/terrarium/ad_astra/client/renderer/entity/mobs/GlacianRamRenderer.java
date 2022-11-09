package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.GlacianRamModel;
import earth.terrarium.ad_astra.entities.mobs.GlacianRam;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacianRamRenderer extends MobRenderer<GlacianRam, GlacianRamModel<GlacianRam>> {
    private static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/glacian_ram/glacian_ram.png");
    private static final ResourceLocation SHEARED_TEXTURE = new ModResourceLocation("textures/entity/glacian_ram/sheared_glacian_ram.png");

    public GlacianRamRenderer(EntityRendererProvider.Context context) {
        super(context, new GlacianRamModel<>(context.bakeLayer(GlacianRamModel.LAYER_LOCATION)), 0.7f);
    }

    public ResourceLocation getTextureLocation(GlacianRam entity) {
        return entity.isSheared() ? SHEARED_TEXTURE : TEXTURE;
    }
}
