package earth.terrarium.ad_astra.client.renderer.entity.mob;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.StarCrawlerModel;
import earth.terrarium.ad_astra.common.entity.mob.StarCrawler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class StarCrawlerRenderer extends MobRenderer<StarCrawler, StarCrawlerModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/star_crawler.png");

    public StarCrawlerRenderer(EntityRendererProvider.Context context) {
        super(context, new StarCrawlerModel(context.bakeLayer(StarCrawlerModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(StarCrawler entity) {
        return TEXTURE;
    }

}
