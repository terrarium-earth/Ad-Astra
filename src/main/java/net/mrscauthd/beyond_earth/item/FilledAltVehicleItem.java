package net.mrscauthd.beyond_earth.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.extensions.IForgeItem;

public interface FilledAltVehicleItem extends ItemLike, IForgeItem {
    public default void fillItemCategoryAlt(CreativeModeTab tab, NonNullList<ItemStack> list) {

    }

    public default void itemCategoryAlt(CreativeModeTab tab, NonNullList<ItemStack> list) {

    }
}