package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.level.LevelSeed;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldGenSettings.class)
public abstract class WorldGenSettingsMixin {

    // Gets the level seed
    @Inject(at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/world/level/levelgen/WorldOptions;Lnet/minecraft/world/level/levelgen/WorldDimensions;)V")
    private void adastra_GeneratorOptions(WorldOptions worldOptions, WorldDimensions worldDimensions, CallbackInfo ci) {
        LevelSeed.setSeed(worldOptions.seed());
    }
}