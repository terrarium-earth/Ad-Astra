package net.mrscauthd.beyond_earth.machines;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.machines.tile.WaterPumpBlockEntity;

public class WaterPump extends AbstractMachineBlock<WaterPumpBlockEntity> {

    public WaterPump(Properties properties) {
        super(properties);
    }
    
    @Override
    protected boolean useFacing() {
    	return true;
    }

    @Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();

        switch (state.getValue(FACING)) {
            case SOUTH :
            default :
                shape = Shapes.join(shape, Shapes.box(0.46875, 0.0625, 0.8125, 0.53125, 1, 0.875), BooleanOp.OR);//done
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.78125, 0.5625, 0.0625, 0.90625), BooleanOp.OR);
                return shape;
            case NORTH :
                shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.46875, 0.1875, 1, 0.53125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.09375, 0, 0.4375, 0.21875, 0.0625, 0.5625), BooleanOp.OR);//done
                return shape;
            case EAST :
                shape = Shapes.join(shape, Shapes.box(0.8125, 0.0625, 0.46875, 0.875, 1, 0.53125), BooleanOp.OR);//done
                shape = Shapes.join(shape, Shapes.box(0.78125, 0, 0.4375, 0.90625, 0.0625, 0.5625), BooleanOp.OR);
                return shape;
            case WEST :
                shape = Shapes.join(shape, Shapes.box(0.46875, 0.0625, 0.125, 0.53125, 1, 0.1875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.09375, 0.5625, 0.0625, 0.21875), BooleanOp.OR);
                return shape;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();

        if (this.useFacing()) {
            return state.setValue(FACING, context.getHorizontalDirection());
        } else {
            return state;
        }
    }

    @Override
	public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getTransferPerTickText(GaugeValueHelper.getFluid(WaterPumpBlockEntity.TRANSFER_PER_TICK))));
    }

    @Override
    public WaterPumpBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaterPumpBlockEntity(pos, state);
    }
    
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
    	return BlockPathTypes.BLOCKED;
    }
}
