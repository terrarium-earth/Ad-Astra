package earth.terrarium.ad_astra.mixin.oxygen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class FireBlockMixin {

    @Inject(method = "onPlace", at = @At(value = "HEAD"))
    public void adastra_onBlockAdded(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!AdAstra.CONFIG.general.doOxygen) {
            return;
        }
        // Extinguish the fire in dimensions with no oxygen.
        if (!state.getBlock().equals(Blocks.SOUL_FIRE)) {
            if (!OxygenUtils.posHasOxygen(level, pos)) {
                level.removeBlock(pos, false);
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
            }
        }
    }
}