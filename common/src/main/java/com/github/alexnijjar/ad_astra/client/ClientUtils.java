package com.github.alexnijjar.ad_astra.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.NotImplementedException;

@Environment(EnvType.CLIENT)
public class ClientUtils {

    @ExpectPlatform
    public static BakedModel getModel(BakedModelManager dispatcher, Identifier id) {
        throw new NotImplementedException();
    }
}
