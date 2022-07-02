package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.client.utils.ClientOxygenUtils;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.OxygenDistributorScreenHandler;
import com.github.alexnijjar.beyond_earth.recipes.OxygenConversionRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.OxygenFillerAlgorithm;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class OxygenDistributorBlockEntity extends FluidMachineBlockEntity {

    public static final long MAX_ENERGY = BeyondEarth.CONFIG.mainConfig.oxygenDistributorMaxEnergy;
    public static final long ENERGY_PER_TICK = BeyondEarth.CONFIG.mainConfig.oxygenDistributorEnergyPerTick;
    public static final int TANK_SIZE = BeyondEarth.CONFIG.mainConfig.oxygenDistributorTankBuckets;
    public static final int MAX_BLOCK_CHECKS = BeyondEarth.CONFIG.mainConfig.oxygenDistributorMaxBlockChecks;
    public static final int UPDATE_OXYGEN_FILLER_TICKS = BeyondEarth.CONFIG.mainConfig.oxygenDistributorRefreshTicks;

    public static final double OXYGEN_USAGE_MULTIPLIER = BeyondEarth.CONFIG.mainConfig.oxygenDistributorOxygenMultiplier;
    public static final double ENERGY_USAGE_MULTIPLIER = BeyondEarth.CONFIG.mainConfig.oxygenDistributorEnergyMultiplier;

    private int oxygenFillCheckTicks = UPDATE_OXYGEN_FILLER_TICKS;

    @Environment(EnvType.CLIENT)
    private int clientOxygenFillCheckTicks;

    public OxygenDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_DISTRIBUTOR_ENTITY, blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.oxygenFillCheckTicks = nbt.getInt("OxygenFillCheckTicks");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("OxygenFillCheckTicks", oxygenFillCheckTicks);
    }

    @Override
    public long getInputSize() {
        return TANK_SIZE;
    }

    @Override
    public long getOutputSize() {
        return TANK_SIZE * 2;
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return MAX_ENERGY;
    }

    @Override
    public long getEnergyPerTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    public long getMaxEnergyInsert() {
        return ENERGY_PER_TICK * 32;
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
    public void tick() {

        BlockPos pos = this.getPos();
        if (this.world instanceof ServerWorld world) {
            ItemStack insertSlot = this.getItems().get(0);
            ItemStack extractSlot = this.getItems().get(1);

            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount()) {
                ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
                FluidUtils.insertFluidIntoTank(this, 0, 1, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world).stream().anyMatch(r -> r.getFluidInput().equals(f.getFluid())));
            }

            if (this.hasEnergy()) {
                List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
                if (FluidUtils.convertFluid(this, recipes)) {
                    this.drainEnergy();
                    this.setActive(true);
                } else {
                    this.setActive(false);
                }

                if (oxygenFillCheckTicks >= UPDATE_OXYGEN_FILLER_TICKS) {
                    if (this.outputTank.amount > 0) {
                        OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, MAX_BLOCK_CHECKS);
                        Set<BlockPos> positions = floodFiller.runAlgorithm(pos.up());

                        long amountOfFluidToExtract = (long) (positions.size() * OXYGEN_USAGE_MULTIPLIER);
                        if (this.outputTank.amount > amountOfFluidToExtract) {
                            if (this.getEnergy() > ((long) (positions.size() * ENERGY_USAGE_MULTIPLIER))) {
                                try (Transaction transaction = Transaction.openOuter()) {
                                    if (!this.outputTank.isResourceBlank()) {
                                        if (this.outputTank.extract(this.outputTank.getResource(), amountOfFluidToExtract, transaction) == amountOfFluidToExtract) {
                                            this.drainEnergy((long) (positions.size() * ENERGY_USAGE_MULTIPLIER));
                                            OxygenUtils.setEntry(world, pos, positions);
                                            transaction.commit();
                                            this.setActive(true);
                                        }
                                    } else {
                                        OxygenUtils.removeEntry(world, pos);
                                    }
                                }
                            } else {
                                OxygenUtils.removeEntry(world, pos);
                                if (this.inputTank.amount <= 0 && this.outputTank.amount < 100) {
                                    this.outputTank.amount = 0;
                                }
                            }
                        } else {
                            OxygenUtils.removeEntry(world, pos);
                            if (this.inputTank.amount <= 0 && this.outputTank.amount < 100) {
                                this.outputTank.amount = 0;
                            }
                        }
                    }
                    oxygenFillCheckTicks = 0;
                }
                oxygenFillCheckTicks++;
            }
        }

        if (this.world.isClient) {
            if (this.outputTank.amount > 0) {
                if (clientOxygenFillCheckTicks >= UPDATE_OXYGEN_FILLER_TICKS) {
                    OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(world, MAX_BLOCK_CHECKS);
                    Set<BlockPos> positions = floodFiller.runAlgorithm(pos.up());

                    long amountOfFluidToExtract = (long) (positions.size() * 0.8);
                    if (this.outputTank.amount > amountOfFluidToExtract) {
                        if (this.getEnergy() > ((long) (positions.size() * ENERGY_USAGE_MULTIPLIER))) {
                            ClientOxygenUtils.setEntry(world, pos, positions);
                            ClientOxygenUtils.oxygenLeak = floodFiller.oxygenLeakDetected();
                        } else {
                            ClientOxygenUtils.removeEntry(world, pos);
                        }
                    } else {
                        ClientOxygenUtils.removeEntry(world, pos);
                    }
                    clientOxygenFillCheckTicks = 0;
                }
            }
            clientOxygenFillCheckTicks++;
        }
    }
}