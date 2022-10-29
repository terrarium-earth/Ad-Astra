package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.handler.SolarPanelScreenHandler;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.ExtractOnlyEnergyContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity implements EnergyBlock {
    private ExtractOnlyEnergyContainer energyContainer;

    public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_PANEL.get(), blockPos, blockState);
    }

    public static long getEnergyForDimension(World world) {
        if (world != null) {
            return (long) (ModUtils.getSolarEnergy(world) * AdAstra.CONFIG.solarPanel.energyMultiplier);
        } else {
            return 0;
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SolarPanelScreenHandler(syncId, inv, this);
    }

    @Override
    public void tick() {
        if (!this.world.isClient()) {
            // Check solar panel conditions.
            if (world.isDay() && (!this.world.getRegistryKey().equals(World.OVERWORLD) || !this.world.isRaining() && !this.world.isThundering()) && world.isSkyVisible(this.getPos().up())) {
                this.getEnergyStorage().internalInsert(this.getEnergyPerTick(), false);
                this.setActive(true);
            } else {
                this.setActive(false);
            }

            EnergyHooks.distributeEnergyNearby(this, this.getEnergyPerTick());
        }
    }

    public long getEnergyPerTick() {
        return getEnergyForDimension(this.getWorld());
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public ExtractOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new ExtractOnlyEnergyContainer(this, (int) AdAstra.CONFIG.solarPanel.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}