package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.StarCrawlerModel;
import net.mrscauthd.beyond_earth.entities.mobs.StarCrawlerEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class StarCrawlerRenderer extends MobEntityRenderer<StarCrawlerEntity, StarCrawlerModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/starfish.png");
    
    public StarCrawlerRenderer(EntityRendererFactory.Context context) {
        super(context, new StarCrawlerModel(context.getPart(StarCrawlerModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public Identifier getTexture(StarCrawlerEntity entity) {
        return TEXTURE;
    }
    
}
