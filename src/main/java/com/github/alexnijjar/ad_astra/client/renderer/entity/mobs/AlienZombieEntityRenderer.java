package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.AlienZombieEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.AlienZombieEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

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
