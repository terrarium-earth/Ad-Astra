package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.MachineBlock;
import earth.terrarium.ad_astra.common.block.machine.MachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

@MethodsReturnNonnullByDefault
public class OxygenSensorBlockEntity extends MachineBlockEntity {
    public OxygenSensorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.OXYGEN_SENSOR.get(), blockPos, blockState);
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (level.getGameTime() % 20 == 0) {
            boolean hasOxygen = false;
            for (Direction dir : Direction.values()) {
//                if (OxygenUtils.posHasOxygen(level, this.worldPosition.relative(dir))) { // TODO
                hasOxygen = true;
                break;
//                }
            }
            level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(MachineBlock.POWERED, hasOxygen));
        }
    }
}
