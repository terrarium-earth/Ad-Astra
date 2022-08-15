package com.github.alexnijjar.ad_astra.blocks.cables;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.*;

public class CableBlockEntity extends BlockEntity implements InteractablePipe<EnergyStorage> {
    private final List<EnergyStorage> consumers = new ArrayList<>();
    private EnergyStorage source;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE, pos, state);
    }

    @Override
    public boolean supportsAutoExtract() {
        return true;
    }

    @Override
    public boolean canTakeFrom(EnergyStorage source) {
        return source.supportsExtraction();
    }

    @Override
    public boolean canInsertInto(EnergyStorage consumer) {
        return consumer.supportsInsertion();
    }

    @Override
    public boolean canConnectTo(BlockEntity next) {
        return true;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public int insertInto(EnergyStorage consumer, int leftover) {
        if (getSource() != null && !getConsumers().isEmpty()) {
            return leftover - (int) EnergyStorageUtil.move(getSource(), consumer, Math.max(0, leftover / getConsumers().size()), null);
        }
        return leftover;
    }

    @Override
    public EnergyStorage getInteraction(World world, BlockPos pos, Direction direction) {
        return EnergyStorage.SIDED.find(world, pos, direction);
    }

    @Override
    public @Nullable EnergyStorage getSource() {
        return source;
    }

    @Override
    public void setSource(EnergyStorage source) {
        this.source = source;
    }

    @Override
    public void clearSource() {
        this.source = null;
    }

    @Override
    public List<EnergyStorage> getConsumers() {
        return consumers;
    }

    @Override
    public int getWorkTime() {
        return 5;
    }

    @Override
    public int getTransferAmount() {
        if(this.getCachedState().getBlock() instanceof CableBlock cableBlock) {
            return cableBlock.getTransferRate();
        }
        return 0;
    }
}