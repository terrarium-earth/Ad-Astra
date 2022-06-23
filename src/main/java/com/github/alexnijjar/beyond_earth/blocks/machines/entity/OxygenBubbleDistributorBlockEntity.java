package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.client.utils.ClientOxygenUtils;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.OxygenBubbleDistributorScreenHandler;
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

public class OxygenBubbleDistributorBlockEntity extends FluidMachineBlockEntity {

    public static final long MAX_ENERGY = 20000L;
    public static final long ENERGY_PER_TICK = 5L;
    public static final int TANK_SIZE = 3;
    public static final int MAX_BLOCK_CHECKS = 2000;
    public static final int UPDATE_OXYGEN_FILLER_TICKS = 60;

    private int oxygenFillCheckTicks;

    @Environment(EnvType.CLIENT)
    private int clientOxygenFillCheckTicks;

    public OxygenBubbleDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_BUBBLE_DISTRIBUTOR_ENTITY, blockPos, blockState);
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
        return new OxygenBubbleDistributorScreenHandler(syncId, inv, this);
    }

    @Override
    public void tick() {

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
            }

            if (oxygenFillCheckTicks >= UPDATE_OXYGEN_FILLER_TICKS) {
                OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, MAX_BLOCK_CHECKS);
                List<BlockPos> positions = floodFiller.runAlgorithm(this.getPos().up());

                long amountOfFluidToExtract = (long) (positions.size() * 0.65);
                if (amountOfFluidToExtract > 0) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        if (!this.outputTank.isResourceBlank()) {
                            OxygenUtils.oxygenLocations = positions;
                            if (this.outputTank.extract(this.outputTank.getResource(), amountOfFluidToExtract, transaction) == amountOfFluidToExtract) {
                                transaction.commit();

                                this.drainEnergy(positions.size() / 5);
                                this.setActive(true);
                            } else {
                                OxygenUtils.removeEntries(world, positions);
                            }
                        } else {
                            OxygenUtils.removeEntries(world, positions);
                        }
                    }
                } else {
                    OxygenUtils.removeEntries(world, positions);
                }

                oxygenFillCheckTicks = 0;

            }
            oxygenFillCheckTicks++;
        } else {
            if (clientOxygenFillCheckTicks >= (ClientOxygenUtils.requestOxygenCalculationsOnClient ? UPDATE_OXYGEN_FILLER_TICKS : UPDATE_OXYGEN_FILLER_TICKS * 10)) {
                OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, MAX_BLOCK_CHECKS);
                List<BlockPos> positions = floodFiller.runAlgorithm(this.getPos().up());
                ClientOxygenUtils.oxygenLocations = positions;
                ClientOxygenUtils.oxygenLeak = floodFiller.oxygenLeakDetected();
                clientOxygenFillCheckTicks = 0;
            }
            clientOxygenFillCheckTicks++;
        }
    }
}