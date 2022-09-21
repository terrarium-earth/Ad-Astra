package com.github.alexnijjar.ad_astra.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {

    @Accessor("STRIPPED_BLOCKS")
    static Map<Block, Block> adastra_getStrippedBlocks() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor("STRIPPED_BLOCKS")
    static void adastra_setStrippedBlocks(Map<Block, Block> value) {
        throw new UnsupportedOperationException();
    }
}
