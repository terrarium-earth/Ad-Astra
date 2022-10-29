package earth.terrarium.ad_astra.networking.packets.server;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.screen.handler.AbstractMachineScreenHandler;
import earth.terrarium.ad_astra.screen.handler.AbstractVehicleScreenHandler;
import earth.terrarium.ad_astra.util.ModIdentifier;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public record MachineInfoPacket(long energy, List<FluidHolder> fluidHolders) implements Packet<MachineInfoPacket> {

    public static final Identifier ID = new ModIdentifier("machine_info");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<MachineInfoPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<MachineInfoPacket> {
        @Override
        public void encode(MachineInfoPacket packet, PacketByteBuf buf) {
            buf.writeLong(packet.energy());
            buf.writeCollection(packet.fluidHolders, (buf2, fluid) -> {
                buf2.writeIdentifier(Registry.FLUID.getId(fluid.getFluid()));
                buf2.writeLong(fluid.getFluidAmount());
            });
        }

        @Override
        public MachineInfoPacket decode(PacketByteBuf buf) {
            return new MachineInfoPacket(buf.readLong(), buf.readList(buf2 -> FluidHooks.newFluidHolder(Registry.FLUID.get(buf2.readIdentifier()), buf2.readLong(), null)));
        }

        @Override
        public PacketContext handle(MachineInfoPacket packet) {
            return (player, world) -> {
                if (player.currentScreenHandler instanceof AbstractMachineScreenHandler<?> handler) {
                    handler.setEnergyAmount(packet.energy());
                    handler.setFluids(packet.fluidHolders);
                } else if (player.currentScreenHandler instanceof AbstractVehicleScreenHandler handler) {
                    handler.setFluids(packet.fluidHolders);
                }
            };
        }
    }
}
