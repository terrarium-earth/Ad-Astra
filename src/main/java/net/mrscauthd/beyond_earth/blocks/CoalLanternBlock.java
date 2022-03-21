package net.mrscauthd.beyond_earth.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.mrscauthd.beyond_earth.registry.ModBlocks;

public class CoalLanternBlock extends LanternBlock {

    public CoalLanternBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ItemStack itemstack = player.getStackInHand(hand);

//            if (Methods.isSpaceWorldWithoutOxygen(world))
            if (itemstack.getItem() instanceof FlintAndSteelItem || itemstack.getItem() instanceof FireChargeItem) {

                world.setBlockState(pos, Blocks.LANTERN.getStateWithProperties(state), 3);

                itemstack.getItem().use(world, player, hand);

                boolean hasFlint = itemstack.getItem() instanceof FlintAndSteelItem;

                if (hasFlint) {
                    world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1);
                } else {
                    world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);
                }

                if (!player.isCreative()) {
                    if (hasFlint) {
                        itemstack.damage(1, world.random, (ServerPlayerEntity) player);
                    } else {
                        itemstack.decrement(1);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
