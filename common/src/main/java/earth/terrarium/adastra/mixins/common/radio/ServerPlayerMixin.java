package earth.terrarium.adastra.mixins.common.radio;

import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundPlayStationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "startRiding", at = @At("RETURN"))
    private void adastra$startRiding(Entity vehicle, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            Player player = (Player) ((Object) this);
            if (player.getVehicle() instanceof RadioHolder holder) {
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundPlayStationPacket(holder.getRadioUrl()), player);
            }
        }
    }
}
