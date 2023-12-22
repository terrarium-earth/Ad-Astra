package earth.terrarium.adastra.mixins.common.multipart;

import earth.terrarium.adastra.common.entities.multipart.MultipartPartsHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements MultipartPartsHolder {

    @Inject(
        method = "getEntityOrPart",
        at = @At("HEAD"),
        cancellable = true
    )
    private void adastra$getEntityOrPart(int id, CallbackInfoReturnable<Entity> cir) {
        Entity entity = adastra$getParts().get(id);
        if (entity != null) {
            cir.setReturnValue(entity);
        }
    }

}
