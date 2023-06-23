package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

    @Shadow
    @Final
    private Fluid content;

    public BucketItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void ad_astra$use(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (ModUtils.getWorldTemperature(level) < 0) {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
            BlockPos pos = blockhitresult.getBlockPos();
            if (!OxygenUtils.posHasOxygen(level, pos) && !OxygenUtils.posHasOxygen(level, pos.above())) {
                BlockState blockState = level.getBlockState(pos);
                if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(usedHand)));
                }
            }
        }
    }

    // Allow buckets to be used in hot dimensions when there is oxygen.
    @Inject(method = "emptyContents", at = @At(value = "HEAD", target = "Lnet/minecraft/world/item/BucketItem;emptyContents(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/BlockHitResult;)Z"), cancellable = true)
    private void ad_astra$emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult blockHitResult, CallbackInfoReturnable<Boolean> cir) {
        if (OxygenUtils.posHasOxygen(level, pos) && ModUtils.isPlanet(level)) {
            BlockState blockState = level.getBlockState(pos);

            if (!level.isClientSide && blockState.canBeReplaced(this.content) && !blockState.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (!level.setBlock(pos, this.content.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                cir.setReturnValue(false);
            } else {
                SoundEvent soundEvent = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
                level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
                cir.setReturnValue(true);
            }
        }
    }
}
