package earth.terrarium.adastra.mixins.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mixin(SkullBlockEntity.class)
public interface SkullBlockEntityInvoker {

    @Invoker
    static CompletableFuture<Optional<GameProfile>> invokeFetchGameProfile(String profileName) {
        throw new AssertionError();
    }
}
