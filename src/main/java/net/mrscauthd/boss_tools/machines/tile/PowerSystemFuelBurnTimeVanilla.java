package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;

public abstract class PowerSystemFuelBurnTimeVanilla extends PowerSystemFuelBurnTime {

	public PowerSystemFuelBurnTimeVanilla(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity, slot);
	}

	public abstract IRecipeType<?> getRecipeType();

	@Override
	protected int getFuelInternal(ItemStack fuel) {
		return ForgeHooks.getBurnTime(fuel, this.getRecipeType());
	}

	@Override
	public ResourceLocation getName() {
		ResourceLocation name = super.getName();
		return new ResourceLocation(name.getNamespace(), name.getPath() + "/vanilla");
	}

}
