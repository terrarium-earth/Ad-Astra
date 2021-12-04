package net.mrscauthd.boss_tools.events;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId)
public class MobSpawnEvents {

    @SubscribeEvent
    public static void addLivingEntityToBiomes(BiomeLoadingEvent event) {
        if (event.getName().equals(new ResourceLocation(BossToolsMod.ModId, "moon"))) {
            event.getSpawns().getSpawner(EntityClassification.create("monster", "monster", 2, false , false, 128)).add(new MobSpawnInfo.Spawners(ModInnet.ALIEN_ZOMBIE.get(), 1, 1, 1));
            event.getSpawns().getSpawner(EntityClassification.create("monster", "monster", 4, false , false, 128)).add(new MobSpawnInfo.Spawners(ModInnet.STAR_CRAWLER.get(), 1, 1, 1));
        }
    }
}
