package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.level.LevelSeed;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(WorldGenSettings.class)
public abstract class WorldGenSettingsMixin {

    // Gets the level seed
    @Inject(at = @At(value = "TAIL"), method = "<init>(JZZLnet/minecraft/core/Registry;Ljava/util/Optional;)V")
    private void adastra_GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, Registry<LevelStem> options, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        LevelSeed.setSeed(seed);
    }
}