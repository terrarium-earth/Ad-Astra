package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.blocks.machines.OxygenSensorBlock;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class OxygenSensorBlockEntity extends AbstractMachineBlockEntity {

    public static final Direction[] CHECK_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP};

    public OxygenSensorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_SENSOR.get(), blockPos, blockState);
    }

    @Override
    public void tick() {
        if (this.world instanceof ServerWorld serverWorld) {
            boolean hasOxygen = false;
            for (Direction dir : CHECK_DIRECTIONS) {
                if (OxygenUtils.posHasOxygen(serverWorld, this.pos.offset(dir))) {
                    hasOxygen = true;
                    break;
                }
            }
            serverWorld.setBlockState(this.getPos(), this.getCachedState().with(OxygenSensorBlock.POWERED, hasOxygen));
        }
    }
}
