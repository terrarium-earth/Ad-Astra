package com.github.alexnijjar.ad_astra.client.forge;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;

public class ClientUtilsImpl {
    public static BakedModel getModel(BakedModelManager dispatcher, Identifier id) {
        return dispatcher.getModel(id);
    }
}
