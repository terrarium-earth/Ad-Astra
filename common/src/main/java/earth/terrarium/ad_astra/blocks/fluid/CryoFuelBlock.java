package earth.terrarium.ad_astra.blocks.fluid;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;

public class CryoFuelBlock extends ArchitecturyLiquidBlock {

    public CryoFuelBlock(Supplier<FlowingFluid> fluid, Properties settings) {
        super(fluid, settings);
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
}
