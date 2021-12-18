package net.mrscauthd.beyond_earth.machines.tile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeType;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeTypes;
import net.mrscauthd.beyond_earth.crafting.GeneratingRecipe;

public class PowerSystemFuelGeneratingRecipe extends PowerSystemFuelBurnTime {

	public PowerSystemFuelGeneratingRecipe(AbstractMachineBlockEntity blockEntity, int slot) {
		super(blockEntity, slot);
	}

	public BeyondEarthRecipeType<? extends GeneratingRecipe> getRecipeType() {
		return BeyondEarthRecipeTypes.GENERATING;
	}

	@Override
	protected int getFuelInternal(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			return -1;
		}

		GeneratingRecipe recipe = this.getRecipeType().findFirst(this.getBlockEntity().getLevel(), f -> f.test(fuel));
		return recipe != null ? recipe.getBurnTime() : -1;
	}

	@Override
	public ResourceLocation getName() {
		ResourceLocation name = super.getName();
		return new ResourceLocation(name.getNamespace(), name.getPath() + "/generating");
	}
	
}
