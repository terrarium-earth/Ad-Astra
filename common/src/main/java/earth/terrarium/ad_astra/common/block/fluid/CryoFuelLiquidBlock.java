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


/*

package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {

    @Inject(method = "onPlace", at = @At("HEAD"))
    public void adastra_onBlockAdded(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!level.isClientSide) {
            LiquidBlock block = (LiquidBlock) (Object) this;
            if (block.getFluidState(state).is(FluidTags.WATER) && !block.equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                if (!OxygenUtils.posHasOxygen(level, pos)) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    // Turn water fluids into ice upon contact with cryo fuel
    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    public void adastra_receiveNeighborFluids(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        LiquidBlock block = (LiquidBlock) (Object) this;
        if (block.equals(Blocks.WATER)) {
            for (Direction direction : new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST}) {
                BlockPos blockPos = pos.relative(direction.getOpposite());
                if (level.getFluidState(blockPos).createLegacyBlock().getBlock().equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                    level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                    cir.setReturnValue(false);
                }
            }
        }
    }
}

 */