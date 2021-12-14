package net.mrscauthd.boss_tools.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
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
            event.getSpawns().getSpawner(MobCategory.create("monster", "monster", 1, false , false, 128)).add(new MobSpawnSettings.SpawnerData(ModInnet.ALIEN_ZOMBIE.get(), 1, 1, 1));
            event.getSpawns().getSpawner(MobCategory.create("monster", "monster", 2, false , false, 128)).add(new MobSpawnSettings.SpawnerData(ModInnet.STAR_CRAWLER.get(), 1, 1, 1));
        }
    }
}
