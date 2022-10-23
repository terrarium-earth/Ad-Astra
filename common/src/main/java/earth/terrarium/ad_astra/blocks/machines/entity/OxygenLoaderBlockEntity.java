package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.recipes.OxygenConversionRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.screen.handler.ConversionScreenHandler;
import earth.terrarium.ad_astra.util.FluidUtils;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OxygenLoaderBlockEntity extends FluidMachineBlockEntity {

	public OxygenLoaderBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.OXYGEN_LOADER.get(), blockPos, blockState);
	}

	@Override
	public long getInputTankCapacity() {
		return FluidHooks.buckets(AdAstra.CONFIG.oxygenLoader.tankBuckets);
	}

	@Override
	public long getOutputTankCapacity() {
		return FluidHooks.buckets(AdAstra.CONFIG.oxygenLoader.tankBuckets);
	}

	@Override
	public boolean usesEnergy() {
		return true;
	}

	@Override
	public long getCapacity() {
		return AdAstra.CONFIG.oxygenLoader.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return AdAstra.CONFIG.oxygenLoader.energyPerTick;
	}

	@Override
	public boolean canInsertEnergy() {
		return true;
	}

	@Override
	public int getInventorySize() {
		return 4;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir) {
		return slot == 0;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return slot == 1 || slot == 3;
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new ConversionScreenHandler(syncId, inv, this);
	}

	@Override
	public void tick() {
		if (!this.world.isClient) {
			ItemStack insertSlot = this.getItems().get(0);
			ItemStack extractSlot = this.getItems().get(1);
			ItemStack outputInsertSlot = this.getItems().get(2);
			ItemStack outputExtractSlot = this.getItems().get(3);

			if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount() && FluidHooks.isFluidContainingItem(insertSlot)) {
				FluidUtils.insertFluidToContainerFromItem(insertSlot, 0, this.getFluidContainer(), f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.world).stream().anyMatch(r -> r.matches(f)));
			}

			if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
				FluidUtils.extractFluidFromItem(outputExtractSlot, 0, this, f -> true);
			}

			if (this.hasEnergy()) {
				List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.world);
				if (FluidUtils.convertFluid(this, recipes, 10)) {
					this.drainEnergy();
					this.setActive(true);
				} else {
					this.setActive(false);
				}
			} else {
				this.setActive(false);
			}
		}
	}
}