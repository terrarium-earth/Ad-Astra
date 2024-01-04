package earth.terrarium.adastra.mixins.fabric.common;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @WrapWithCondition(
        method = "changeDimension",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V",
            ordinal = 5
        )
    )
    private boolean adastra$changeDimension(ServerGamePacketListenerImpl instance, Packet<?> packet) {
        return !(getVehicle() instanceof Vehicle);
    }
}
