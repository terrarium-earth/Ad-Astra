package earth.terrarium.adastra.common.handlers.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;

public record SpaceStation(ChunkPos position, Component name) {
}
