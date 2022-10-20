package earth.terrarium.ad_astra.networking.packets.client;

import earth.terrarium.ad_astra.recipes.SpaceStationRecipe;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.util.ModIdentifier;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public record CreateSpaceStationPacket(Identifier targetWorld) implements Packet<CreateSpaceStationPacket> {

    public static final Identifier ID = new ModIdentifier("toggle_distributor");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<CreateSpaceStationPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<CreateSpaceStationPacket> {
        @Override
        public void encode(CreateSpaceStationPacket keybindPacket, PacketByteBuf buf) {
            buf.writeIdentifier(keybindPacket.targetWorld());
        }

        @Override
        public CreateSpaceStationPacket decode(PacketByteBuf buf) {
            return new CreateSpaceStationPacket(buf.readIdentifier());
        }

        @Override
        public PacketContext handle(CreateSpaceStationPacket message) {
            return (player, world) -> {
                if (!player.isCreative() && !player.isSpectator()) {
                    for (SpaceStationRecipe recipe : ModRecipes.SPACE_STATION_RECIPE.get().getRecipes(player.world)) {
                        for (int i = 0; i < recipe.getIngredients().size(); i++) {
                            if (!hasItem(player, recipe.getIngredients().get(i), recipe.getStackCounts().get(i))) {
                                return;
                            }
                        }
                    }
                    PlayerInventory inventory = player.getInventory();
                    ModRecipes.SPACE_STATION_RECIPE.get().getRecipes(player.world).forEach(recipe -> {
                        for (int i = 0; i < recipe.getIngredients().size(); i++) {
                            inventory.remove(recipe.getIngredients().get(i), recipe.getStackCounts().get(i), inventory);
                        }
                    });
                }

                if (world instanceof ServerWorld serverWorld) {
                    ServerWorld targetWorld = serverWorld.getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, message.targetWorld));

                    if (targetWorld == null) {
                        return;
                    }

                    // Create the Space Station from the nbt file
                    Structure structure = targetWorld.getStructureTemplateManager().getStructureOrBlank(new ModIdentifier("space_station"));
                    BlockPos pos = new BlockPos(player.getX() - (structure.getSize().getX() / 2.0f), 100, player.getZ() - (structure.getSize().getZ() / 2.0f));
                    targetWorld.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(pos), 1, pos);
                    structure.place(targetWorld, pos, pos, new StructurePlacementData(), targetWorld.random, 2);
                }
            };
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
}
