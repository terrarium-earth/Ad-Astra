package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MoglerEntityModel;
import net.mrscauthd.beyond_earth.entities.mobs.MoglerEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class MoglerEntityRenderer extends MobEntityRenderer<MoglerEntity, MoglerEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/mogler_entity.png");
    
    public MoglerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MoglerEntityModel(context.getPart(MoglerEntityModel.LAYER_LOCATION)), 0.7f);
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
