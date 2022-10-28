package earth.terrarium.ad_astra.blocks.machines;

import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import earth.terrarium.botarium.api.menu.MenuHooks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public abstract class AbstractMachineBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public AbstractMachineBlock(Settings settings) {
        super(settings.luminance(getLuminance()));
        this.setDefaultState(this.buildDefaultState());
    }

    private static ToIntFunction<BlockState> getLuminance() {
        return (blockState) -> blockState.contains(LIT) ? (blockState.get(LIT) ? ((AbstractMachineBlock) blockState.getBlock()).getBrightness() : 0) : 0;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof AbstractMachineBlockEntity machine) {
                machine.tick();
            }
        };
    }

    protected BlockState buildDefaultState() {
        BlockState state = this.stateManager.getDefaultState();

        state = state.with(POWERED, false);
        if (this.useFacing()) {
            state = state.with(FACING, Direction.NORTH);
        }
        if (this.useLit()) {
            state = state.with(LIT, false);
        }

        return state;
    }

    protected boolean useFacing() {
        return false;
    }

    protected boolean useLit() {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
                MenuHooks.openMenu((ServerPlayerEntity) player, machineBlock);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        if (this.useFacing()) {
            return state.with(FACING, rotation.rotate(state.get(FACING)));
        } else {
            return state;
        }
    }

    public int getBrightness() {
        return 12;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractMachineBlockEntity machineBlock) {
                if (machineBlock.getInventorySize() > 0) {
                    if (this.removeOutput()) {
                        machineBlock.removeStack(machineBlock.getInventorySize() - 1);
                    }
                    ItemScatterer.spawn(world, pos, machineBlock);
                    world.updateComparators(pos, this);
                }
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public boolean removeOutput() {
        return false;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        if (this.useFacing()) {
            builder.add(FACING);
        }

        builder.add(POWERED);

        if (this.useLit()) {
            builder.add(LIT);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState().with(POWERED, false);
        return this.useFacing() ? state.with(FACING, ctx.getPlayerFacing().getOpposite()) : state;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public boolean doRedstoneCheck() {
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        if (this.doRedstoneCheck()) {
            if (!world.isClient) {
                world.setBlockState(pos, state.with(POWERED, world.isReceivingRedstonePower(pos)));
            }
        }
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        return blockEntity instanceof AbstractMachineBlockEntity ? ScreenHandler.calculateComparatorOutput(blockEntity) : 0;
    }

    // Get nbt in stack when picking block
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = super.getPickStack(world, pos, state);
        if (world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
            NbtCompound nbt = stack.getOrCreateNbt();
            Inventories.writeNbt(nbt, machineBlock.getItems());
            nbt.putLong("energy", machineBlock.getEnergyStorage().getStoredEnergy());

            if (machineBlock instanceof FluidMachineBlockEntity fluidMachine) {
                if (fluidMachine.getInputTank().getCompound() != null) {
                    nbt.put("inputFluid", fluidMachine.getInputTank().getCompound());
                    nbt.putLong("inputAmount", fluidMachine.getInputTank().getFluidAmount());
                }

                if (fluidMachine.getOutputTank().getCompound() != null) {
                    nbt.put("outputFluid", fluidMachine.getOutputTank().getCompound());
                    nbt.putLong("outputAmount", fluidMachine.getOutputTank().getFluidAmount());
                }

                if (machineBlock instanceof OxygenDistributorBlockEntity oxygenDistributorMachine) {
                    nbt.putBoolean("showOxygen", oxygenDistributorMachine.shouldShowOxygen());
                }
            }
        }
        return stack;
    }
}