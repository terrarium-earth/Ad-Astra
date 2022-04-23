package net.mrscauthd.beyond_earth.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HammerItem extends Item {

    public HammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}