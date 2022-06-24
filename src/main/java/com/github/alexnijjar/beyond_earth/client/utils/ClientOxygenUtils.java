package com.github.alexnijjar.beyond_earth.client.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ClientOxygenUtils {

    public static boolean requestOxygenCalculationsOnClient;
    public static boolean renderOxygenParticles;
    private static int spawnOxygenBubblesTick;

    // Contains every pos in all dimensions with oxygen.
    private static Map<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> oxygenLocations = new HashMap<>();
    public static boolean oxygenLeak;

    static {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.world != null) {
                if (spawnOxygenBubblesTick >= 30) {
                    if (renderOxygenParticles) {
                        for (Map.Entry<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> entry : oxygenLocations.entrySet()) {
                            if (client.world.getRegistryKey().equals(entry.getKey().getLeft())) {
                                for (BlockPos oxygenPos : entry.getValue()) {
                                    client.particleManager.addParticle(ModParticleTypes.OXYGEN_BUBBLE, oxygenPos.getX() + 0.5, oxygenPos.getY() + 0.5, oxygenPos.getZ() + 0.5, 0, 0, 0);
                                }
                            }
                        }
                    }
                    spawnOxygenBubblesTick = 0;
                }
                if (!client.isPaused()) {
                    spawnOxygenBubblesTick++;
                }
            }
        });
    }

    // Checks if there is oxygen in a specific block in a specific dimension.
    public static boolean posHasOxygen(World world, BlockPos pos) {
        for (Map.Entry<Pair<RegistryKey<World>, BlockPos>, Set<BlockPos>> entry : oxygenLocations.entrySet()) {
            if (world.getRegistryKey().equals(entry.getKey().getLeft())) {
                if (entry.getValue().contains(pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setEntry(World world, BlockPos source, Set<BlockPos> entries) {
        oxygenLocations.put(Pair.of(world.getRegistryKey(), source), entries);
    }

    public static int getOxygenBlocksCount(World world, BlockPos source) {
        if (oxygenLocations.containsKey(Pair.of(world.getRegistryKey(), source))) {
            return oxygenLocations.get(Pair.of(world.getRegistryKey(), source)).size();
        } else {
            return 0;
        }
    }

    public static void removeEntry(World world, BlockPos source) {
        if (!oxygenLocations.containsKey(Pair.of(world.getRegistryKey(), source))) {
            return;
        }
        oxygenLocations.remove(Pair.of(world.getRegistryKey(), source));
    }
}
