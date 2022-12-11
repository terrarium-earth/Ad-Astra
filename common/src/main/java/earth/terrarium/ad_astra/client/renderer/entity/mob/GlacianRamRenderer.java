package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.GlacianRamModel;
import earth.terrarium.ad_astra.common.entity.mob.GlacianRam;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GlacianRamRenderer extends MobRenderer<GlacianRam, GlacianRamModel<GlacianRam>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/glacian_ram/glacian_ram.png");
    private static final ResourceLocation SHEARED_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/glacian_ram/sheared_glacian_ram.png");

    public GlacianRamRenderer(EntityRendererProvider.Context context) {
        super(context, new GlacianRamModel<>(context.bakeLayer(GlacianRamModel.LAYER_LOCATION)), 0.7f);
    }

    public @NotNull ResourceLocation getTextureLocation(GlacianRam entity) {
        return entity.isSheared() ? SHEARED_TEXTURE : TEXTURE;
    }
}
