package earth.terrarium.adastra.mixins.forge.common.multipart;

import earth.terrarium.adastra.common.entities.multipart.MultipartEntity;
import earth.terrarium.adastra.common.entities.multipart.MultipartPartEntity;
import earth.terrarium.adastra.common.entities.multipart.MultipartPartsHolder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(Level.class)
public class LevelMixin implements MultipartPartsHolder {

    @Unique
    private final Int2ObjectMap<Entity> adastra$multipartEntityParts = new Int2ObjectOpenHashMap<>();

    @Override
    public Int2ObjectMap<Entity> adastra$getParts() {
        return adastra$multipartEntityParts;
    }

    @Inject(
        method = {"method_31593"},
        at = @At("TAIL")
    )
    private static void adastra$getEntities(Entity entity, Predicate<? super Entity> predicate, List<Entity> list, Entity entity2, CallbackInfo ci) {
        if (entity2 instanceof MultipartEntity multipartEntity) {
            for (MultipartPartEntity<?> part : multipartEntity.getParts()) {
                Entity asEntity = (Entity) part;
                if (entity != entity2 && predicate.test(asEntity)) {
                    list.add(asEntity);
                }
            }
        }
    }

    @Inject(
        method = {"method_47576"},
        at = @At(value = "RETURN", ordinal = 1),
        cancellable = true
    )
    private static <T extends Entity> void adastra$getEntities(
        Predicate<? super T> predicate, List<? super T> output, int maxResults, EntityTypeTest<Entity, T> entityTypeTest, Entity entity, CallbackInfoReturnable<AbortableIterationConsumer.Continuation> cir
    ) {

        if (entity instanceof MultipartEntity multipartEntity) {
            for(MultipartPartEntity<?> part : multipartEntity.getParts()) {
                T entity2 = entityTypeTest.tryCast((Entity) part);
                if (entity2 != null && predicate.test(entity2)) {
                    output.add(entity2);
                    if (output.size() >= maxResults) {
                        cir.setReturnValue(AbortableIterationConsumer.Continuation.ABORT);
                    }
                }
            }
        }
    }

}
