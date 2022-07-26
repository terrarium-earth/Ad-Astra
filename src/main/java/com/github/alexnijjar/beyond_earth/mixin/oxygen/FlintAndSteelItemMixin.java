package com.github.alexnijjar.beyond_earth.mixin.oxygen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> ci) {
        if (!BeyondEarth.CONFIG.world.doOxygen) {
            return;
        }
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        if (!world.isClient) {
            BlockState blockState = world.getBlockState(context.getBlockPos());
            if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
                if (!OxygenUtils.worldHasOxygen(world, context.getBlockPos())) {
                    world.playSound(player, context.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                    world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, context.getBlockPos());
                    if (player != null) {
                        context.getStack().damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
                    }
                    ci.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}
