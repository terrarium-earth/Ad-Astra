package earth.terrarium.ad_astra.common.util;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OxygenUtils {

    // Contains every pos in all dimensions with oxygen.
    public static final Map<Pair<ResourceKey<Level>, BlockPos>, Set<BlockPos>> OXYGEN_LOCATIONS = new HashMap<>();

    /**
     * Checks if a level has oxygen, regardless of position.
     */
    public static boolean levelHasOxygen(Level level) {
        if (!PlanetData.isOxygenated(level.dimension())) {
            // Ensure all non-Ad Astra dimensions have oxygen by default
            return !ModUtils.isSpacelevel(level);
        }
        return true;
    }

    /**
     * Checks if an entity has oxygen.
     */
    public static boolean entityHasOxygen(Level level, LivingEntity entity) {
        Vec3 eyePosition = entity.getEyePosition();
        return posHasOxygen(level, BlockPos.containing(eyePosition));
    }

    /**
     * Checks if there is oxygen in a specific block in a specific dimension.
     */
    @SuppressWarnings("deprecation")
    public static boolean posHasOxygen(Level level, BlockPos pos) {

        if (!level.hasChunkAt(pos)) {
            return true;
        }

        if (levelHasOxygen(level)) {
            return true;
        }

        return inDistributorBubble(level, pos);
    }

    public static boolean inDistributorBubble(Level level, BlockPos pos) {
        for (Map.Entry<Pair<ResourceKey<Level>, BlockPos>, Set<BlockPos>> entry : OXYGEN_LOCATIONS.entrySet()) {
            if (level.dimension().equals(entry.getKey().getLeft())) {
                if (entry.getValue().contains(pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the amount of blocks that an oxygen distributor is distributing.
     *
     * @param level  The level to check for oxygen in
     * @param source The oxygen distributor position
     * @return The amount of blocks that an oxygen distributor is distributing oxygen to
     */
    public static int getOxygenBlocksCount(Level level, BlockPos source) {
        return OXYGEN_LOCATIONS.getOrDefault(getOxygenSource(level, source), Set.of()).size();
    }

    public static void setEntry(Level level, BlockPos source, Set<BlockPos> entries) {
        // Get all the entries that have changed. If they are have been removed, deoxygenate their pos.
        if (!level.isClientSide) {
            if (OXYGEN_LOCATIONS.containsKey(getOxygenSource(level, source))) {
                Set<BlockPos> changedPositions = new HashSet<>(OXYGEN_LOCATIONS.get(getOxygenSource(level, source)));
                if (changedPositions != null && !changedPositions.isEmpty()) {
                    changedPositions.removeAll(entries);
                    deoxygenizeBlocks((ServerLevel) level, changedPositions, source);
                }
            }
        }
        OXYGEN_LOCATIONS.put(getOxygenSource(level, source), entries);
    }

    public static void removeEntry(Level level, BlockPos source) {
        OxygenUtils.setEntry(level, source, Set.of());
    }

    /**
     * Removes the oxygen from a set of blocks. For example, turns water into ice or air, converts torches into extinguished torches, puts out flames, kills plants etc.
     */
    public static void deoxygenizeBlocks(ServerLevel level, Set<BlockPos> entries, BlockPos source) {
        try {
            if (entries == null) {
                return;
            }
            if (entries.isEmpty()) {
                return;
            }

            if (levelHasOxygen(level)) {
                OXYGEN_LOCATIONS.remove(getOxygenSource(level, source));
                return;
            }

            for (BlockPos pos : new HashSet<>(entries)) {

                BlockState state = level.getBlockState(pos);

                OXYGEN_LOCATIONS.get(getOxygenSource(level, source)).remove(pos);
                if (posHasOxygen(level, pos)) {
                    continue;
                }

                if (state.isAir()) {
                    continue;
                }

                Block block = state.getBlock();
                if (block instanceof WallTorchBlock && !block.equals(Blocks.SOUL_WALL_TORCH)) {
                    level.setBlockAndUpdate(pos, ModBlocks.WALL_EXTINGUISHED_TORCH.get().defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(WallTorchBlock.FACING)));
                    continue;
                }

                if (block instanceof TorchBlock && !block.equals(Blocks.SOUL_TORCH) && !block.equals(Blocks.SOUL_WALL_TORCH)) {
                    level.setBlockAndUpdate(pos, ModBlocks.EXTINGUISHED_TORCH.get().defaultBlockState());
                    continue;
                }

                if (block instanceof CandleCakeBlock) {
                    level.setBlockAndUpdate(pos, block.defaultBlockState().setValue(CandleCakeBlock.LIT, false));
                    continue;
                }

                if (block instanceof CandleBlock) {
                    level.setBlockAndUpdate(pos, block.defaultBlockState().setValue(CandleBlock.CANDLES, state.getValue(CandleBlock.CANDLES)).setValue(CandleBlock.LIT, false));
                    continue;
                }

                if (block instanceof FireBlock) {
                    level.removeBlock(pos, false);
                    continue;
                }

                if (block instanceof CampfireBlock) {
                    level.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false).setValue(CampfireBlock.FACING, state.getValue(CampfireBlock.FACING)));
                    continue;
                }

                if (block instanceof GrassBlock) {
                    level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                    continue;
                }

                if (block instanceof BushBlock || block instanceof CactusBlock || block instanceof VineBlock) {
                    level.removeBlock(pos, true);
                    continue;
                }

                if (block instanceof FarmBlock) {
                    level.setBlockAndUpdate(pos, state.setValue(FarmBlock.MOISTURE, 0));
                    continue;
                }

                if (state.getFluidState().is(FluidTags.WATER)) {
                    if (!block.equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                        if (ModUtils.getWorldTemperature(level) < 0) {
                            level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                        } else {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    } else if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.WATERLOGGED, false));
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
            AdAstra.LOGGER.error("Error deoxygenizing blocks");
            e.printStackTrace();
        }
    }

    private static Pair<ResourceKey<Level>, BlockPos> getOxygenSource(Level level, BlockPos source) {
        return Pair.of(level.dimension(), source);
    }
}