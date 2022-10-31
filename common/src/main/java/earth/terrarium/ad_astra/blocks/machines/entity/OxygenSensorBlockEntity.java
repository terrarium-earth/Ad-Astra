package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.blocks.machines.OxygenSensorBlock;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenSensorBlockEntity extends AbstractMachineBlockEntity {

    public static final Direction[] CHECK_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP};

    public OxygenSensorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_SENSOR.get(), blockPos, blockState);
    }

    @Override
    public void tick() {
        if (this.level instanceof ServerLevel serverWorld) {
            boolean hasOxygen = false;
            for (Direction dir : CHECK_DIRECTIONS) {
                if (OxygenUtils.posHasOxygen(serverWorld, this.worldPosition.relative(dir))) {
                    hasOxygen = true;
                    break;
                }
            }
            serverWorld.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(OxygenSensorBlock.POWERED, hasOxygen));
        }
    }
}
