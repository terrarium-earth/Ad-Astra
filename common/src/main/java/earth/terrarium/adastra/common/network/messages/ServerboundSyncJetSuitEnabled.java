package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.KeybindManager;
import net.minecraft.resources.ResourceLocation;

public record ServerboundSyncJetSuitEnabled(boolean suitFlightEnabled) implements Packet<ServerboundSyncJetSuitEnabled> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_jet_suit_enabled");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSyncJetSuitEnabled> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSyncJetSuitEnabled> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.BOOLEAN.fieldOf(ServerboundSyncJetSuitEnabled::suitFlightEnabled),
                ServerboundSyncJetSuitEnabled::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSyncJetSuitEnabled packet) {
            return (player, level) -> KeybindManager.set(player, packet.suitFlightEnabled());
        }
    }
}
