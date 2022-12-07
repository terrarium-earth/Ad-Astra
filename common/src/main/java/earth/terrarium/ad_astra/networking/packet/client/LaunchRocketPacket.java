package earth.terrarium.ad_astra.networking.packet.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packet.server.StartRocketPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record LaunchRocketPacket() implements Packet<LaunchRocketPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "launch_rocket_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<LaunchRocketPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<LaunchRocketPacket> {
        @Override
        public void encode(LaunchRocketPacket packet, FriendlyByteBuf buf) {
        }

        @Override
        public LaunchRocketPacket decode(FriendlyByteBuf buf) {
            return new LaunchRocketPacket();
        }

        @Override
        public PacketContext handle(LaunchRocketPacket packet) {
            return (player, level) -> {
                if (player.getVehicle() instanceof Rocket rocket) {
                    if (!rocket.isFlying()) {
                        if (rocket.getTankAmount() >= Rocket.getRequiredAmountForLaunch(rocket.getTankFluid())) {
                            rocket.initiateLaunchSequenceFromServer();
                            // Tell all clients to start rendering the rocket launch
                            NetworkHandling.CHANNEL.sendToAllLoaded(new StartRocketPacket(rocket.getId()), level, rocket.getOnPos());
                        } else {
                            player.displayClientMessage(Component.translatable("message.ad_astra.no_fuel"), false);
                        }
                    }
                }
            };
        }
    }
}
