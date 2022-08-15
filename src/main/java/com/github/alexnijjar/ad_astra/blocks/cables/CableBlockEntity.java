package com.github.alexnijjar.ad_astra.blocks.cables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

public class CableBlockEntity extends BlockEntity {
    private final List<EnergyStorage> consumers = new ArrayList<>();
    private EnergyStorage source;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE, pos, state);
    }

    public void tick() {
        if (this.world != null && !this.world.isClient()) {
            if (world.getTime() % 5 == 0) {
                this.source = null;
                this.consumers.clear();
                Set<BlockPos> visitedNodes = new HashSet<>();
                List<BlockPos> openNodes = new ArrayList<>();
                openNodes.add(this.getPos());
                visitedNodes.add(this.getPos());
                for (Direction direction : Direction.values()) {
                    BlockPos offset = this.getPos().offset(direction);
                    EnergyStorage potentialSource = EnergyStorage.SIDED.find(world, offset, direction);
                    if (potentialSource != null && potentialSource.supportsExtraction()) {
                        source = EnergyStorage.SIDED.find(world, offset, direction);
                        break;
                    }
                }
                if (source != null) {
                    while (!openNodes.isEmpty()) {
                        BlockPos openNode = openNodes.get(openNodes.size() - 1);
                        for (Direction direction : Direction.values()) {
                            BlockPos offset = openNode.offset(direction).toImmutable();
                            BlockEntity entity = world.getBlockEntity(offset);
                            if (!visitedNodes.contains(offset) && entity instanceof CableBlockEntity) {
                                openNodes.add(offset);
                            } else {
                                EnergyStorage potentialConsumer = EnergyStorage.SIDED.find(world, offset, direction);
                                if (potentialConsumer != null && potentialConsumer.supportsInsertion()) {
                                    consumers.add(EnergyStorage.SIDED.find(world, offset, direction));
                                }
                            }
                        }
                        openNodes.remove(openNode);
                        visitedNodes.add(openNode);
                    }
                }

            } else if (source != null && !consumers.isEmpty() && this.getCachedState().getBlock() instanceof CableBlock cableBlock) {
                int notTransferred = cableBlock.getTransferRate();
                int transferAmount = cableBlock.getTransferRate() / consumers.size();

                for (EnergyStorage consumer : consumers) {
                    EnergyStorageUtil.move(this.source, consumer, Math.min(notTransferred, transferAmount), null);
                }
            }
        }
    }
}