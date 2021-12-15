package net.mrscauthd.astrocraft.machines.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.astrocraft.ModInnet;
import net.mrscauthd.astrocraft.crafting.AstroCraftRecipeTypes;
import net.mrscauthd.astrocraft.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.astrocraft.gui.screens.compressor.CompressorGui;

public class CompressorBlockEntity extends ItemStackToItemStackBlockEntity {

	public static final int ENERGY_PER_TICK = 1;

	public CompressorBlockEntity(BlockPos pos, BlockState state) {
		super(ModInnet.COMPRESSOR.get(), pos, state);
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new CompressorGui.GuiContainer(id, inventory, this);
	}

	@Override
	public ItemStackToItemStackRecipeType<?> getRecipeType() {
		return AstroCraftRecipeTypes.COMPRESSING;
	}

	@Override
	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
		super.createEnergyStorages(registry);
		registry.put(this.createEnergyStorageCommon());
	}

	@Override
	protected void createPowerSystems(PowerSystemRegistry map) {
		super.createPowerSystems(map);
		map.put(new PowerSystemEnergyCommon(this) {
			@Override
			public int getBasePowerForOperation() {
				return CompressorBlockEntity.this.getBasePowerForOperation();
			}
		});
	}

	public int getBasePowerForOperation() {
		return ENERGY_PER_TICK;
	}
}