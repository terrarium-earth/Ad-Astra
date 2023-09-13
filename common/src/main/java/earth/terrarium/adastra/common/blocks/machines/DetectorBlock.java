package earth.terrarium.adastra.common.blocks.machines;

import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Locale;

@SuppressWarnings("deprecation")
public class DetectorBlock extends MachineBlock {
    public static final BooleanProperty INVERTED = BooleanProperty.create("inverted");
    public static final EnumProperty<DetectionType> DETECTION_TYPE = EnumProperty.create("detection_type", DetectionType.class);

    public DetectorBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(LIT, false)
            .setValue(INVERTED, false)
            .setValue(DETECTION_TYPE, DetectionType.OXYGEN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(INVERTED, DETECTION_TYPE);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (player.isShiftKeyDown()) {
            level.setBlockAndUpdate(pos, state.cycle(INVERTED));
            player.displayClientMessage(!state.getValue(INVERTED) ? ConstantComponents.DETECTOR_INVERTED_TRUE : ConstantComponents.DETECTOR_INVERTED_FALSE, true);
        } else {
            BlockState newState = state.cycle(DETECTION_TYPE);
            level.setBlockAndUpdate(pos, newState);
            DetectionType newType = newState.getValue(DETECTION_TYPE);
            player.displayClientMessage(switch (newType) {
                case OXYGEN -> ConstantComponents.DETECTOR_OXYGEN_MODE;
                case GRAVITY -> ConstantComponents.DETECTOR_GRAVITY_MODE;
                case TEMPERATURE -> ConstantComponents.DETECTOR_TEMPERATURE_MODE;
            }, true);
        }
        return InteractionResult.SUCCESS;
    }

    public enum DetectionType implements StringRepresentable {
        OXYGEN,
        GRAVITY,
        TEMPERATURE,
        ;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }
}