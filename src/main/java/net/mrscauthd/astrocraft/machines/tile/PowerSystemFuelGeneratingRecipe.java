package net.mrscauthd.astrocraft.machines.tile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.astrocraft.crafting.AstroCraftRecipeType;
import net.mrscauthd.astrocraft.crafting.AstroCraftRecipeTypes;
import net.mrscauthd.astrocraft.crafting.GeneratingRecipe;

public class PowerSystemFuelGeneratingRecipe extends PowerSystemFuelBurnTime {

	public PowerSystemFuelGeneratingRecipe(AbstractMachineBlockEntity blockEntity, int slot) {
		super(blockEntity, slot);
	}

	public AstroCraftRecipeType<? extends GeneratingRecipe> getRecipeType() {
		return AstroCraftRecipeTypes.GENERATING;
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
