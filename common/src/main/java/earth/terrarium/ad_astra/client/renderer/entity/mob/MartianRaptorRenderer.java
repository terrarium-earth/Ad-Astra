package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.MartianRaptorModel;
import earth.terrarium.ad_astra.common.entity.mob.MartianRaptor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MartianRaptorRenderer extends MobRenderer<MartianRaptor, MartianRaptorModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/martian_raptor.png");

    public MartianRaptorRenderer(EntityRendererProvider.Context context) {
        super(context, new MartianRaptorModel(context.bakeLayer(MartianRaptorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MartianRaptor entity) {
        return TEXTURE;
    }

}
