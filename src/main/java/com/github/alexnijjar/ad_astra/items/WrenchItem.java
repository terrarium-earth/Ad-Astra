package com.github.alexnijjar.ad_astra.items;

import com.github.alexnijjar.ad_astra.blocks.pipes.Wrenchable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class WrenchItem extends Item {

    public WrenchItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.getBlockState(context.getBlockPos()).getBlock() instanceof Wrenchable block) {
            block.handleWrench(world, context.getBlockPos(), world.getBlockState(context.getBlockPos()), context.getSide(), context.getPlayer(), context.getHitPos());
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

}
