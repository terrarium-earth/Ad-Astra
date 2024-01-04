package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.compat.cadmus.CadmusIntegration;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.recipes.base.IngredientHolder;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public record ServerboundConstructSpaceStationPacket(ResourceLocation dimensionLocation,
                                                     ChunkPos targetPos) implements Packet<ServerboundConstructSpaceStationPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "construct_space_station");
    public static final Handler HANDLER = new Handler();

    public static final ResourceLocation SPACE_STATION_STRUCTURE = new ResourceLocation(AdAstra.MOD_ID, "space_station");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundConstructSpaceStationPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundConstructSpaceStationPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.RESOURCE_LOCATION.fieldOf(ServerboundConstructSpaceStationPacket::dimensionLocation),
                ExtraByteCodecs.CHUNK_POS.fieldOf(ServerboundConstructSpaceStationPacket::targetPos),
                ServerboundConstructSpaceStationPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundConstructSpaceStationPacket packet) {
            return (player, level) -> {
                if (!(level instanceof ServerLevel serverLevel)) return;
                if (!(player.containerMenu instanceof PlanetsMenu)) return;
                Planet planet = AdAstraData.getPlanet(packet.dimensionLocation);
                if (planet == null) return;
                var server = serverLevel.getServer();
                ServerLevel targetLevel = server.getLevel(planet.orbitIfPresent());
                if (targetLevel == null) return;
                if (SpaceStationHandler.isSpaceStationAt(targetLevel, packet.targetPos())) return;

                if (!player.isCreative() && !player.isSpectator()) {
                    var recipe = targetLevel.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get()).stream()
                        .filter(r -> targetLevel.dimension().location().equals(r.dimension())).findFirst().orElse(null);
                    if (recipe == null) return;
                    if (!recipe.matches(player.getInventory(), targetLevel)) return;

                    var inventory = player.getInventory();
                    for (IngredientHolder holder : recipe.ingredients()) {
                        int count = holder.count();
                        if (count <= 0) continue;
                        for (int i = 0; i < inventory.getContainerSize(); i++) {
                            var stack = inventory.getItem(i);
                            if (holder.ingredient().test(stack)) {
                                stack.shrink(Math.min(count, stack.getCount()));
                                count -= stack.getCount();
                            }
                        }
                    }
                }

                StructureTemplate structure = targetLevel.getStructureManager().getOrCreate(SPACE_STATION_STRUCTURE);
                BlockPos pos = BlockPos.containing((player.getX() - (structure.getSize().getX() / 2.0f)), 100, (player.getZ() - (structure.getSize().getZ() / 2.0f)));
                targetLevel.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(pos), 1, pos);
                structure.placeInWorld(targetLevel, pos, pos, new StructurePlaceSettings(), targetLevel.random, 2);

                SpaceStationHandler.addSpaceStation(targetLevel, packet.targetPos(), player.getUUID());

                // Cadmus claiming 3x3 chunks
                if (CadmusIntegration.cadmusLoaded()) {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            CadmusIntegration.claim((ServerPlayer) player, new ChunkPos(packet.targetPos().x + i, packet.targetPos().z + j));
                        }
                    }
                }
            };
        }
    }
}