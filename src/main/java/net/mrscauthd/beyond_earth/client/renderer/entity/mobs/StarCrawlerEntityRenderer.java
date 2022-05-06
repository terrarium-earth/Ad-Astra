package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.StarCrawlerEntityModel;
import net.mrscauthd.beyond_earth.entities.mobs.StarCrawlerEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class StarCrawlerEntityRenderer extends MobEntityRenderer<StarCrawlerEntity, StarCrawlerEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/starfish.png");
    
    public StarCrawlerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new StarCrawlerEntityModel(context.getPart(StarCrawlerEntityModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public Identifier getTexture(StarCrawlerEntity entity) {
        return TEXTURE;
    }
    
}
