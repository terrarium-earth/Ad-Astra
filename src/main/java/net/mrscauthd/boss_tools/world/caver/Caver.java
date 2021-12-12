package net.mrscauthd.boss_tools.world.caver;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Caver {

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			try {
				//WorldCarver.CAVE
				ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CAVE, new ImmutableSet.Builder<Block>()
						.addAll((Set<Block>) ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CAVE, "replaceableBlocks"))
						//Moon
						.add(ModInnet.MOON_STONE.get().defaultBlockState().getBlock())
						//Mars
						.add(ModInnet.MARS_STONE.get().defaultBlockState().getBlock())
						//Mercury
						.add(ModInnet.MERCURY_STONE.get().defaultBlockState().getBlock())
						//Venus
						.add(ModInnet.VENUS_STONE.get().defaultBlockState().getBlock()).build(), "replaceableBlocks");
				//WorldCarver.CANYON
				ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CANYON, new ImmutableSet.Builder<Block>()
						.addAll((Set<Block>) ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CANYON, "replaceableBlocks"))
						//Moon
						.add(ModInnet.MOON_STONE.get().defaultBlockState().getBlock())
						//Mars
						.add(ModInnet.MARS_STONE.get().defaultBlockState().getBlock())
						//Mercury
						.add(ModInnet.MERCURY_STONE.get().defaultBlockState().getBlock())
						//venus
						.add(ModInnet.VENUS_STONE.get().defaultBlockState().getBlock()).build(), "replaceableBlocks");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
