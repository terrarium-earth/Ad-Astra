package earth.terrarium.adastra.common.systems;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.material.FluidState;

public class EnvironmentEffects {

    public static void tickChunk(ServerLevel level, LevelChunk chunk) {
        LevelChunkSection[] chunkSections = chunk.getSections();
        var chunkPos = chunk.getPos();
        int chunkX = chunkPos.getMinBlockX();
        int chunkZ = chunkPos.getMinBlockZ();

        for (int i = 0; i < chunkSections.length; i++) {
            var section = chunkSections[i];
            if (!section.isRandomlyTicking()) continue;
            int yCord = SectionPos.sectionToBlockCoord(chunk.getSectionYFromSectionIndex(i));

            for (int j = 0; j < AdAstraConfig.planetRandomTickSpeed; j++) {
                BlockPos pos = level.getBlockRandomPos(chunkX, yCord, chunkZ, 15);

                BlockState state = section.getBlockState(
                    pos.getX() - chunkX,
                    pos.getY() - yCord,
                    pos.getZ() - chunkZ
                );

                if (state.isAir()) continue;
                short temperature = TemperatureApi.API.getTemperature(level, pos);
                if (AdAstraEvents.environmentTick(level, pos, state, temperature)) {
                    if (temperature > PlanetConstants.MAX_LIVEABLE_TEMPERATURE) {
                        tickHot(level, pos, state);
                    } else if (temperature < PlanetConstants.FREEZE_TEMPERATURE) {
                        tickCold(level, pos, state);
                    }
                    tickBlock(level, pos, state);
                }
            }
        }
    }

    private static void tickBlock(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.is(ModBlockTags.DESTROYED_IN_SPACE) && !hasOxygenOnAnySide(level, pos)) {
            level.destroyBlock(pos, true);
        } else if (state.getBlock() instanceof GrassBlock && !hasOxygenOnAnySide(level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
        } else if (state.getBlock() instanceof FarmBlock && !hasOxygenOnAnySide(level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
        } else if (state.getBlock() instanceof CampfireBlock) {
            level.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false).setValue(CampfireBlock.FACING, state.getValue(CampfireBlock.FACING)));
        } else if (state.getBlock() instanceof CandleBlock) {
            level.setBlockAndUpdate(pos, state.setValue(CandleBlock.CANDLES, state.getValue(CandleBlock.CANDLES)).setValue(CandleBlock.LIT, false));
        } else if (state.getBlock() instanceof CandleCakeBlock) {
            level.setBlockAndUpdate(pos, state.setValue(CandleCakeBlock.LIT, false));
        }
    }

    private static void tickCold(ServerLevel level, BlockPos pos, BlockState state) {
        FluidState fluidState = state.getFluidState();
        if (fluidState.isSource() && fluidState.is(ModFluidTags.FREEZES_IN_SPACE)) {
            level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
        }
    }

    private static void tickHot(ServerLevel level, BlockPos pos, BlockState state) {
        FluidState fluidState = state.getFluidState();
        if (fluidState.isSource() && fluidState.is(ModFluidTags.EVAPORATES_IN_SPACE)) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 2.6f + (level.random.nextFloat() - level.random.nextFloat()) * 0.8f);
            ModUtils.sendParticles(level, ParticleTypes.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 8, 0.5, 0.5, 0.5, 0);
        }
    }

    private static boolean hasOxygenOnAnySide(ServerLevel level, BlockPos pos) {
        for (var direction : Direction.values()) {
            if (OxygenApi.API.hasOxygen(level, pos.relative(direction))) {
                return true;
            }
        }
        return false;
    }
}
