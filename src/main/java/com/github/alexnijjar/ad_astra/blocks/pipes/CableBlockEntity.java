package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

public class CableBlockEntity extends BlockEntity implements InteractablePipe<EnergyStorage> {
    private List<Node<EnergyStorage>> consumers = new ArrayList<>();
    private Node<EnergyStorage> source;

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
    public boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos) {
        return next.getCachedState().getBlock() instanceof CableBlock && next.getCachedState().get(CableBlock.DIRECTIONS.get(direction.getOpposite()));
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void insertInto(EnergyStorage consumer, Direction direction, BlockPos pos) {
        BlockState state = this.getCachedState();
        BlockState state2 = world.getBlockState(pos);

        if (!(state.getBlock() instanceof CableBlock) || !(state2.getBlock() instanceof CableBlock)) {
            return;
        }

        if (!state.get(CableBlock.DIRECTIONS.get(this.getSource().direction()))) {
            return;
        }

        if (!state2.get(CableBlock.DIRECTIONS.get(direction))) {
            return;
        }

        if (getSource().storage() != null && getConsumers().size() > 0) {
            EnergyStorageUtil.move(getSource().storage(), consumer, Math.max(0, this.getTransferAmount() / getConsumers().size()), null);
        }

    }

    @Override
    public EnergyStorage getInteraction(World world, BlockPos pos, Direction direction) {
        return EnergyStorage.SIDED.find(world, pos, direction);
    }

    @Override
    public Node<EnergyStorage> getSource() {
        return source;
    }

    @Override
    public void setSource(Node<EnergyStorage> source) {
        this.source = source;
    }

    @Override
    public void clearSource() {
        this.source = null;
    }

    @Override
    public List<Node<EnergyStorage>> getConsumers() {
        return this.consumers;
    }

    @Override
    public int getWorkTime() {
        return 5;
    }

    @Override
    public World getPipeWorld() {
        return this.world;
    }

    @Override
    public long getTransferAmount() {
        if (this.getCachedState().getBlock() instanceof CableBlock cableBlock) {
            return cableBlock.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getPos();
    }
}