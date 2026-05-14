package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.blockentities.base.MachineBlockEntity;
import earth.terrarium.adastra.common.blocks.machines.DetectorBlock;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class DetectorBlockEntity extends MachineBlockEntity {

    public DetectorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }


    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (time % 40 != 0) return;
        boolean inverted = state.getValue(DetectorBlock.INVERTED);
        boolean power = switch (state.getValue(DetectorBlock.DETECTION_TYPE)) {
            case OXYGEN -> hasOxygen(level, pos, inverted);
            case GRAVITY -> hasNormalGravity(level, pos, inverted);
            case TEMPERATURE -> hasSafeTemperature(level, pos, inverted);
        };
        if (power != state.getValue(DetectorBlock.POWERED)) {
            level.setBlockAndUpdate(pos, state.setValue(DetectorBlock.POWERED, power));
        }
    }

    public boolean hasOxygen(ServerLevel level, BlockPos pos, boolean inverted) {
        for (var direction : Direction.values()) {
            if (OxygenApi.API.hasOxygen(level, pos.relative(direction))) return !inverted;
        }
        return inverted;
    }

    public boolean hasNormalGravity(ServerLevel level, BlockPos pos, boolean inverted) {
        for (var direction : Direction.values()) {
            float gravity = GravityApi.API.getGravity(level, pos.relative(direction)) * PlanetConstants.EARTH_GRAVITY;
            if (gravity > PlanetConstants.EARTH_GRAVITY - 1 && gravity < PlanetConstants.EARTH_GRAVITY + 1) {
                return !inverted;
            }
        }
        return inverted;
    }

    public boolean hasSafeTemperature(ServerLevel level, BlockPos pos, boolean inverted) {
        for (var direction : Direction.values()) {
            if (TemperatureApi.API.isLiveable(level, pos.relative(direction))) return !inverted;
        }
        return inverted;
    }
}
