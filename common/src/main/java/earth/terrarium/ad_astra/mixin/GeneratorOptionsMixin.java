package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.world.WorldSeed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;

@Mixin(WorldGenSettings.class)
public class GeneratorOptionsMixin {

    // Gets the level seed to replace the default value of '0' for custom dimensions
    @Inject(at = @At(value = "TAIL"), method = "<init>(JZZLnet/minecraft/core/Registry;Ljava/util/Optional;)V")
    private void adastra_GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, Registry<LevelStem> options, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        WorldSeed.setSeed(seed);
    }
}