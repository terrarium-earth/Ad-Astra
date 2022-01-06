package net.mrscauthd.beyond_earth.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.mrscauthd.beyond_earth.events.Methods;

public class CoalLanternBlock extends LanternBlock {
    public CoalLanternBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public InteractionResult use(BlockState p_51274_, Level p_51275_, BlockPos p_51276_, Player p_51277_, InteractionHand p_51278_, BlockHitResult p_51279_) {
        ItemStack itemstack = p_51277_.getItemInHand(p_51278_);

        if (!p_51275_.getBlockState(p_51276_).getValue(CoalLanternBlock.HANGING) && !Methods.isSpaceWorldWithoutOxygen(p_51275_) && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
            if (!p_51275_.isClientSide) {
                p_51275_.setBlock(p_51276_, Blocks.LANTERN.defaultBlockState(), 3);
                fireManager(itemstack, p_51277_, p_51278_, p_51276_, p_51275_);
                return InteractionResult.SUCCESS;
            }
        }

        if (p_51275_.getBlockState(p_51276_).getValue(CoalLanternBlock.HANGING) && !Methods.isSpaceWorldWithoutOxygen(p_51275_) && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
            if (!p_51275_.isClientSide) {
                p_51275_.setBlock(p_51276_, Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true), 3);
                fireManager(itemstack, p_51277_, p_51278_, p_51276_, p_51275_);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static void fireManager(ItemStack itemstack, Player playerentity, InteractionHand hand, BlockPos pos, Level world) {
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1);

            itemstack.hurtAndBreak(1, playerentity, (player) -> {
                player.broadcastBreakEvent(hand);
            });
        }

        if (itemstack.getItem() == Items.FIRE_CHARGE) {
            world.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1,1);

            itemstack.setCount(itemstack.getCount() - 1);
        }
    }
}
