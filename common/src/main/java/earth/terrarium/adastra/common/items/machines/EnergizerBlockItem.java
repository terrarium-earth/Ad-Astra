package earth.terrarium.adastra.common.items.machines;

import earth.terrarium.adastra.common.blockentities.machines.EnergizerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EnergizerBlockItem extends BlockItem {

    public EnergizerBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, Player player, ItemStack stack, BlockState state) {
        if (level.isClientSide() || !(level.getBlockEntity(pos) instanceof EnergizerBlockEntity entity)) {
            return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        }

        var tag = stack.getOrCreateTag();
        ContainerHelper.loadAllItems(tag, entity.items());
        if (tag.contains("Energy")) {
            entity.getEnergyStorage().setEnergy((long) Mth.clamp(tag.getLong("Energy"), 0, entity.getEnergyStorage().getMaxCapacity()));
            entity.onEnergyChange();
        }

        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
