package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.adastra.common.items.armor.JetSuitItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {

    @Shadow
    private int aboveGroundTickCount;
    @Shadow
    private int aboveGroundVehicleTickCount;

    @Shadow
    public ServerPlayer player;

    @Inject(method = "tick", at = @At("HEAD"))
    public void adastra$tick(CallbackInfo ci) {
        if (player.tickCount % 50 == 0) {
            // Prevent the player from being kicked for flying a jet suit.
            if (!player.onGround() && JetSuitItem.hasFullSet(player)) {
                aboveGroundTickCount = 0;
            }

            // Prevent the player from being kicked for flying in a rocket.
            if (player.getVehicle() instanceof Vehicle) {
                aboveGroundVehicleTickCount = 0;
            }
        }
    }
}