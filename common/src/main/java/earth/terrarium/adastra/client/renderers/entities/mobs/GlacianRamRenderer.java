package earth.terrarium.adastra.client.renderers.entities.mobs;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.models.entities.mobs.GlacianRamModel;
import earth.terrarium.adastra.common.entities.mob.GlacianRam;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class GlacianRamRenderer extends MobRenderer<GlacianRam, GlacianRamModel<GlacianRam>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/glacian_ram/glacian_ram.png");
    private static final ResourceLocation SHEARED_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/glacian_ram/sheared_glacian_ram.png");

    public GlacianRamRenderer(EntityRendererProvider.Context context) {
        super(context, new GlacianRamModel<>(context.bakeLayer(GlacianRamModel.LAYER_LOCATION)), 0.7f);
    }

    public @NotNull ResourceLocation getTextureLocation(GlacianRam entity) {
        return entity.isSheared() ? SHEARED_TEXTURE : TEXTURE;
    }
}
