package net.mrscauthd.beyond_earth.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlassEvent {
    @SubscribeEvent
    public static void AtlasEvent(TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation(BeyondEarthMod.MODID, "entities/tile_entity_box_oxygen_generator"));
    }
}
