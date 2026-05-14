package earth.terrarium.adastra.mixins.client;

import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {

    @Invoker
    boolean invokeDoesMobEffectBlockSky(Camera camera);

    @Accessor
    int getTicks();

    @Accessor
    VertexBuffer getSkyBuffer();
}
