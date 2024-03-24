package earth.terrarium.adastra.client.renderers.entities.mobs;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.models.entities.mobs.MartianRaptorModel;
import earth.terrarium.adastra.common.entities.mob.MartianRaptor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class MartianRaptorRenderer extends MobRenderer<MartianRaptor, MartianRaptorModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/mob/martian_raptor.png");

    public MartianRaptorRenderer(EntityRendererProvider.Context context) {
        super(context, new MartianRaptorModel(context.bakeLayer(MartianRaptorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MartianRaptor entity) {
        return TEXTURE;
    }
}
