package earth.terrarium.adastra.mixins.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(SkullBlockEntity.class)
public interface SkullBlockEntityInvoker {

    @Accessor("CHECKED_MAIN_THREAD_EXECUTOR")
    static Executor getCheckedMainThreadExecutor() {
        throw new AssertionError();
    }

    @Invoker
    static CompletableFuture<Optional<GameProfile>> invokeFetchGameProfile(String profileName) {
        throw new AssertionError();
    }
}
