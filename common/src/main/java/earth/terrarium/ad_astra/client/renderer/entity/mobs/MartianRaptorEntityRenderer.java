package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.MartianRaptorEntityModel;
import earth.terrarium.ad_astra.entities.mobs.MartianRaptor;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MartianRaptorEntityRenderer extends MobRenderer<MartianRaptor, MartianRaptorEntityModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/martian_raptor.png");

    public MartianRaptorEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MartianRaptorEntityModel(context.bakeLayer(MartianRaptorEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MartianRaptor entity) {
        return TEXTURE;
    }

}
