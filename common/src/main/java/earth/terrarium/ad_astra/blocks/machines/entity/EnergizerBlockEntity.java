package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.blocks.machines.EnergizerBlock;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.SimpleUpdatingEnergyContainer;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EnergizerBlockEntity extends AbstractMachineBlockEntity implements EnergyBlock {
    private SimpleUpdatingEnergyContainer energyContainer;

    public EnergizerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ENERGIZER.get(), blockPos, blockState);
    }

    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public void tick() {
        if (!this.getLevel().isClientSide()) {
            if (!this.getBlockState().getValue(AbstractMachineBlock.POWERED)) {
                ItemStackHolder stack = new ItemStackHolder(this.getItem(0));
                this.setActive(true);
                if (!stack.getStack().isEmpty()) {
                    if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                        long moved = EnergyHooks.safeMoveBlockToItemEnergy(this, null, stack, this.getEnergyPerTick());
                        if (moved > 0) {
                            if (stack.isDirty()) {
                                this.setItem(0, stack.getStack());
                            }
                            BlockPos pos = this.getBlockPos();
                            ModUtils.spawnForcedParticles((ServerLevel) level, ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5, 2, 0.1, 0.1, 0.1, 0.1);
                        }
                    }
                } else {
                    this.setActive(false);
                }
            }

            float ratio = (float) this.getEnergyStorage().getStoredEnergy() / (float) this.getMaxCapacity();
            // convert the ratio into an int between 0 and 4
            int level = (int) (ratio * 4);
            if (ratio > 0.0002f) {
                level++;
            }
            // Set the block state to the correct level
            this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(EnergizerBlock.POWER, level));
            EnergyHooks.distributeEnergyNearby(this, this.getEnergyPerTick());
        }
    }

    public long getEnergyPerTick() {
        return AdAstra.CONFIG.energizer.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public SimpleUpdatingEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new SimpleUpdatingEnergyContainer(this, (int) AdAstra.CONFIG.energizer.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}