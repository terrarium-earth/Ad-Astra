package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.handler.SolarPanelScreenHandler;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity {

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
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getCapacity() {
        return AdAstra.CONFIG.solarPanel.maxEnergy;
    }

    @Override
    public long getEnergyPerTick() {
        return getEnergyForDimension(this.getWorld());
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            // Check solar panel conditions.
            if (world.isDay() && (!this.world.getRegistryKey().equals(World.OVERWORLD) || !this.world.isRaining() && !this.world.isThundering()) && world.isSkyVisible(this.getPos().up())) {
                this.cumulateEnergy();
                this.setActive(true);
            } else {
                this.setActive(false);
            }

            this.energyOut();
        }
    }
}