package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

public abstract class PowerSystemFuelBurnTimeVanilla extends PowerSystemFuelBurnTime {

	public PowerSystemFuelBurnTimeVanilla(AbstractMachineBlockEntity blockEntity, int slot) {
		super(blockEntity, slot);
	}

	public abstract RecipeType<?> getRecipeType();

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
