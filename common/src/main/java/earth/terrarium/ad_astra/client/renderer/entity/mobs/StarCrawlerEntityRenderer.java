package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.StarCrawlerEntityModel;
import earth.terrarium.ad_astra.entities.mobs.StarCrawlerEntity;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StarCrawlerEntityRenderer extends MobRenderer<StarCrawlerEntity, StarCrawlerEntityModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/entity/star_crawler.png");

    public StarCrawlerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new StarCrawlerEntityModel(context.bakeLayer(StarCrawlerEntityModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(StarCrawlerEntity entity) {
        return TEXTURE;
    }

}
