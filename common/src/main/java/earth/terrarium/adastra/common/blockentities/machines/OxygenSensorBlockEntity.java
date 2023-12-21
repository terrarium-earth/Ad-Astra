package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.blockentities.base.MachineBlockEntity;
import earth.terrarium.adastra.common.blocks.machines.OxygenSensorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenSensorBlockEntity extends MachineBlockEntity {

    public OxygenSensorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }


    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (time % 40 != 0) return;
        boolean inverted = state.getValue(OxygenSensorBlock.INVERTED);
        boolean power = hasOxygen(level, pos, inverted);
        if (power != state.getValue(OxygenSensorBlock.POWERED)) {
            level.setBlockAndUpdate(pos, state.setValue(OxygenSensorBlock.POWERED, power));
        }
    }

    public boolean hasOxygen(ServerLevel level, BlockPos pos, boolean inverted) {
        for (var direction : Direction.values()) {
            if (OxygenApi.API.hasOxygen(level, pos.relative(direction))) return !inverted;
        }
        return inverted;
    }
}
