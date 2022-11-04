package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModParticleTypes;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RocketTier4 extends Rocket {

    public RocketTier4(EntityType<?> type, Level level) {
        super(type, level, 4);
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 1.7f;
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_4_ROCKET.get().getDefaultInstance();
    }

    @Override
    public void spawnAfterburnerParticles() {
        super.spawnAfterburnerParticles();
        if (this.level instanceof ServerLevel serverWorld) {
            Vec3 pos = this.position();

            float xRotator = Mth.cos(this.getYRot() * ((float) Math.PI / 180.0f)) * 1.2f;
            float zRotator = Mth.sin(this.getYRot() * ((float) Math.PI / 180.0f)) * 1.2f;

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_FLAME.get(), pos.x() + xRotator, pos.y() + 0.35, pos.z() + zRotator, 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_SMOKE.get(), pos.x() + xRotator, pos.y() + 0.35, pos.z() + zRotator, 10, 0.1, 0.1, 0.1, 0.04);

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_FLAME.get(), pos.x() - xRotator, pos.y() + 0.35, pos.z() - zRotator, 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_SMOKE.get(), pos.x() - xRotator, pos.y() + 0.35, pos.z() - zRotator, 10, 0.1, 0.1, 0.1, 0.04);
        }
    }
}