package earth.terrarium.adastra.mixins.common.multipart;

import earth.terrarium.adastra.common.entities.multipart.MultipartPartEntity;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkMap.class)
public class ChunkMapMixin {

    @Inject(
        method = "addEntity",
        at = @At("HEAD"),
        cancellable = true
    )
    private void adastra$addEntity(Entity entity, CallbackInfo ci) {
        if (entity instanceof MultipartPartEntity<?>) {
            ci.cancel();
        }
    }
}
