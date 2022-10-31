package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.MartianRaptorEntityModel;
import earth.terrarium.ad_astra.entities.mobs.MartianRaptorEntity;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MartianRaptorEntityRenderer extends MobRenderer<MartianRaptorEntity, MartianRaptorEntityModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/martian_raptor.png");

    public MartianRaptorEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MartianRaptorEntityModel(context.bakeLayer(MartianRaptorEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MartianRaptorEntity entity) {
        return TEXTURE;
    }

}
