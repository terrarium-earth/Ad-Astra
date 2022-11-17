package earth.terrarium.ad_astra.mixin.oxygen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelItemMixin {
    @Inject(method = "useOn", at = @At(value = "HEAD"), cancellable = true)
    public void adastra_useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (!AdAstra.CONFIG.general.doOxygen) {
            return;
        }
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if (!level.isClientSide) {
            BlockState blockState = level.getBlockState(context.getClickedPos());
            if (CampfireBlock.canLight(blockState) || CandleBlock.canLight(blockState) || CandleCakeBlock.canLight(blockState)) {
                if (!OxygenUtils.posHasOxygen(level, context.getClickedPos())) {
                    level.playSound(player, context.getClickedPos(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, context.getClickedPos());
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(context.getHand()));
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
