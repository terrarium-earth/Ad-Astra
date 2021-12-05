package net.mrscauthd.boss_tools.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AtlassEvent {
    @SubscribeEvent
    public static void AtlasEvent(TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation(BossToolsMod.ModId, "entities/tile_entity_box_oxygen_generator"));
    }
}
