package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin {

    @Inject(method = "playerDestroy", at = @At("TAIL"))
    public void adastra_playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        if (!OxygenUtils.posHasOxygen(level, pos) && ModUtils.getWorldTemperature(level) < 0) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    @Inject(method = "melt", at = @At("HEAD"), cancellable = true)
    public void adastra_melt(BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
        if (!OxygenUtils.posHasOxygen(level, pos) && ModUtils.getWorldTemperature(level) < 0) {
            ci.cancel();
        }
    }
}
