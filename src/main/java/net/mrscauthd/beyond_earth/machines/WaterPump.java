package net.mrscauthd.beyond_earth.machines;

import static net.mrscauthd.beyond_earth.block.helper.VoxelShapeHelper.boxSimple;

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
import net.minecraft.world.phys.Vec3;
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
        Vec3 offset = state.getOffset(level, pos);
        switch (state.getValue(FACING)) {
            case SOUTH :
            default :
                return Shapes.or(boxSimple(5.5, 0, 10.5, 10.5, 1, 5.5), boxSimple(6, 1, 10, 10, 13, 6), boxSimple(5.5, 13, 10.5, 10.5, 16, 5.5), boxSimple(6, 6, 15, 10, 10, 6), boxSimple(5.5, 5.5, 16, 10.5, 10.5, 15)).move(offset.x, offset.y, offset.z);
                case NORTH :
                return Shapes.or(boxSimple(5.5, 0, 10.5, 10.5, 1, 5.5), boxSimple(6, 1, 10, 10, 13, 6), boxSimple(5.5, 13, 10.5, 10.5, 16, 5.5), boxSimple(6, 6, 1, 10, 10, 6), boxSimple(5.5, 5.5, 1, 10.5, 10.5, 0)).move(offset.x, offset.y, offset.z);
            case EAST :
                return Shapes.or(boxSimple(5.5, 0, 10.5, 10.5, 1, 5.5), boxSimple(6, 1, 10, 10, 13, 6), boxSimple(5.5, 13, 10.5, 10.5, 16, 5.5), boxSimple(15, 6, 10, 10, 10, 6), boxSimple(16, 5.5, 10.5, 15, 10.5, 5.5)).move(offset.x, offset.y, offset.z);
            case WEST :
                return Shapes.or(boxSimple(5.5, 0, 10.5, 10.5, 1, 5.5), boxSimple(6, 1, 10, 10, 13, 6), boxSimple(5.5, 13, 10.5, 10.5, 16, 5.5), boxSimple(1, 6, 6, 10, 10, 10), boxSimple(0, 5.5, 5.5, 1, 10.5, 10.5)).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();

        if (this.useFacing() == true) {
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
