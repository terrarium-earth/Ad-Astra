package earth.terrarium.adastra.common.menus.content;

import com.teamresourceful.resourcefullib.common.menu.MenuContent;
import com.teamresourceful.resourcefullib.common.menu.MenuContentSerializer;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import earth.terrarium.adastra.common.menus.base.PlanetsMenuProvider;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record PlanetsContent(Set<ResourceLocation> disabledPlanets,
                             Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> spaceStations,
                             Set<GlobalPos> spawnLocations) implements MenuContent<PlanetsContent> {
    public static final MenuContentSerializer<PlanetsContent> SERIALIZER = new Serializer();

    @Override
    public MenuContentSerializer<PlanetsContent> serializer() {
        return SERIALIZER;
    }

    private static class Serializer implements MenuContentSerializer<PlanetsContent> {

        @Override
        public @Nullable PlanetsContent from(FriendlyByteBuf buffer) {
            return new PlanetsContent(
                PlanetsMenuProvider.createDisabledPlanetsFromBuf(buffer),
                PlanetsMenuProvider.createSpaceStationsFromBuf(buffer),
                PlanetsMenuProvider.createSpawnLocationsFromBuf(buffer)
            );
        }

        @Override
        public void to(FriendlyByteBuf buffer, PlanetsContent content) {
            PlanetsMenuProvider.writeDisabledPlanetsToBuf(buffer, content.disabledPlanets());
            PlanetsMenuProvider.writeSpaceStationsToBuf(buffer, content.spaceStations());
            PlanetsMenuProvider.writeSpawnLocationsToBuf(buffer, content.spawnLocations());
        }
    }
}
