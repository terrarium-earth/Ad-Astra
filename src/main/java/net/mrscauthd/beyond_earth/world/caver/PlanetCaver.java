package net.mrscauthd.beyond_earth.world.caver;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlanetCaver {

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			try {
				//WorldCarver.CAVE
				ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CAVE, new ImmutableSet.Builder<Block>()
						.addAll((Set<Block>) ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CAVE, "f_64983_"))
						//Moon
						.add(ModInit.MOON_STONE.get().defaultBlockState().getBlock())
						//Mars
						.add(ModInit.MARS_STONE.get().defaultBlockState().getBlock())
						//Mercury
						.add(ModInit.MERCURY_STONE.get().defaultBlockState().getBlock())
						//Venus
						.add(ModInit.VENUS_STONE.get().defaultBlockState().getBlock()).build(), "f_64983_");
				//WorldCarver.CANYON
				ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CANYON, new ImmutableSet.Builder<Block>()
						.addAll((Set<Block>) ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CANYON, "f_64983_"))
						//Moon
						.add(ModInit.MOON_STONE.get().defaultBlockState().getBlock())
						//Mars
						.add(ModInit.MARS_STONE.get().defaultBlockState().getBlock())
						//Mercury
						.add(ModInit.MERCURY_STONE.get().defaultBlockState().getBlock())
						//venus
						.add(ModInit.VENUS_STONE.get().defaultBlockState().getBlock()).build(), "f_64983_");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
