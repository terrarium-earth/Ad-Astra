package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MoglerModel;
import net.mrscauthd.beyond_earth.entities.mobs.MoglerEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class MoglerRenderer extends MobEntityRenderer<MoglerEntity, MoglerModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/mogler_entity.png");
    
    public MoglerRenderer(EntityRendererFactory.Context context) {
        super(context, new MoglerModel(context.getPart(MoglerModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public Identifier getTexture(MoglerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected boolean isShaking(MoglerEntity entity) {
        return super.isShaking(entity) || entity.canConvert();
    }
}
