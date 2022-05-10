package net.mrscauthd.beyond_earth.entities.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModItems;
import net.mrscauthd.beyond_earth.registry.ModParticles;

public class RocketEntityTier4 extends RocketEntity {

    public RocketEntityTier4(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 1.7f;
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_4_ROCKET.getDefaultStack();
    }

    @Override
    public float getAfterburnerLength() {
        return 2.9f;
    }

    @Override
    public void spawnAfterburnerParticles() {
        super.spawnAfterburnerParticles();
        Vec3d pos = this.getPos();
        if (this.world.isClient) {
            this.world.addImportantParticle(ModParticles.SMALL_FLAME, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            this.world.addImportantParticle(ModParticles.SMALL_SMOKE, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
        }
    }
}