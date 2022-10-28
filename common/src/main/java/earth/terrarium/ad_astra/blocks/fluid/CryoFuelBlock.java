package earth.terrarium.ad_astra.blocks.fluid;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class CryoFuelBlock extends ArchitecturyLiquidBlock {

    public CryoFuelBlock(Supplier<FlowableFluid> fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.slowMovement(state, new Vec3d(0.9f, 1.5, 0.9f));
            if (world.isClient) {
                RandomGenerator random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }
            entity.setInPowderSnow(true);
            entity.setFrozenTicks(Math.min(entity.getMinFreezeDamageTicks(), entity.getFrozenTicks() + 5));
            if (!world.isClient) {
                entity.setOnFire(false);
                entity.damage(ModDamageSource.CRYO_FUEL, 4 * (entity.isFireImmune() ? 2 : 1));
            }
        }
    }
}
