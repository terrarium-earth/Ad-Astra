package earth.terrarium.adastra.client.renderers.entities.mobs;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.models.entities.mobs.CorruptedLunarianModel;
import earth.terrarium.adastra.common.entities.mob.CorruptedLunarian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class CorruptedLunarianRenderer extends MobRenderer<CorruptedLunarian, CorruptedLunarianModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/lunarian/corrupted_lunarian.png");

    public CorruptedLunarianRenderer(EntityRendererProvider.Context context) {
        super(context, new CorruptedLunarianModel(context.bakeLayer(CorruptedLunarianModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CorruptedLunarian entity) {
        return TEXTURE;
    }
}
