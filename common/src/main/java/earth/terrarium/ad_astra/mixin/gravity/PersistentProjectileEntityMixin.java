package earth.terrarium.ad_astra.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Vec3d;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

	@Unique
	private static final double CONSTANT = 0.05;

	@Inject(method = "tick", at = @At("TAIL"))
	public void adastra_tick(CallbackInfo ci) {
		if (AdAstra.CONFIG.general.doEntityGravity) {
			Entity entity = (Entity) (Object) this;
			if (!entity.hasNoGravity()) {
				Vec3d velocity = entity.getVelocity();
				double newGravity = CONSTANT * ModUtils.getPlanetGravity(entity.world);
				entity.setVelocity(velocity.getX(), velocity.getY() + CONSTANT - newGravity, velocity.getZ());
			}
		}
	}
}