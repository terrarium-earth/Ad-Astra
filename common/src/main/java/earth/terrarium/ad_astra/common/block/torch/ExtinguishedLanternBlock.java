package earth.terrarium.ad_astra.common.block.torch;

import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ExtinguishedLanternBlock extends LanternBlock {

    public ExtinguishedLanternBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (OxygenUtils.posHasOxygen(level, pos)) {
                if (itemstack.getItem() instanceof FlintAndSteelItem || itemstack.getItem() instanceof FireChargeItem) {

                    level.setBlock(pos, Blocks.LANTERN.withPropertiesOf(state), 3);

                    itemstack.getItem().use(level, player, hand);

                    boolean hasFlint = itemstack.getItem() instanceof FlintAndSteelItem;

                    if (hasFlint) {
                        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1);
                    } else {
                        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1, 1);
                    }

                    if (!player.isCreative()) {
                        if (hasFlint) {
                            itemstack.hurt(1, level.random, (ServerPlayer) player);
                        } else {
                            itemstack.shrink(1);
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}