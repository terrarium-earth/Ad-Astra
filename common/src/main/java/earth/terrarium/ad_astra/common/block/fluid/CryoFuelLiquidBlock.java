package earth.terrarium.ad_astra.common.block.fluid;

import earth.terrarium.ad_astra.common.registry.ModDamageSource;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.botarium.api.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.api.registry.fluid.FluidData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("deprecation")
public class CryoFuelLiquidBlock extends BotariumLiquidBlock {

    public CryoFuelLiquidBlock(FluidData data, Properties properties) {
        super(data, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.makeStuckInBlock(state, new Vec3(0.9f, 1.5, 0.9f));
            if (level.isClientSide) {
                RandomSource random = level.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    level.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), pos.getY() + 1, entity.getZ(), Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }
            entity.setIsInPowderSnow(true);
            entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze(), entity.getTicksFrozen() + 5));
            if (!level.isClientSide) {
                entity.setSharedFlagOnFire(false);
                entity.hurt(ModDamageSource.CRYO_FUEL, 4 * (entity.fireImmune() ? 2 : 1));
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (this.shouldSpreadLiquid(level, pos, state)) {
            level.scheduleTick(pos, state.getFluidState().getType(), ModFluids.CRYO_FUEL.get().getTickDelay(level));
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (this.shouldSpreadLiquid(level, pos, state)) {
            level.scheduleTick(pos, state.getFluidState().getType(), ModFluids.CRYO_FUEL.get().getTickDelay(level));
        }
    }

    // Turn water fluids into ice upon contact with cryo fuel
    private boolean shouldSpreadLiquid(Level level, BlockPos pos, BlockState state) {
        for (Direction direction : new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST}) {
            BlockPos blockPos = pos.relative(direction.getOpposite());
            FluidState fluidState = level.getFluidState(blockPos);
            if (fluidState.is(Fluids.WATER) || fluidState.is(Fluids.FLOWING_WATER)) {
                level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                level.levelEvent(LevelEvent.LAVA_FIZZ, pos, 0);
                return false;
            }
        }
        return true;
    }

    private void fizz(LevelAccessor level, BlockPos pos) {
        level.levelEvent(LevelEvent.LAVA_FIZZ, pos, 0);
    }


}
