package earth.terrarium.ad_astra.networking.packets.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.recipes.SpaceStationRecipe;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public record CreateSpaceStationPacket(ResourceLocation targetWorld) implements Packet<CreateSpaceStationPacket> {

    public static final ResourceLocation ID = new ModResourceLocation("toggle_distributor");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<CreateSpaceStationPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<CreateSpaceStationPacket> {
        private static boolean hasItem(Player player, Ingredient ingredient, int count) {
            int found = 0;
            for (ItemStack stack : player.getInventory().items) {
                if (ingredient.test(stack)) {
                    found += stack.getCount();
                }
            }
            return found >= count;
        }

        @Override
        public void encode(CreateSpaceStationPacket packet, FriendlyByteBuf buf) {
            buf.writeResourceLocation(packet.targetWorld());
        }

        @Override
        public CreateSpaceStationPacket decode(FriendlyByteBuf buf) {
            return new CreateSpaceStationPacket(buf.readResourceLocation());
        }

        @Override
        public PacketContext handle(CreateSpaceStationPacket message) {
            return (player, level) -> {
                if (!player.isCreative() && !player.isSpectator()) {
                    for (SpaceStationRecipe recipe : ModRecipeTypes.SPACE_STATION_RECIPE.get().getRecipes(player.level)) {
                        for (int i = 0; i < recipe.getIngredients().size(); i++) {
                            if (!hasItem(player, recipe.getIngredients().get(i), recipe.getHolders().get(i).count())) {
                                return;
                            }
                        }
                    }
                    Inventory inventory = player.getInventory();
                    ModRecipeTypes.SPACE_STATION_RECIPE.get().getRecipes(player.level).forEach(recipe -> {
                        for (int i = 0; i < recipe.getIngredients().size(); i++) {
                            inventory.clearOrCountMatchingItems(recipe.getIngredients().get(i), recipe.getHolders().get(i).count(), inventory);
                        }
                    });
                }

                if (level instanceof ServerLevel serverWorld) {
                    ServerLevel targetWorld = serverWorld.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, message.targetWorld));

                    if (targetWorld == null) {
                        return;
                    }

                    // Create the Space Station from the nbt file
                    StructureTemplate structure = targetWorld.getStructureManager().getOrCreate(new ModResourceLocation("space_station"));
                    BlockPos pos = new BlockPos(player.getX() - (structure.getSize().getX() / 2.0f), 100, player.getZ() - (structure.getSize().getZ() / 2.0f));
                    targetWorld.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(pos), 1, pos);
                    structure.placeInWorld(targetWorld, pos, pos, new StructurePlaceSettings(), targetWorld.random, 2);
                }
            };
        }
    }
}
