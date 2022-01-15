package net.mrscauthd.beyond_earth.mixin;

import net.minecraft.core.MappedRegistry;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.mrscauthd.beyond_earth.world.chunk.seedfixer.SeedFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(WorldGenSettings.class)
public abstract class SeedFixerMixin {
    @Inject(at = @At(value = "TAIL"), method = "Lnet/minecraft/world/level/levelgen/WorldGenSettings;<init>(JZZLnet/minecraft/core/MappedRegistry;Ljava/util/Optional;)V", remap = false)
    private void WorldGenSettings(long p_64614_, boolean p_64615_, boolean p_64616_, MappedRegistry<LevelStem> p_64617_, Optional<String> p_64618_, CallbackInfo info) {
        SeedFixer.setSeedInternal(p_64614_);
    }
}