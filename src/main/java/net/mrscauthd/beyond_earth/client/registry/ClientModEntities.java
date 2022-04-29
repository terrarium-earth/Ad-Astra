package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mrscauthd.beyond_earth.client.renderer.entity.RocketEntityRenderer;
import net.mrscauthd.beyond_earth.registry.ModEntities;

public class ClientModEntities {

    @Environment(EnvType.CLIENT)
    public static void register() {

        EntityRendererRegistry.register(ModEntities.TIER_1_ROCKET, (context) -> {
            return new RocketEntityRenderer(context);
        });
        EntityRendererRegistry.register(ModEntities.TIER_2_ROCKET, (context) -> {
            return new RocketEntityRenderer(context);
        });
        EntityRendererRegistry.register(ModEntities.TIER_3_ROCKET, (context) -> {
            return new RocketEntityRenderer(context);
        });
        EntityRendererRegistry.register(ModEntities.TIER_4_ROCKET, (context) -> {
            return new RocketEntityRenderer(context);
        });
    }
}