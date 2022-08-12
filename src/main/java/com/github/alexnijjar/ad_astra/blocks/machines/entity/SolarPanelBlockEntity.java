package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.screen.handler.SolarPanelScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity {

	public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.SOLAR_PANEL, blockPos, blockState);
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
		return AdAstra.CONFIG.solarPanel.energyPerTick;
	}

	@Override
	public long getMaxEnergyExtract() {
		return AdAstra.CONFIG.solarPanel.energyPerTick * 2;
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
}