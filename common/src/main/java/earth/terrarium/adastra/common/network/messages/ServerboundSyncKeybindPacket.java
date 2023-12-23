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

public record ServerboundSyncKeybindPacket(boolean jumping, boolean sprinting,
                                           boolean suitFlightEnabled) implements Packet<ServerboundSyncKeybindPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_keybinds");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSyncKeybindPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSyncKeybindPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::jumping),
                ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::sprinting),
                ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::suitFlightEnabled),
                ServerboundSyncKeybindPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSyncKeybindPacket packet) {
            return (player, level) -> KeybindManager.set(player, packet.jumping(), packet.sprinting(), packet.suitFlightEnabled());
        }
    }
}
