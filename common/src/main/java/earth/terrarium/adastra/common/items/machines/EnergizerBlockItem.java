package earth.terrarium.adastra.common.items.machines;

import earth.terrarium.adastra.common.blockentities.machines.EnergizerBlockEntity;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergizerBlockItem extends BlockItem implements BotariumEnergyItem<WrappedItemEnergyContainer> {

    public EnergizerBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, Player player, ItemStack stack, BlockState state) {
        if (level.isClientSide() || !(level.getBlockEntity(pos) instanceof EnergizerBlockEntity entity)) {
            return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        }

        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyContainer itemEnergyContainer = EnergyApi.getItemEnergyContainer(holder);
        if (itemEnergyContainer == null) return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        entity.getEnergyStorage().setEnergy(itemEnergyContainer.getStoredEnergy());
        entity.onEnergyChange();

        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }

    @Override
    public WrappedItemEnergyContainer getEnergyStorage(ItemStack holder) {
        return new WrappedItemEnergyContainer(
            holder,
            new SimpleEnergyContainer(2_000_000) {
                @Override
                public long maxInsert() {
                    return 1_000;
                }

                @Override
                public long maxExtract() {
                    return 1_000;
                }
            });
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getEnergyStorage(stack).getStoredEnergy() > 0;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        var energyStorage = getEnergyStorage(stack);
        return (int) (((double) energyStorage.getStoredEnergy() / energyStorage.getMaxCapacity()) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0x63dcc2;
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        var energy = getEnergyStorage(stack);
        tooltipComponents.add(TooltipUtils.getEnergyComponent(energy.getStoredEnergy(), energy.getMaxCapacity()));
        tooltipComponents.add(TooltipUtils.getMaxEnergyInComponent(energy.maxInsert()));
        tooltipComponents.add(TooltipUtils.getMaxEnergyOutComponent(energy.maxExtract()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.ENERGIZER_INFO);
    }
}
