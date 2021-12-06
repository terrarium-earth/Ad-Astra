package net.mrscauthd.boss_tools.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGui;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemFuelBurnTimeVanilla;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class BlastingFurnaceBlock {

	public static final int SLOT_FUEL = 2;

	// Blast Furnace Block
	public static class CustomBlock extends AbstractMachineBlock<BlastingFurnaceBlockEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops());
		}
		
		@Override
		protected boolean useFacing() {
			return true;
		}
		
		@Override
		protected boolean useLit() {
			return true;
		}

		@Override
		public BlastingFurnaceBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new BlastingFurnaceBlockEntity(pos, state);
		}
		
	}

	public static class BlastingFurnaceBlockEntity extends ItemStackToItemStackBlockEntity {
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
			return BossToolsRecipeTypes.BLASTING;
		}

		@Override
		protected BooleanProperty getBlockActivatedProperty() {
			return CustomBlock.LIT;
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
}
