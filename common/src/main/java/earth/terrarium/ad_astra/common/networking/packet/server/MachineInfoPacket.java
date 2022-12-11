package earth.terrarium.ad_astra.common.networking.packet.server;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import earth.terrarium.ad_astra.common.screen.menu.AbstractVehicleMenu;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record MachineInfoPacket(long energy, List<FluidHolder> fluidHolders) implements Packet<MachineInfoPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "machine_info_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<MachineInfoPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<MachineInfoPacket> {
        @Override
        public void encode(MachineInfoPacket packet, FriendlyByteBuf buf) {
            buf.writeLong(packet.energy());
            buf.writeCollection(packet.fluidHolders, (buf2, fluid) -> {
                buf2.writeResourceLocation(Registry.FLUID.getKey(fluid.getFluid()));
                buf2.writeLong(fluid.getFluidAmount());
            });
        }

        @Override
        public MachineInfoPacket decode(FriendlyByteBuf buf) {
            return new MachineInfoPacket(buf.readLong(), buf.readList(buf2 -> FluidHooks.newFluidHolder(Registry.FLUID.get(buf2.readResourceLocation()), buf2.readLong(), null)));
        }

        @Override
        public PacketContext handle(MachineInfoPacket packet) {
            return (player, level) -> {
                if (player.containerMenu instanceof AbstractMachineMenu<?> handler) {
                    handler.setEnergyAmount(packet.energy());
                    handler.setFluids(packet.fluidHolders);
                } else if (player.containerMenu instanceof AbstractVehicleMenu handler) {
                    handler.setFluids(packet.fluidHolders);
                }
            };
        }
    }
}
