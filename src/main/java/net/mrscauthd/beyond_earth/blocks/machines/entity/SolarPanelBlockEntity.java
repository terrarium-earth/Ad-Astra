package net.mrscauthd.beyond_earth.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.gui.screen_handlers.SolarPanelScreenHandler;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity {

    public static final long MAX_ENERGY = 9000L;
    public static final long ENERGY_PER_TICK = 5L;

    public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_PANEL_ENTITY, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SolarPanelScreenHandler(syncId, inv, this);
    }

    @Override
    public boolean useEnergy() {
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
    public long getMaxEnergyExtract() {
        return ENERGY_PER_TICK * 2;
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity blockEntity) {
        if (blockEntity.useEnergy()) {
            // Check solar panel conditions.
            if (world.isDay() && !world.isRaining() && !world.isThundering()
                    && world.isSkyVisible(blockEntity.getPos().up())) {
                blockEntity.cumulateEnergy();
            }

            blockEntity.energyOut();
        }
    }
}