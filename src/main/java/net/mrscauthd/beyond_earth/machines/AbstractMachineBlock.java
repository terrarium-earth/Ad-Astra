package net.mrscauthd.beyond_earth.machines;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.beyond_earth.machines.tile.AbstractMachineBlockEntity;

public abstract class AbstractMachineBlock<T extends AbstractMachineBlockEntity> extends Block implements EntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public AbstractMachineBlock(Properties properties) {
		super(properties);

		this.registerDefaultState(this.buildDefaultState());
	}

	protected BlockState buildDefaultState() {
		BlockState any = this.stateDefinition.any();

		if (this.useFacing() == true) {
			any = any.setValue(FACING, Direction.NORTH);
		}

		if (this.useLit() == true) {
			any = any.setValue(LIT, false);
		}

		return any;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);

		if (this.useFacing() == true) {
			builder.add(FACING);
		}

		if (this.useLit() == true) {
			builder.add(LIT);
		}

	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		if (this.useFacing() == true) {
			return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
		} else {
			return state;
		}

	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = this.defaultBlockState();

		if (this.useFacing() == true) {
			return state.setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return state;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		if (this.useLit() == true) {
			return state.getValue(LIT) ? 12 : 0;
		} else {
			return super.getLightBlock(state, level, pos);
		}

	}

	protected boolean useFacing() {
		return false;
	}

	protected boolean useLit() {
		return false;
	}

	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			T blockEntity = this.getBlockEntity(level, pos);

			if (blockEntity != null) {
				Containers.dropContents(level, pos, blockEntity);
			}
		}

		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player entity, InteractionHand hand,
			BlockHitResult raytrace) {
		if (entity instanceof ServerPlayer) {
			T blockEntity = this.getBlockEntity(level, pos);

			if (blockEntity != null) {
				NetworkHooks.openGui((ServerPlayer) entity, blockEntity, pos);
			}

			return InteractionResult.CONSUME;
		} else {
			return InteractionResult.SUCCESS;
		}

	}

	@SuppressWarnings("unchecked")
	public T getBlockEntity(Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof AbstractMachineBlockEntity) {
			return (T) blockEntity;
		}

		return null;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		@SuppressWarnings("deprecation")
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty()) {
			return dropsOriginal;
		} else {
			return Collections.singletonList(new ItemStack(this));
		}

	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	@Nullable
	public abstract T newBlockEntity(BlockPos pos, BlockState state);

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public <T2 extends BlockEntity> BlockEntityTicker<T2> getTicker(Level level, BlockState state, BlockEntityType<T2> type) {
		return (l, p, s, e) -> { if (e instanceof AbstractMachineBlockEntity) { ((AbstractMachineBlockEntity) e).tick(); } };
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof AbstractMachineBlockEntity) {
			return AbstractContainerMenu.getRedstoneSignalFromContainer((AbstractMachineBlockEntity) blockEntity);
		} else {
			return 0;
		}

	}

}
