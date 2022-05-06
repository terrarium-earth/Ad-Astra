package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.AlienZombieEntityModel;
import net.mrscauthd.beyond_earth.entities.mobs.AlienZombieEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class AlienZombieEntityRenderer extends MobEntityRenderer<AlienZombieEntity, AlienZombieEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/alien_zombie.png");

    public AlienZombieEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AlienZombieEntityModel(context.getPart(AlienZombieEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(AlienZombieEntity entity) {
        return TEXTURE;
    }
}
