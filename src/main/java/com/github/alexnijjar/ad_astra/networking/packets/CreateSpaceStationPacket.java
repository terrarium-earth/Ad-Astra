package com.github.alexnijjar.ad_astra.networking.packets;

import com.github.alexnijjar.ad_astra.recipes.SpaceStationRecipe;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class CreateSpaceStationPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ServerWorld targetWorld = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier()));
        server.execute(() -> {
            if (!player.isCreative() && !player.isSpectator()) {
                for (SpaceStationRecipe recipe : ModRecipes.SPACE_STATION_RECIPE.getRecipes(player.world)) {
                    for (int i = 0; i < recipe.getIngredients().size(); i++) {
                        if (!hasItem(player, recipe.getIngredients().get(i), recipe.getStackCounts().get(i))) {
                            return;
                        }
                    }
                }
                PlayerInventory inventory = player.getInventory();
                ModRecipes.SPACE_STATION_RECIPE.getRecipes(player.world).forEach(recipe -> {
                    for (int i = 0; i < recipe.getIngredients().size(); i++) {
                        inventory.remove(recipe.getIngredients().get(i), recipe.getStackCounts().get(i), inventory);
                    }
                });
            }

            if (targetWorld == null) return;

            // Create the Space Station from the nbt file.
            Structure structure = targetWorld.getStructureManager().getStructureOrBlank(new ModIdentifier("space_station"));
            BlockPos pos = new BlockPos(player.getX() - (structure.getSize().getX() / 2f), 100, player.getZ() - (structure.getSize().getZ() / 2f));
            structure.place(targetWorld, pos, pos, new StructurePlacementData(), targetWorld.random, 2);
        });
    }

    private static boolean hasItem(PlayerEntity player, Ingredient ingredient, int count) {
        int found = 0;
        for (ItemStack stack : player.getInventory().main) {
            if (ingredient.test(stack)) {
                found += stack.getCount();
            }
        }
        return found >= count;
    }
}
