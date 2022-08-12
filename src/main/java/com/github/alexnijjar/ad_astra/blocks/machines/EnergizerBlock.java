package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.EnergizerBlockEntity;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class EnergizerBlock extends AbstractMachineBlock {

	public static final IntProperty POWER = IntProperty.of("power", 0, 5);

	public EnergizerBlock(Settings settings) {
		super(settings);
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
		return super.buildDefaultState().with(POWER, 0);
	}

	@Override
	public EnergizerBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EnergizerBlockEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			if (world.getBlockEntity(pos) instanceof EnergizerBlockEntity entity) {
				ItemStack playerStack = player.getStackInHand(hand);
				if (entity.getStack(0).isEmpty() && !playerStack.isEmpty() && playerStack.getCount() == 1) {
					player.setStackInHand(hand, ItemStack.EMPTY);
					entity.setStack(0, playerStack);
					return ActionResult.SUCCESS;
				} else if (playerStack.isEmpty()) {
					player.setStackInHand(hand, entity.getStack(0));
					entity.clear();
					return ActionResult.SUCCESS;
				}
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(POWER);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, net.minecraft.loot.context.LootContext.Builder builder) {
		BlockEntity blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
		ItemStack stack = ModItems.ENERGIZER.getDefaultStack();
		stack.getOrCreateNbt().putLong("energy", ((EnergizerBlockEntity) blockEntity).energyStorage.amount);
		return List.of(stack);
	}
}