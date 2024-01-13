package earth.terrarium.adastra.datagen.provider.client;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.FlagBlock;
import earth.terrarium.adastra.common.blocks.properties.EightDirectionProperty;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModHighlightBlockStateProvider implements DataProvider {

    private final PackOutput output;
    private final Map<Block, JsonObject> highlights = new HashMap<>();

    public ModHighlightBlockStateProvider(PackOutput output) {
        this.output = output;
    }

    public void createHighlights() {
        ModBlocks.FLAGS.stream().map(RegistryEntry::get).forEach(this::flag);
    }

    public void flag(Block block) {
        JsonObject highlightJson = new JsonObject();
        JsonObject variantsJson = new JsonObject();
        highlightJson.add("variants", variantsJson);

        block.getStateDefinition().getPossibleStates().forEach(state -> {
            EightDirectionProperty.Direction facing = state.getValue(FlagBlock.FACING);
            DoubleBlockHalf half = state.getValue(FlagBlock.HALF);

            int angle = facing.asRotation();
            boolean is45 = angle % 90 != 0;
            angle = (angle + 45) / 90 * 90;

            if (facing == EightDirectionProperty.Direction.NORTH
                || facing == EightDirectionProperty.Direction.NORTH_EAST
                || facing == EightDirectionProperty.Direction.SOUTH
                || facing == EightDirectionProperty.Direction.SOUTH_WEST) {
                angle -= 180;
            }

            JsonObject stateJson = new JsonObject();
            stateJson.addProperty("highlight", AdAstra.MOD_ID + (is45 ? ":flag_45_shape" : ":flag_shape"));
            JsonObject rotationJson = new JsonObject();
            rotationJson.addProperty("y", (angle + 180) % 360);
            stateJson.add("rotation", rotationJson);

            if (half == DoubleBlockHalf.UPPER) {
                JsonArray translationJson = new JsonArray();
                translationJson.add(0);
                translationJson.add(-1);
                translationJson.add(0);
                stateJson.add("translation", translationJson);
            }

            variantsJson.add("facing=%s,half=%s".formatted(facing.getSerializedName().toLowerCase(Locale.ROOT), half.name().toLowerCase(Locale.ROOT)), stateJson);
        });

        highlights.put(block, highlightJson);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        createHighlights();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[highlights.size()];
        int i = 0;
        for (var entry : highlights.entrySet()) {
            futures[i++] = saveHighlight(cache, entry.getValue(), entry.getKey());
        }
        return CompletableFuture.allOf(futures);
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private CompletableFuture<?> saveHighlight(CachedOutput cache, JsonObject stateJson, Block owner) {
        ResourceLocation blockName = Preconditions.checkNotNull(key(owner));
        Path outputPath = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
            .resolve(blockName.getNamespace()).resolve("resourcefullib/highlights").resolve(blockName.getPath() + ".json");
        return DataProvider.saveStable(cache, stateJson, outputPath);
    }

    @NotNull
    @Override
    public String getName() {
        return "Ad Astra Highlight States";
    }
}
