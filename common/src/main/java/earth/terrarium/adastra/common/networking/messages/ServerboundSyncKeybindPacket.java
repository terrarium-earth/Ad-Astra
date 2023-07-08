package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.KeybindManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ServerboundSyncKeybindPacket(
    KeybindManager keybinds) implements Packet<ServerboundSyncKeybindPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_keybinds");
    public static final ServerboundSyncKeybindPacket.Handler HANDLER = new ServerboundSyncKeybindPacket.Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSyncKeybindPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundSyncKeybindPacket> {
        @Override
        public void encode(ServerboundSyncKeybindPacket packet, FriendlyByteBuf buf) {
            buf.writeByte(packet.keybinds().pack());
        }

        @Override
        public ServerboundSyncKeybindPacket decode(FriendlyByteBuf buf) {
            return new ServerboundSyncKeybindPacket(KeybindManager.unpack(buf.readByte()));
        }

        @Override
        public PacketContext handle(ServerboundSyncKeybindPacket packet) {
            return (player, level) -> KeybindManager.set(player, packet.keybinds());
        }
    }
}
