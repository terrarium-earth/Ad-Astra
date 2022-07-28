package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models.CorruptedLunarianEntityModel;
import com.github.alexnijjar.beyond_earth.entities.mobs.CorruptedLunarianEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CorruptedLunarianEntityRenderer extends MobEntityRenderer<CorruptedLunarianEntity, CorruptedLunarianEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/lunarian/corrupted_lunarian.png");

    public CorruptedLunarianEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CorruptedLunarianEntityModel(context.getPart(CorruptedLunarianEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(CorruptedLunarianEntity entity) {
        return TEXTURE;
    }
}
