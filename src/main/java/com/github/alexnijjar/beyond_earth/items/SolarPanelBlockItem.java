package com.github.alexnijjar.beyond_earth.items;

import com.github.alexnijjar.beyond_earth.registry.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;

public class SolarPanelBlockItem extends MachineBlockItem {

    public SolarPanelBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    // Prevents the player from placing solar panels directly adjacent to other solar panels.
    @Override
    public ActionResult place(ItemPlacementContext context) {
        for (Direction dir : Direction.values()) {
            if (context.getWorld().getBlockState(context.getBlockPos().offset(dir)).isOf(ModBlocks.SOLAR_PANEL)) {
                return ActionResult.PASS;
            }
        }
        return super.place(context);
    }
    

}
