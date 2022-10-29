package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.container.DoubleFluidTank;
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
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class OxygenDistributorBlockEntity extends FluidMachineBlockEntity implements EnergyBlock {
    private InsertOnlyEnergyContainer energyContainer;

    private int oxygenFillCheckTicks = AdAstra.CONFIG.oxygenDistributor.refreshTicks;
    private boolean showOxygen = false;

    public OxygenDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_DISTRIBUTOR.get(), blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        showOxygen = nbt.getBoolean("showOxygen");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("showOxygen", showOxygen);
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
    public int getInventorySize() {
        return 2;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new OxygenDistributorScreenHandler(syncId, inv, this);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
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
        long oxygenBlocks = OxygenUtils.getOxygenBlocksCount(this.world, this.getPos());
        long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks, false);
        long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks, false);

        if (world.getTime() % 20 == 0) {
            this.getFluidContainer().extractFluid(FluidHooks.newFluidHolder(this.getOutputTank().getFluid(), amountOfFluidToExtract, null), false);
        }

        if (this.getEnergyStorage().internalExtract(amountOfEnergyToConsume, false) > 0) {
            ModUtils.spawnForcedParticles((ServerWorld) this.getWorld(), ModParticleTypes.OXYGEN_BUBBLE.get(), this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.03);
        }
    }

    public boolean canDistribute(int oxygenBlocks) {
        long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks, false);
        long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks, false);
        if (getOutputTank().isEmpty()) {
            return false;
        } else if (this.getCachedState().get(AbstractMachineBlock.POWERED)) {
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
        if (!this.world.isClient) {
            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount() && FluidHooks.isFluidContainingItem(insertSlot)) {
                FluidUtils.insertItemFluidToTank(this.getFluidContainer(), this, 0, 1, 0, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.world).stream().anyMatch(r -> r.matches(f)));
            }

            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.world);
                if (FluidUtils.convertFluid((DoubleFluidTank) this.getFluidContainer(), recipes, 20)) {
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

        if (!world.isClient) {
            boolean active = OxygenUtils.getOxygenBlocksCount(this.world, this.getPos()) > 0;
            this.setActive(active);

            if (active) {
                this.extractResources();
            }
        }

    }

    public void runAlgorithm() {
        if (this.world.isClient()) {
            if (!this.getCachedState().get(AbstractMachineBlock.LIT)) {
                return;
            }
        } else {
            if (getOutputTank().getFluidAmount() <= 0 && this.getEnergyStorage().getStoredEnergy() <= 0) {
                return;
            }
        }

        OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, this.getMaxBlockChecks());
        Set<BlockPos> positions = floodFiller.runAlgorithm(pos.up());

        if (this.canDistribute(positions.size())) {
            OxygenUtils.setEntry(this.world, pos, positions);
        } else if (!world.isClient()) {
            OxygenUtils.removeEntry(this.world, this.getPos());
        }

        if (this.shouldShowOxygen()) {
            this.spawnParticles(positions);
        }
    }

    // Spawn the bubble particles in each oxygenated position. The "show" button must be clicked in the oxygen distributor GUI in order to work.
    public void spawnParticles(Set<BlockPos> positions) {
        if (!world.isClient() && this.getCachedState().get(AbstractMachineBlock.LIT)) {
            for (BlockPos pos : positions) {
                ModUtils.spawnForcedParticles((ServerWorld) this.getWorld(), ModParticleTypes.OXYGEN_BUBBLE.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);
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
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}