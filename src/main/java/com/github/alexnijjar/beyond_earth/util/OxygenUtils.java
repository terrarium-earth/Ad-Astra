package com.github.alexnijjar.beyond_earth.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModBlocks;
import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class OxygenUtils {

    // Contains every pos in all dimensions with oxygen.
    public static Map<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> oxygenLocations = new HashMap<>();
    public static Map<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> clientOxygenLocations = new HashMap<>();

    public static final int UPDATE_OXYGEN_FILLER_TICKS = BeyondEarth.CONFIG.oxygenDistributor.refreshTicks;
    public static int spawnOxygenBubblesTick = UPDATE_OXYGEN_FILLER_TICKS;

    static {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (spawnOxygenBubblesTick >= UPDATE_OXYGEN_FILLER_TICKS) {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    ServerWorld world = player.getWorld();

                    Map<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> locationsInDimension = oxygenLocations.entrySet().stream().filter(entry -> {
                        if (entry.getKey().getLeft().equals(world.getRegistryKey())) {
                            if (world.getBlockEntity(entry.getKey().getRight()) instanceof OxygenDistributorBlockEntity oxygenDistributor) {

                                if (oxygenDistributor.shouldShowOxygen()) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    // get all the oxygen location values into a set
                    Set<BlockPos> oxygenLocationsSet = locationsInDimension.values().stream().flatMap(Set::stream).collect(Collectors.toSet());

                    for (BlockPos pos : oxygenLocationsSet) {
                        ModUtils.spawnForcedParticles(world, ModParticleTypes.OXYGEN_BUBBLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
                spawnOxygenBubblesTick = 0;
            }
            spawnOxygenBubblesTick++;
        });
    }

    /**
     * Checks if there is oxygen in a specific block in a specific dimension.
     */
    @SuppressWarnings("deprecation")
    public static boolean posHasOxygen(World world, BlockPos pos) {

        if (!world.isChunkLoaded(pos)) {
            return true;
        }

        if (worldHasOxygen(world)) {
            return true;
        }

        for (Map.Entry<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> entry : oxygenLocations.entrySet()) {
            if (world.getRegistryKey().equals(entry.getKey().getLeft())) {
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
     * @param world  The world to check for oxygen in
     * @param source The oxygen distributor position
     * @return The amount of blocks that an oxygen distributor is distributing oxygen to
     */
    public static int getOxygenBlocksCount(World world, BlockPos source) {
        if (oxygenLocations.containsKey(Pair.of(world.getRegistryKey(), source))) {
            return oxygenLocations.get(Pair.of(world.getRegistryKey(), source)).size();
        } else {
            return 0;
        }
    }

    public static void setEntry(World world, BlockPos source, Set<BlockPos> entries) {
        // Get all the entries that have changed. If they are have been removed, deoxygenate their pos.
        if (world instanceof ServerWorld serverWorld) {
            Set<BlockPos> locations = oxygenLocations.get(Pair.of(serverWorld.getRegistryKey(), source));
            if (locations != null && !locations.isEmpty()) {
                Set<BlockPos> changedPositions = new HashSet<>(locations);
                changedPositions.removeAll(entries);
                if (changedPositions.size() > 0) {
                    deoxygenizeBlocks(serverWorld, changedPositions, source);
                }
            }
        }
        oxygenLocations.put(Pair.of(world.getRegistryKey(), source), entries);
    }

    public static void removeEntry(World world, BlockPos source) {
        if (oxygenLocations.containsKey(Pair.of(world.getRegistryKey(), source))) {
            if (world instanceof ServerWorld) {
                deoxygenizeBlocks((ServerWorld) world, oxygenLocations.get(Pair.of(world.getRegistryKey(), source)), source);
            }
        }
    }

    /**
     * Removes the oxygen from a set of blocks. For example, turns water into ice or air, converts torches into coal torches, puts
     * out flames, kills plants etc.
     */
    public static void deoxygenizeBlocks(ServerWorld world, Set<BlockPos> entries, BlockPos source) {
        if (entries == null) {
            return;
        }
        if (worldHasOxygen(world)) {
            return;
        }

        for (BlockPos pos : new HashSet<>(entries)) {

            BlockState state = world.getBlockState(pos);

            oxygenLocations.get(Pair.of(world.getRegistryKey(), source)).remove(pos);
            if (worldHasOxygen(world, pos)) {
                continue;
            }

            if (state.isAir()) {
                continue;
            }

            Block block = state.getBlock();
            if (block instanceof WallTorchBlock && !block.equals(Blocks.SOUL_WALL_TORCH)) {
                world.setBlockState(pos, ModBlocks.WALL_COAL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
                continue;
            }

            if (block instanceof TorchBlock && !block.equals(Blocks.SOUL_TORCH) && !block.equals(Blocks.SOUL_WALL_TORCH)) {
                world.setBlockState(pos, ModBlocks.COAL_TORCH.getDefaultState());
                continue;
            }

            if (block instanceof CandleCakeBlock) {
                world.setBlockState(pos, block.getDefaultState().with(CandleCakeBlock.LIT, false));
                continue;
            }

            if (block instanceof CandleBlock) {
                world.setBlockState(pos, block.getDefaultState().with(CandleBlock.CANDLES, state.get(CandleBlock.CANDLES)).with(CandleBlock.LIT, false));
                continue;
            }

            if (block instanceof FireBlock) {
                world.removeBlock(pos, false);
                continue;
            }

            if (block instanceof CampfireBlock) {
                world.setBlockState(pos, state.with(CampfireBlock.LIT, false).with(CampfireBlock.FACING, state.get(CampfireBlock.FACING)));
                continue;
            }

            if (block instanceof GrassBlock) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                continue;
            }

            if (block instanceof PlantBlock || block instanceof CactusBlock) {
                world.removeBlock(pos, true);
                continue;
            }

            if (block instanceof FarmlandBlock) {
                world.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, 0));
                continue;
            }

            if (state.getFluidState().isIn(FluidTags.WATER)) {
                if (!block.equals(ModFluids.CRYO_FUEL_BLOCK)) {
                    if (ModUtils.getWorldTemperature(world) < 0) {
                        world.setBlockState(pos, Blocks.ICE.getDefaultState());
                    } else {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
                else if (state.contains(Properties.WATERLOGGED)) {
                    world.setBlockState(pos, state.with(Properties.WATERLOGGED, false));
                }
            }
        }
    }

    /**
     * Checks if a world has oxygen, regardless of position.
     */
    public static boolean worldHasOxygen(World world) {
        if (world == null) {
            return false;
        }

        if (ModUtils.getBeyondEarthDimensions(world.isClient).stream().noneMatch(world.getRegistryKey()::equals)) {
            return true;
        }
        if (ModUtils.getOrbitWorlds(world.isClient).stream().anyMatch(world.getRegistryKey()::equals)) {
            return false;
        }
        return ModUtils.getWorldsWithoutOxygen(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    /**
     * Checks if an entity has oxygen.
     */
    public static boolean worldHasOxygen(World world, LivingEntity entity) {
        return worldHasOxygen(world, new BlockPos(entity.getEyePos()));
    }

    /**
     * Checks if a block pos has oxygen.
     */
    public static boolean worldHasOxygen(World world, BlockPos pos) {
        if (worldHasOxygen(world)) {
            return true;
        }

        return posHasOxygen(world, pos);
    }
}