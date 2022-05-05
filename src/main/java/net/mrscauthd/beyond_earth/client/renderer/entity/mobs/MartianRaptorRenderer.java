package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MartianRaptorModel;
import net.mrscauthd.beyond_earth.entities.mobs.MartianRaptorEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class MartianRaptorRenderer extends MobEntityRenderer<MartianRaptorEntity, MartianRaptorModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/martian_raptor.png");

    public MartianRaptorRenderer(EntityRendererFactory.Context context) {
        super(context, new MartianRaptorModel(context.getPart(MartianRaptorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(MartianRaptorEntity entity) {
        return TEXTURE;
    }

}
