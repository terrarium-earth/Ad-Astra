package earth.terrarium.ad_astra.items;

import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.PlatformEnergyManager;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MachineBlockItem extends BlockItem {
    public MachineBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, Player player, ItemStack stack, BlockState state) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
                CompoundTag nbt = stack.getOrCreateTag();
                ContainerHelper.loadAllItems(nbt, machineBlock.getItems());
                if (nbt.contains("Energy")) {
                    Optional<PlatformEnergyManager> energyBlock = EnergyHooks.safeGetBlockEnergyManager(machineBlock, null);
                    energyBlock.ifPresent(platformEnergyManager -> platformEnergyManager.insert(nbt.getLong("Energy"), false));
                }

                if (machineBlock instanceof FluidMachineBlockEntity fluidMachine) {
                    if (nbt.contains("InputFluid")) {
                        fluidMachine.getInputTank().setFluid(FluidHooks.fluidFromCompound(nbt.getCompound("InputFluid")).getFluid());
                        fluidMachine.getInputTank().setAmount(nbt.getLong("InputAmount"));
                    }

                    if (nbt.contains("OutputFluid")) {
                        fluidMachine.getOutputTank().setFluid(FluidHooks.fluidFromCompound(nbt.getCompound("OutputFluid")).getFluid());
                        fluidMachine.getOutputTank().setAmount(nbt.getLong("OutputAmount"));
                    }

                    if (machineBlock instanceof OxygenDistributorBlockEntity oxygenDistributorMachine) {
                        if (nbt.contains("ShowOxygen")) {
                            oxygenDistributorMachine.setShowOxygen(nbt.getBoolean("ShowOxygen"));
                        }
                    }
                }
            }
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
