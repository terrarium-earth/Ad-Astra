// package com.github.alexnijjar.beyond_earth.blocks.cables;

// import net.minecraft.block.BlockState;
// import net.minecraft.block.BlockWithEntity;
// import net.minecraft.block.Waterloggable;
// import net.minecraft.block.entity.BlockEntity;
// import net.minecraft.block.entity.BlockEntityTicker;
// import net.minecraft.block.entity.BlockEntityType;
// import net.minecraft.state.property.BooleanProperty;
// import net.minecraft.state.property.Properties;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.world.World;

// public class CableBlock extends BlockWithEntity implements Waterloggable {

// 	public static final BooleanProperty UP = BooleanProperty.of("up");
// 	public static final BooleanProperty DOWN = BooleanProperty.of("down");
// 	public static final BooleanProperty NORTH = BooleanProperty.of("north");
//     public static final BooleanProperty EAST = BooleanProperty.of("east");
// 	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
// 	public static final BooleanProperty WEST = BooleanProperty.of("west");
//     public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    
//     public CableBlock(Settings settings) {
//         super(settings);
//         this.setDefaultState(getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
//     }

//     @Override
//     public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
//         return new CableBlockEntity(pos, state);
//     }

//     @Override
//     public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//         return (entityWorld, pos, entityState, blockEntity) -> {
//             if (blockEntity instanceof CableBlockEntity cable) {
//                 cable.tick();
//             }
//         };
//     }
// }