package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MartianRaptorEntityModel;
import net.mrscauthd.beyond_earth.entities.mobs.MartianRaptorEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class MartianRaptorEntityRenderer extends MobEntityRenderer<MartianRaptorEntity, MartianRaptorEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/martian_raptor.png");

    public MartianRaptorEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MartianRaptorEntityModel(context.getPart(MartianRaptorEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(MartianRaptorEntity entity) {
        return TEXTURE;
    }

}
