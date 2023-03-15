package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void ad_astra$setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (!AdAstraConfig.doOxygen) {
            return;
        }
        Block block = (Block) (Object) this;
        if (block instanceof BushBlock || block instanceof CactusBlock) {
            if (!BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(AdAstra.MOD_ID)) {
                if (!OxygenUtils.posHasOxygen(level, pos)) {
                    level.destroyBlock(pos, true);
                }
            }
        }

        if (block instanceof GrassBlock) {
            if (!OxygenUtils.posHasOxygen(level, pos)) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
        }

        if (block instanceof LeavesBlock) {
            if (!OxygenUtils.posHasOxygen(level, pos)) {
                level.destroyBlock(pos, false);
            }
        }

        if (block instanceof VineBlock) {
            if (!OxygenUtils.posHasOxygen(level, pos)) {
                level.destroyBlock(pos, false);
            }
        }
    }
}
