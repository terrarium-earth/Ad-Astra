package com.github.alexnijjar.ad_astra.networking.packets.client;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public record KeybindPacket(Keybind keybind, boolean pressed) implements Packet<KeybindPacket> {

    public static final Identifier ID = new ModIdentifier("key_changed");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<KeybindPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<KeybindPacket> {
        @Override
        public void encode(KeybindPacket keybindPacket, PacketByteBuf buf) {
            buf.writeEnumConstant(keybindPacket.keybind);
            buf.writeBoolean(keybindPacket.pressed);
        }

        @Override
        public KeybindPacket decode(PacketByteBuf buf) {
            return new KeybindPacket(buf.readEnumConstant(Keybind.class), buf.readBoolean());
        }

        @Override
        public PacketContext handle(KeybindPacket message) {
            return (player, world) -> ModKeyBindings.pressedKeyOnServer(player.getUuid(), message.keybind, message.pressed());
        }
    }

    public enum Keybind {
        JUMP, SPRINT, FORWARD, BACK, LEFT, RIGHT
    }
}
