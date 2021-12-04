package net.mrscauthd.boss_tools.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class HammerItem extends Item {
	public HammerItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) {
		ItemStack retval = new ItemStack(this);
		retval.setDamage(itemstack.getDamage() + 1);
		if (retval.getDamage() >= retval.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return retval;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}
