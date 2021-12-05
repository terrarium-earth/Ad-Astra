package net.mrscauthd.boss_tools.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HammerItem extends Item {
	public HammerItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasCraftingRemainingItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack retval = new ItemStack(this);
		retval.setDamageValue(itemStack.getDamageValue() + 1);
		if (retval.getDamageValue() >= retval.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return retval;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}
