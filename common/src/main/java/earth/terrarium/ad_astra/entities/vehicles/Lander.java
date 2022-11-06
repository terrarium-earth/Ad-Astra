package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.screen.LanderMenuProvider;
import earth.terrarium.ad_astra.util.ModKeyBindings;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Lander extends Vehicle {

    public Lander(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public int getInventorySize() {
        return 11;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        this.openInventory(player, new LanderMenuProvider(this));
        return InteractionResult.SUCCESS;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 1.6f;
    }

    // Drop inventory contents instead of dropping itself.
    @Override
    public void drop() {
        Containers.dropContents(this.level, this.blockPosition(), this.getInventory());
        super.drop();
    }

    @Override
    public boolean shouldRenderPlayer() {
        return false;
    }

    @Override
    public boolean doHighFov() {
        return true;
    }

    @Override
    public boolean fullyConcealsRider() {
        return true;
    }

    @Override
    public boolean canRiderTakeFallDamage() {
        return false;
    }

    @Override
    public boolean renderPlanetBar() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        // Player is clicking 'space' to move upward.
        if (this.getFirstPassenger() instanceof Player player) {
            if (!this.isOnGround()) {
                if (ModKeyBindings.jumpKeyDown(player)) {
                    this.applyBoosters();
                }
            }
        }
    }

    public void applyBoosters() {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.1, 0.0));

        if (this.getDeltaMovement().y() > AdAstra.CONFIG.lander.boosterThreshold) {
            this.setDeltaMovement(0.0, AdAstra.CONFIG.lander.boosterThreshold, 0.0);
        }

        // Particles
        if (!this.level.isClientSide) {
            ModUtils.spawnForcedParticles((ServerLevel) this.getLevel(), ParticleTypes.SPIT, this.getX(), this.getY() - 0.3, this.getZ(), 3, 0.1, 0.1, 0.1, 0.001);
        }
    }
}