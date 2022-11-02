package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.recipes.OxygenConversionRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModParticleTypes;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.screen.handler.OxygenDistributorScreenHandler;
import earth.terrarium.ad_astra.util.FluidUtils;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import earth.terrarium.ad_astra.util.algorithms.OxygenFillerAlgorithm;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OxygenDistributorBlockEntity extends FluidMachineBlockEntity implements EnergyBlock {
    private InsertOnlyEnergyContainer energyContainer;

    private int oxygenFillCheckTicks = AdAstra.CONFIG.oxygenDistributor.refreshTicks;
    private boolean showOxygen = false;

    public OxygenDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_DISTRIBUTOR.get(), blockPos, blockState);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        showOxygen = nbt.getBoolean("ShowOxygen");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("ShowOxygen", showOxygen);
    }

    public boolean shouldShowOxygen() {
        return this.showOxygen;
    }

    public void setShowOxygen(boolean value) {
        this.showOxygen = value;
    }

    @Override
    public long getInputTankCapacity() {
        return AdAstra.CONFIG.oxygenDistributor.tankSize;
    }

    @Override
    public long getOutputTankCapacity() {
        return AdAstra.CONFIG.oxygenDistributor.tankSize * 2;
    }

    @Override
    public Predicate<FluidHolder> getInputFilter() {
        return f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.getLevel()).stream().anyMatch(r -> r.matches(f.getFluid()));
    }

    @Override
    public int getInventorySize() {
        return 2;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new OxygenDistributorScreenHandler(syncId, inv, this);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
    }

    public int getMaxBlockChecks() {
        return AdAstra.CONFIG.oxygenDistributor.maxBlockChecks;
    }

    public long getFluidToExtract(long oxygenBlocks, boolean client) {
        long value = FluidHooks.toMillibuckets((long) ((oxygenBlocks * AdAstra.CONFIG.oxygenDistributor.oxygenMultiplier) / 45));
        if (client) {
            return value;
        }
        return value == 0 ? 1 : value;
    }

    public long getEnergyToConsume(long oxygenBlocks, boolean client) {
        long value = (long) ((oxygenBlocks * AdAstra.CONFIG.oxygenDistributor.energyMultiplier) / 75);
        if (client) {
            return value;
        }
        return value == 0 ? 1 : value;
    }

    public void extractResources() {
        long oxygenBlocks = OxygenUtils.getOxygenBlocksCount(this.level, this.getBlockPos());
        long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks, false);
        long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks, false);

        if (level.getGameTime() % 20 == 0) {
            this.getFluidContainer().extractFluid(FluidHooks.newFluidHolder(this.getOutputTank().getFluid(), amountOfFluidToExtract, null), false);
        }

        if (this.getEnergyStorage().internalExtract(amountOfEnergyToConsume, false) > 0) {
            ModUtils.spawnForcedParticles((ServerLevel) this.getLevel(), ModParticleTypes.OXYGEN_BUBBLE.get(), this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.03);
        }
    }

    public boolean canDistribute(int oxygenBlocks) {
        long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks, false);
        long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks, false);
        if (getOutputTank().isEmpty()) {
            return false;
        } else if (this.getBlockState().getValue(AbstractMachineBlock.POWERED)) {
            return false;
        } else if (this.getEnergyStorage().internalExtract(amountOfEnergyToConsume, true) == 0) {
            return false;
        } else if (getOutputTank().getFluid().equals(Fluids.EMPTY)) {
            return false;
        } else
            return getOutputTank().getFluidAmount() >= amountOfFluidToExtract;
    }

    @Override
    public void tick() {

        ItemStack insertSlot = this.getItems().get(0);
        ItemStack extractSlot = this.getItems().get(1);

        // Convert the input fluid into oxygen
        if (!this.level.isClientSide) {
            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxStackSize() && FluidHooks.isFluidContainingItem(insertSlot)) {
                FluidUtils.insertItemFluidToTank(this.getFluidContainer(), this, 0, 1, 0, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.level).stream().anyMatch(r -> r.matches(f)));
                FluidUtils.extractTankFluidToItem(this.getFluidContainer().getInput(), this, 0, 1, 0, f -> true);
            }

            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.level);
                if (FluidUtils.convertFluid(this.getFluidContainer(), recipes, 20)) {
                    this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
                }
            }
        }

        // Distribute the oxygen every certain amount of ticks. The algorithm is then run to determine how much oxygen to distribute.
        if (oxygenFillCheckTicks >= AdAstra.CONFIG.oxygenDistributor.refreshTicks) {
            this.runAlgorithm();
            oxygenFillCheckTicks = 0;
        } else {
            oxygenFillCheckTicks++;
        }

        if (!level.isClientSide) {
            boolean active = OxygenUtils.getOxygenBlocksCount(this.level, this.getBlockPos()) > 0;
            this.setActive(active);

            if (active) {
                this.extractResources();
            }
        }

    }

    public void runAlgorithm() {
        if (this.level.isClientSide()) {
            if (!this.getBlockState().getValue(AbstractMachineBlock.LIT)) {
                return;
            }
        } else {
            if (getOutputTank().getFluidAmount() <= 0 && this.getEnergyStorage().getStoredEnergy() <= 0) {
                return;
            }
        }

        OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.level, this.getMaxBlockChecks());
        Set<BlockPos> positions = floodFiller.runAlgorithm(worldPosition.above());

        if (this.canDistribute(positions.size())) {
            OxygenUtils.setEntry(this.level, worldPosition, positions);
        } else if (!level.isClientSide()) {
            OxygenUtils.removeEntry(this.level, this.getBlockPos());
        }

        if (this.shouldShowOxygen()) {
            this.spawnParticles(positions);
        }
    }

    // Spawn the bubble particles in each oxygenated position. The "show" button must be clicked in the oxygen distributor GUI in order to work.
    public void spawnParticles(Set<BlockPos> positions) {
        if (!level.isClientSide() && this.getBlockState().getValue(AbstractMachineBlock.LIT)) {
            for (BlockPos pos : positions) {
                ModUtils.spawnForcedParticles((ServerLevel) this.getLevel(), ModParticleTypes.OXYGEN_BUBBLE.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }

    public long getEnergyPerTick() {
        return AdAstra.CONFIG.oxygenDistributor.fluidConversionEnergyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public InsertOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new InsertOnlyEnergyContainer(this, (int) AdAstra.CONFIG.oxygenDistributor.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}