package net.mrscauthd.boss_tools.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGui;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class CompressorBlock {
	public static final int ENERGY_PER_TICK = 1;

	public static class CustomBlock extends AbstractMachineBlock<CustomTileEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops());
		}

		@Override
		protected boolean useLit() {
			return true;
		}
		
		@Override
		protected boolean useFacing() {
			return true;
		}

		@Override
		public CustomTileEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new CustomTileEntity(pos, state);
		}

	}

	public static class CustomTileEntity extends ItemStackToItemStackTileEntity {
		public CustomTileEntity(BlockPos pos, BlockState state) {
			super(ModInnet.COMPRESSOR.get(), pos, state);
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new CompressorGui.GuiContainer(id, inventory, this);
		}

		@Override
		public ItemStackToItemStackRecipeType<?> getRecipeType() {
			return BossToolsRecipeTypes.COMPRESSING;
		}

		@Override
		protected BooleanProperty getBlockActivatedProperty() {
			return CustomBlock.LIT;
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
					return CustomTileEntity.this.getBasePowerForOperation();
				}
			});
		}

		public int getBasePowerForOperation() {
			return ENERGY_PER_TICK;
		}
	}
}
