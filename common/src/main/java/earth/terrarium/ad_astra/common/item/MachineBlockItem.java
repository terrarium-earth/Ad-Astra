package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenDistributorBlockEntity;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineBlockItem extends BlockItem {
    private final String[] tooltips;

    public MachineBlockItem(Block block, Properties properties, String... tooltips) {
        super(block, properties);
        this.tooltips = tooltips;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
                CompoundTag tag = stack.getOrCreateTag();
                ContainerHelper.loadAllItems(tag, machineBlock.getItems());
                if (tag.contains("Energy") && machineBlock instanceof EnergyBlock energyBlock) {
                    energyBlock.getEnergyStorage().setEnergy(Mth.clamp(tag.getLong("Energy"), 0, energyBlock.getEnergyStorage().getMaxCapacity()));
                }

                if (machineBlock instanceof FluidMachineBlockEntity fluidMachine) {
                    if (tag.contains("InputFluid")) {
                        fluidMachine.getInputTank().deserialize(tag.getCompound("InputFluid"));
                    }

                    if (tag.contains("OutputFluid")) {
                        fluidMachine.getOutputTank().deserialize(tag.getCompound("OutputFluid"));
                    }

                    if (machineBlock instanceof OxygenDistributorBlockEntity oxygenDistributorMachine) {
                        if (tag.contains("ShowOxygen")) {
                            oxygenDistributorMachine.setShowOxygen(tag.getBoolean("ShowOxygen"));
                        }
                    }
                }
            }
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        if (Screen.hasShiftDown()) {
            for (String text : tooltips) {
                tooltip.add((Component.translatable(text).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
            }
        } else {
            tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
        }
    }
}
