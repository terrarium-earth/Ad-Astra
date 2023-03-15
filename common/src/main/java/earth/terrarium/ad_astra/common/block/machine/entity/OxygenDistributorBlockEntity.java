package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import earth.terrarium.ad_astra.common.config.OxygenDistributorConfig;
import earth.terrarium.ad_astra.common.recipe.OxygenConversionRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModParticleTypes;
import earth.terrarium.ad_astra.common.screen.menu.OxygenDistributorMenu;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import earth.terrarium.ad_astra.common.util.algorithm.FloodFiller3D;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OxygenDistributorBlockEntity extends FluidMachineBlockEntity implements EnergyAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;

    private int oxygenFillCheckTicks = OxygenDistributorConfig.refreshTicks;
    private boolean showOxygen = false;

    public OxygenDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.OXYGEN_DISTRIBUTOR.get(), blockPos, blockState);
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
        return OxygenDistributorConfig.tankSize;
    }

    @Override
    public long getOutputTankCapacity() {
        return OxygenDistributorConfig.tankSize;
    }

    @Override
    public Predicate<FluidHolder> getInputFilter() {
        return f -> OxygenConversionRecipe.getRecipes(this.getLevel()).stream().anyMatch(r -> r.matches(f.getFluid()));
    }

    @Override
    public int getInventorySize() {
        return 2;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return new OxygenDistributorMenu(syncId, inv, this);
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
        return OxygenDistributorConfig.maxBlockChecks;
    }

    public long getFluidToExtract(long oxygenBlocks, boolean client) {
        long value = (long) (((FluidHooks.buckets(1) / 1000) * oxygenBlocks / 60) * OxygenDistributorConfig.oxygenMultiplier);
        if (client) {
            return value;
        }
        return value == 0 ? 1 : value;
    }

    public long getEnergyToConsume(long oxygenBlocks, boolean client) {
        long value = (long) ((oxygenBlocks * OxygenDistributorConfig.energyMultiplier) / 75);
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
            this.getFluidContainer(this).extractFluid(FluidHooks.newFluidHolder(this.getOutputTank().getFluid(), amountOfFluidToExtract, null), false);
        }

        if (this.getEnergyStorage(this).internalExtract(amountOfEnergyToConsume, false) > 0) {
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
        } else if (this.getEnergyStorage(this).internalExtract(amountOfEnergyToConsume, true) == 0) {
            return false;
        } else if (getOutputTank().getFluid().equals(Fluids.EMPTY)) {
            return false;
        }
        return getOutputTank().getFluidAmount() >= amountOfFluidToExtract;
    }

    @Override
    public void tick() {

        ItemStack insertSlot = this.getItems().get(0);
        ItemStack extractSlot = this.getItems().get(1);

        // Convert the input fluid into oxygen
        if (!this.level.isClientSide) {
            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxStackSize() && FluidHooks.isFluidContainingItem(insertSlot)) {
                FluidUtils.insertItemFluidToTank(this.getFluidContainer(this), this, 0, 1, 0, f -> OxygenConversionRecipe.getRecipes(this.level).stream().anyMatch(r -> r.matches(f)));
                FluidUtils.extractTankFluidToItem(getDoubleFluidTank().getInput(), this, 0, 1, 0, f -> true);
            }

            if (this.getEnergyStorage(this).internalExtract(this.getEnergyPerTick(), true) > 0) {
                List<OxygenConversionRecipe> recipes = OxygenConversionRecipe.getRecipes(this.level);
                if (FluidUtils.convertFluid(getDoubleFluidTank(), recipes, FluidHooks.buckets(1f) / 10)) {
                    this.getEnergyStorage(this).internalExtract(this.getEnergyPerTick(), false);
                }
            }
        }

        // Distribute the oxygen every certain amount of ticks. The algorithm is then run to determine how much oxygen to distribute.
        if (oxygenFillCheckTicks >= OxygenDistributorConfig.refreshTicks) {
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
            if (getOutputTank().getFluidAmount() <= 0 && this.getEnergyStorage(this).getStoredEnergy() <= 0) {
                return;
            }
        }

        Set<BlockPos> positions = FloodFiller3D.run(level, worldPosition.above());

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
        return OxygenDistributorConfig.fluidConversionEnergyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage(this).getMaxCapacity();
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(OxygenDistributorConfig.maxEnergy)) : this.energyContainer;
    }
}