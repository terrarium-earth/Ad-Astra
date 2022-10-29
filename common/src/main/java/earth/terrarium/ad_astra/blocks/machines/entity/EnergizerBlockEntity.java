package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.blocks.machines.EnergizerBlock;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.SimpleUpdatingEnergyContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

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
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient()) {
            if (!this.getCachedState().get(AbstractMachineBlock.POWERED)) {
                ItemStack stack = this.getStack(0);
                this.setActive(true);
                if (!stack.isEmpty()) {
                    if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                        // TODO: This doesn't transfer anything. "moved" is always 0 on fabric.
                        long moved = EnergyHooks.moveBlockToItemEnergy(this, null, stack, this.getEnergyPerTick());
                        if (moved > 0) {
                            BlockPos pos = this.getPos();
                            ModUtils.spawnForcedParticles((ServerWorld) world, ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5, 2, 0.1, 0.1, 0.1, 0.1);
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
            this.world.setBlockState(this.getPos(), this.getCachedState().with(EnergizerBlock.POWER, level));
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
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}