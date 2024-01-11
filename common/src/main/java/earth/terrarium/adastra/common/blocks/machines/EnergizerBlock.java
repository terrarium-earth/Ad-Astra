package earth.terrarium.adastra.common.blocks.machines;

import earth.terrarium.adastra.common.blockentities.machines.EnergizerBlockEntity;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

@SuppressWarnings("deprecation")
public class EnergizerBlock extends MachineBlock {

    public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 5);

    public EnergizerBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(LIT, false)
            .setValue(POWER, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return super.use(state, level, pos, player, hand, hit);
        if (!(level.getBlockEntity(pos) instanceof EnergizerBlockEntity entity)) {
            return super.use(state, level, pos, player, hand, hit);
        }

        var stack = player.getItemInHand(hand);
        if (entity.getItem(0).isEmpty() && !stack.isEmpty() && stack.getCount() == 1) {
            player.setItemInHand(hand, ItemStack.EMPTY);
            entity.setItem(0, stack);
        } else if (stack.isEmpty()) {
            player.setItemInHand(hand, entity.getItem(0));
            entity.clearContent();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWER);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof EnergizerBlockEntity entity) {
            return (int) (entity.getEnergyStorage().getStoredEnergy() / (float) entity.getEnergyStorage().getMaxCapacity() * 15);
        }
        return 0;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(blockEntity instanceof EnergizerBlockEntity entity)) return super.getDrops(blockState, builder);
        ItemStackHolder stack = new ItemStackHolder(ModItems.ENERGIZER.get().getDefaultInstance());
        EnergyContainer itemEnergyContainer = EnergyContainer.of(stack);
        if (itemEnergyContainer == null) return super.getDrops(blockState, builder);
        itemEnergyContainer.setEnergy(entity.getEnergyStorage().getStoredEnergy());
        stack.getStack().getOrCreateTagElement(Botarium.BOTARIUM_DATA)
            .putLong("Energy", entity.getEnergyStorage().getStoredEnergy());
        return List.of(stack.getStack());
    }
}