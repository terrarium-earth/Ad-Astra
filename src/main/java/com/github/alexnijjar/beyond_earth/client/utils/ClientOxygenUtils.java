package com.github.alexnijjar.beyond_earth.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ClientOxygenUtils {

    public static boolean requestOxygenCalculationsOnClient;
    public static boolean renderOxygenParticles;
    private static int spawnOxygenBubblesTick;
    public static List<BlockPos> oxygenLocations = new ArrayList<>();
    public static boolean oxygenLeak;

    static {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (spawnOxygenBubblesTick >= 60) {
                if (renderOxygenParticles) {
                    for (BlockPos oxygenPos : oxygenLocations) {
                        client.particleManager.addParticle(ModParticleTypes.OXYGEN_BUBBLE, oxygenPos.getX() + 0.5, oxygenPos.getY() + 0.5, oxygenPos.getZ() + 0.5, 0, 0, 0);
                    }
                }
                spawnOxygenBubblesTick = 0;
            }
            spawnOxygenBubblesTick++;
        });
    }

    public static boolean posHasOxygen(BlockPos pos) {
        return oxygenLocations.contains(pos);
    }
}
