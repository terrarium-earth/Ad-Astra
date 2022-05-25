package com.github.alexnijjar.beyond_earth.blocks.launch_pad;

import java.util.List;

import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier4;
import com.github.alexnijjar.beyond_earth.items.vehicles.RocketItem;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class RocketLaunchPad extends BlockWithEntity implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty STAGE = Properties.LIT;

    public RocketLaunchPad(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(WATERLOGGED, false).with(STAGE, false));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.ROCKET_LAUNCH_PAD_ENTITY, RocketLaunchPadEntity::serverTick);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);

        if (state.get(STAGE)) {
            return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 4, 16)).offset(offset.getX(), offset.getY(), offset.getZ());
        } else {
            return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 3, 16)).offset(offset.getX(), offset.getY(), offset.getZ());
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STAGE);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.hasTopRim(world, pos.down());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RocketLaunchPadEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {

            if (state.get(STAGE).equals(true)) {
                if (world.getBlockEntity(pos) instanceof RocketLaunchPadEntity launchPadEntity) {
                    ItemStack currentStack = player.getStackInHand(hand);
                    if (currentStack.getItem() instanceof RocketItem<?> rocket) {

                        RocketEntity rocketEntity = null;

                        int tier = rocket.getTier();
                        switch (tier) {
                        case 1 -> {
                            rocketEntity = new RocketEntityTier1(rocket.getRocketEntity(), world);
                        }
                        case 2 -> {
                            rocketEntity = new RocketEntityTier2(rocket.getRocketEntity(), world);
                        }
                        case 3 -> {
                            rocketEntity = new RocketEntityTier3(rocket.getRocketEntity(), world);
                        }
                        case 4 -> {
                            rocketEntity = new RocketEntityTier4(rocket.getRocketEntity(), world);
                        }
                        }

                        if (rocketEntity != null) {

                            // Check if a rocket is already placed on the pad.
                            Box scanAbove = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                            List<RocketEntity> entities = ((ServerWorld) world).getEntitiesByClass(RocketEntity.class, scanAbove, entity -> true);
                            if (!entities.isEmpty()) {
                                return ActionResult.PASS;
                            }

                            rocketEntity.setHomeLaunchPad(pos);
                            currentStack.decrement(1);
                            world.playSound(null, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_PLACE, SoundCategory.BLOCKS, 1, 1);

                            rocketEntity.setPosition(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
                            rocketEntity.setYaw(Math.round(player.getYaw() / 90) * 90);
                            world.spawnEntity(rocketEntity);

                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
}