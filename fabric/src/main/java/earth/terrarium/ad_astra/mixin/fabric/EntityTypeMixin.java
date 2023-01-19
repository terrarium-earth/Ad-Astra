package earth.terrarium.ad_astra.mixin.fabric;

import com.google.common.collect.ImmutableSet;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.Builder.class)
public abstract class EntityTypeMixin {
    @Shadow
    @Final
    private EntityType.EntityFactory<? extends Entity> factory;
    @Shadow
    @Final
    private MobCategory category;
    @Shadow
    private ImmutableSet<Block> immuneTo;
    @Shadow
    private boolean serialize;
    @Shadow
    private boolean summon;
    @Shadow
    private boolean fireImmune;
    @Shadow
    private boolean canSpawnFarFromPlayer;
    @Shadow
    private int clientTrackingRange;
    @Shadow
    private int updateInterval;
    @Shadow
    private EntityDimensions dimensions;
    @Shadow
    private FeatureFlagSet requiredFeatures;

    // Removes the datafixer warning
    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    public void build(String key, CallbackInfoReturnable<EntityType<? extends Entity>> cir) {
        if (AdAstra.MOD_ID.equals(key)) {
            cir.setReturnValue(new EntityType<>(factory, category, serialize, summon, fireImmune, canSpawnFarFromPlayer, immuneTo, dimensions, clientTrackingRange, updateInterval, requiredFeatures));
        }
    }
}
