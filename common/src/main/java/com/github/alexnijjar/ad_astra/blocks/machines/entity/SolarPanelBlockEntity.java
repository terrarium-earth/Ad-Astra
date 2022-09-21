package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.screen.handler.SolarPanelScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity {

	public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.SOLAR_PANEL.get(), blockPos, blockState);
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new SolarPanelScreenHandler(syncId, inv, this);
	}

	@Override
	public boolean usesEnergy() {
		return true;
	}

	@Override
	public long getMaxGeneration() {
		return AdAstra.CONFIG.solarPanel.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return getEnergyForDimension(this.getWorld());
	}

	@Override
	public long getMaxEnergyExtract() {
		return (long) (AdAstra.CONFIG.solarPanel.energyMultiplier * 15.0);
	}

	@Override
	public void tick() {
		if (!this.world.isClient) {
			// Check solar panel conditions.
			if (world.isDay() && (!this.world.getRegistryKey().equals(World.OVERWORLD) || !this.world.isRaining() && !this.world.isThundering()) && world.isSkyVisible(this.getPos().up())) {
				this.cumulateEnergy();
				this.setActive(true);
			} else {
				this.setActive(false);
			}

			this.energyOut();
		}
	}

	public static long getEnergyForDimension(World world) {
		if (world != null) {
			return (long) (ModUtils.getSolarEnergy(world) * AdAstra.CONFIG.solarPanel.energyMultiplier);
		} else {
			return 0;
		}
	}
}