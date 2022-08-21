package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models.MoglerEntityModel;
import com.github.alexnijjar.beyond_earth.entities.mobs.ZombifiedMoglerEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ZombifiedMoglerEntityRenderer extends MobEntityRenderer<ZombifiedMoglerEntity, MoglerEntityModel<ZombifiedMoglerEntity>> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/zombified_mogler.png");

    public ZombifiedMoglerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MoglerEntityModel<>(context.getPart(MoglerEntityModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public Identifier getTexture(ZombifiedMoglerEntity entity) {
        return TEXTURE;
    }
}
