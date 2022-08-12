package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class PlanetSelectionScreenHandler extends ScreenHandler {

    private final PlayerEntity player;
    private final int tier;

    public PlanetSelectionScreenHandler(int syncId, PlayerEntity player, PacketByteBuf buf) {
        this(syncId, player, buf.readInt());
    }

    public PlanetSelectionScreenHandler(int syncId, PlayerEntity player, int tier) {
        super(ModScreenHandlers.PLANET_SELECTION_SCREEN_HANDLER, syncId);
        this.tier = tier;
        this.player = player;
    }

    public int getTier() {
        return tier;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !player.isDead();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }
}