package net.mrscauthd.beyond_earth.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.events.Methods;

public class CoalTorchBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public CoalTorchBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState p_51274_, Level p_51275_, BlockPos p_51276_, Player p_51277_, InteractionHand p_51278_, BlockHitResult p_51279_) {
        ItemStack itemstack = p_51277_.getItemInHand(p_51278_);

        if (p_51275_.getBlockState(p_51276_).getBlock() == ModInit.WALL_COAL_TORCH_BLOCK.get() && !Methods.isSpaceWorldWithoutOxygen(p_51275_) && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
            if (!p_51275_.isClientSide) {

                BlockState bs = p_51275_.getBlockState(p_51276_);
                DirectionProperty property = (DirectionProperty) bs.getBlock().getStateDefinition().getProperty("facing");

                p_51275_.setBlock(p_51276_, Blocks.WALL_TORCH.defaultBlockState().setValue(property, bs.getValue(property)), 3);

                flintManager(itemstack, p_51277_, p_51278_, p_51276_, p_51275_);
                return InteractionResult.SUCCESS;
            }
        }

        if (p_51275_.getBlockState(p_51276_).getBlock() == ModInit.COAL_TORCH_BLOCK.get() && !Methods.isSpaceWorldWithoutOxygen(p_51275_) && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
            if (!p_51275_.isClientSide) {
                p_51275_.setBlock(p_51276_, Blocks.TORCH.defaultBlockState(), 3);
                flintManager(itemstack, p_51277_, p_51278_, p_51276_, p_51275_);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState p_57503_, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
        return p_57504_ == Direction.DOWN && !this.canSurvive(p_57503_, p_57506_, p_57507_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_57503_, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
    }

    public boolean canSurvive(BlockState p_57499_, LevelReader p_57500_, BlockPos p_57501_) {
        return canSupportCenter(p_57500_, p_57501_.below(), Direction.UP);
    }

    public static void flintManager(ItemStack itemstack, Player playerentity, InteractionHand hand, BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1,1);

        itemstack.hurtAndBreak(1, playerentity, (player) -> {
            player.broadcastBreakEvent(hand);
        });
    }
}