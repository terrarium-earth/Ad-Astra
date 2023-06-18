package earth.terrarium.ad_astra.common.block.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.EnergizerBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class EnergizerBlock extends AbstractMachineBlock {

    public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 5);

    public EnergizerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    protected boolean useLit() {
        return true;
    }

    @Override
    public int getBrightness() {
        return 2;
    }

    @Override
    protected BlockState buildDefaultState() {
        return super.buildDefaultState().setValue(POWER, 0);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof EnergizerBlockEntity entity) {
                ItemStack playerStack = player.getItemInHand(hand);
                if (entity.getItem(0).isEmpty() && !playerStack.isEmpty() && playerStack.getCount() == 1) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    entity.setItem(0, playerStack);
                    return InteractionResult.SUCCESS;
                } else if (playerStack.isEmpty()) {
                    player.setItemInHand(hand, entity.getItem(0));
                    entity.clearContent();
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWER);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack stack = ModItems.ENERGIZER.get().getDefaultInstance();
        stack.getOrCreateTag().putLong("Energy", ((EnergizerBlockEntity) blockEntity).getEnergyStorage().getStoredEnergy());
        return List.of(stack);
    }
}