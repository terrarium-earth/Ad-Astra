package earth.terrarium.adastra.mixins.fabric.common;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//TODO: Check if this is correct!
@Mixin(DimensionTransition.class)
public abstract class DimensionTransitionMixin {

    @WrapWithCondition(
        method = "playPortalSound(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V",
            ordinal = 0
        )
    )
    // Prevent the portal teleportation sound from playing when leaving the atmosphere.
    private static boolean adastra$changeDimension(ServerGamePacketListenerImpl instance, Packet<?> packet, Entity entity) {
        return entity.getY() > AdAstraConfig.atmosphereLeave + 1 || entity.getY() < AdAstraConfig.atmosphereLeave - 1;
    }
}
