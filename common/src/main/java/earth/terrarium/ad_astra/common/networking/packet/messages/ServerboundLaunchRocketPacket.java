package earth.terrarium.ad_astra.common.networking.packet.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.networking.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record ServerboundLaunchRocketPacket() implements Packet<ServerboundLaunchRocketPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "launch_rocket");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundLaunchRocketPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundLaunchRocketPacket> {
        @Override
        public void encode(ServerboundLaunchRocketPacket packet, FriendlyByteBuf buf) {
        }

        @Override
        public ServerboundLaunchRocketPacket decode(FriendlyByteBuf buf) {
            return new ServerboundLaunchRocketPacket();
        }

        @Override
        public PacketContext handle(ServerboundLaunchRocketPacket packet) {
            return (player, level) -> {
                if (player.getVehicle() instanceof Rocket rocket) {
                    if (!rocket.isFlying()) {
                        if (rocket.getTankAmount() >= Rocket.getRequiredAmountForLaunch(rocket.getTankFluid())) {
                            rocket.initiateLaunchSequenceFromServer();
                            // Tell all clients to start rendering the rocket launch
                            NetworkHandler.CHANNEL.sendToAllLoaded(new ClientboundStartRocketPacket(rocket.getId()), level, rocket.getOnPos());
                        } else {
                            player.displayClientMessage(Component.translatable("message.ad_astra.no_fuel"), false);
                        }
                    }
                }
            };
        }
    }
}
