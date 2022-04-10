package net.mrscauthd.beyond_earth.mixin;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {

    @Accessor("lightSkyBuffer")
    VertexBuffer getLightSkyBuffer();

    @Accessor("darkSkyBuffer")
    VertexBuffer getDarkSkyBuffer();
}
