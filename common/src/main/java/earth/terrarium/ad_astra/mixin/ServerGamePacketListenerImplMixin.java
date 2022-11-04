package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.entities.vehicles.Vehicle;
import earth.terrarium.ad_astra.items.armour.JetSuit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    private int aboveGroundTickCount;
    @Shadow
    private int aboveGroundVehicleTickCount;

    @Inject(method = "tick", at = @At("HEAD"))
    public void adastra_tick(CallbackInfo ci) {
        ServerPlayer player = ((ServerGamePacketListenerImpl) (Object) this).player;

        // Prevent the player from being kicked for flying a jet suit
        if (!player.isOnGround() && JetSuit.hasFullSet(player)) {
            this.aboveGroundTickCount = 0;
        }

        // Prevent the player from being kicked for flying in a rocket
        if (player.getVehicle() instanceof Vehicle) {
            this.aboveGroundVehicleTickCount = 0;
        }
    }
}
