package earth.terrarium.ad_astra.mixin.oxygen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
@SuppressWarnings("deprecation")
public abstract class BucketItemMixin {

    @Final
    @Shadow
    private Fluid content;


    // Evaporate water in a no-oxygen environment. Water is not evaporated in a oxygen distributor.
    @Inject(method = "emptyContents", at = @At(value = "HEAD"), cancellable = true)
    public void adastra_emptyContents(Player player, Level level, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (!AdAstra.CONFIG.general.doOxygen) {
            return;
        }

        if (!ModUtils.isSpacelevel(level)) {
            return;
        }

        BucketItem bucketItem = (BucketItem) (Object) this;

        if (!OxygenUtils.posHasOxygen(level, pos) && !this.content.equals(ModFluids.CRYO_FUEL.get())) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            if (ModUtils.getWorldTemperature(level) < 0) {
                BlockState state = level.getBlockState(pos);
                if (state.isAir()) {
                    level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                }

            }
            level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 2.6f + (level.random.nextFloat() - level.random.nextFloat()) * 0.8f);
            for (int l = 0; l < 8; ++l) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0, 0.0, 0.0);
            }
            cir.setReturnValue(true);
        } else if (level.dimensionType().ultraWarm()) {
            cir.cancel();

            boolean bl2;
            if (!(this.content instanceof FlowingFluid)) {
                cir.setReturnValue(false);
            }
            BlockState blockState = level.getBlockState(pos);
            Block block = blockState.getBlock();
            Material material = blockState.getMaterial();
            boolean bl = blockState.canBeReplaced(this.content);
            bl2 = blockState.isAir() || bl || block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(level, pos, blockState, this.content);
            if (!bl2) {
                cir.setReturnValue(hitResult != null && bucketItem.emptyContents(player, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null));
            }
            if (block instanceof LiquidBlockContainer && this.content.equals(Fluids.WATER)) {
                ((LiquidBlockContainer) block).placeLiquid(level, pos, blockState, ((FlowingFluid) this.content).getSource(false));
                SoundEvent soundEvent = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
                level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
                cir.setReturnValue(true);
            }
            if (!level.isClientSide && bl && !material.isLiquid()) {
                level.destroyBlock(pos, true);
            }
            if (!blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                if (level.setBlock(pos, this.content.defaultFluidState().createLegacyBlock(), 11) || blockState.getFluidState().isSource()) {
                    SoundEvent soundEvent = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
                    level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
                    cir.setReturnValue(true);
                }
            }

            cir.setReturnValue(true);
        }
    }
}
