package net.mrscauthd.beyond_earth.mixin.coal_torches;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(FireBlock.class)
public class FireBlockMixin {

    @Inject(at = @At(value = "HEAD"), method = "onBlockAdded")
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify,
            CallbackInfo info) {
        // Extinguish the fire in dimensions with no oxygen.
        if (!ModUtils.dimensionHasOxygen(world)) {
            world.removeBlock(pos, false);
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
        }
    }
}