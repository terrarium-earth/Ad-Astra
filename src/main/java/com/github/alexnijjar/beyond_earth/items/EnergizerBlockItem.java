package com.github.alexnijjar.beyond_earth.items;

import java.util.List;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.EnergizerBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergizerBlockItem extends BlockItem {
    public EnergizerBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long energy = 0;
        if (stack.hasNbt() && stack.getNbt().contains("energy")) {
            energy = stack.getNbt().getLong("energy");
        }
        tooltip.add(new TranslatableText("gauge_text.beyond_earth.storage", energy, BeyondEarth.CONFIG.energizer.maxEnergy).setStyle(Style.EMPTY.withColor(energy > 0 ? Formatting.GREEN : Formatting.RED)));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state) {
        if (!world.isClient) {
            if (stack.hasNbt() && stack.getNbt().contains("energy")) {
                if (world.getBlockEntity(pos) instanceof EnergizerBlockEntity energizer) {
                    energizer.energyStorage.amount = stack.getNbt().getLong("energy");
                }
            }
        }
        return super.postPlacement(pos, world, player, stack, state);
    }
}
