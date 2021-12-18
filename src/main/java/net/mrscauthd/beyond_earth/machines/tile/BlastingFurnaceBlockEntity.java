package net.mrscauthd.beyond_earth.machines.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.ModInnet;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeTypes;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.beyond_earth.gui.screens.blastfurnace.BlastFurnaceGui;

public class BlastingFurnaceBlockEntity extends ItemStackToItemStackBlockEntity {

	public static final int SLOT_FUEL = 2;

	private PowerSystemFuelBurnTimeVanilla powerSystemBurnTime;

	public BlastingFurnaceBlockEntity(BlockPos pos, BlockState state) {
		super(ModInnet.BLAST_FURNACE.get(), pos, state);
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new BlastFurnaceGui.GuiContainer(id, inventory, this);
	}

	@Override
	public ItemStackToItemStackRecipeType<?> getRecipeType() {
		return BeyondEarthRecipeTypes.BLASTING;
	}

	@Override
	protected void createPowerSystems(PowerSystemRegistry map) {
		super.createPowerSystems(map);

		map.put(this.powerSystemBurnTime = new PowerSystemFuelBurnTimeVanilla(this, this.getFuelSlot()) {
			@Override
			public RecipeType<?> getRecipeType() {
				return BlastingFurnaceBlockEntity.this.getRecipeType();
			}
		});
	}

	public PowerSystemFuelBurnTimeVanilla getPowerSystemBurnTime() {
		return this.powerSystemBurnTime;
	}

	public int getFuelSlot() {
		return SLOT_FUEL;
	}
}