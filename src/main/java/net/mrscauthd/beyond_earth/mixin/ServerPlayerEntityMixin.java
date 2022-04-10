package net.mrscauthd.beyond_earth.mixin;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ModUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntity {

    protected ServerPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        // Teleport the player to the planet when they fall in the void while in an orbit dimension.
        RegistryKey<World> key = this.world.getRegistryKey();

        if (this.getY() < world.getBottomY() && ModUtils.isOrbitDimension(key)) {
            ModUtils.teleportToPlanet(key, (ServerPlayerEntity) (Object) this);
        }
    }
}
// TODO: check if player is not in a lander entity.
//        if (this.getY() < 0 && !(this.getVehicle() instanceof LanderEntity)) {