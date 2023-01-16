package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        createSimpleShapeless(writer, ModItems.ETRIUM_NUGGET.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.ETRIUM_INGOT.get())
                .unlockedBy("has_item", has(ModItems.ETRIUM_INGOT.get()))
        );

        createSimpleShapeless(writer, ModItems.ETRIUM_INGOT.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.ETRIUM_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.ETRIUM_NUGGET.get()))
        );

        createSimpleShapeless(writer, "etrium_block_to_ingot", ModItems.ETRIUM_INGOT.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.ETRIUM_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.ETRIUM_BLOCK.get()))
        );

        createSimpleShapeless(writer, ModItems.ETRIUM_BLOCK.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.ETRIUM_INGOT.get(), 9)
                .unlockedBy("has_item", has(ModItems.ETRIUM_INGOT.get()))
        );

        createSimpleShapeless(writer, ModItems.STEEL_INGOT.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.STEEL_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.STEEL_NUGGET.get()))
        );

        createSimpleShapeless(writer, ModItems.STEEL_NUGGET.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModTags.Items.STEEL_INGOTS)
                .unlockedBy("has_item", has(ModTags.Items.STEEL_INGOTS))
        );

        createSimpleShapeless(writer, "steel_block_to_ingot", ModItems.STEEL_INGOT.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.STEEL_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.STEEL_BLOCK.get()))
        );

        createSimpleShapeless(writer, ModItems.STEEL_BLOCK.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.STEEL_INGOT.get(), 9)
                .unlockedBy("has_item", has(ModItems.STEEL_INGOT.get()))
        );

        createSimpleShapeless(writer, ModItems.AEROLYTE_INGOT.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.AEROLYTE_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.AEROLYTE_NUGGET.get()))
        );

        createSimpleShapeless(writer, ModItems.AEROLYTE_NUGGET.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.AEROLYTE_INGOT.get())
                .unlockedBy("has_item", has(ModItems.AEROLYTE_INGOT.get()))
        );

        createSimpleShapeless(writer, "aerolyte_block_to_ingot", ModItems.AEROLYTE_INGOT.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.AEROLYTE_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.AEROLYTE_BLOCK.get()))
        );

        createSimpleShapeless(writer, ModItems.AEROLYTE_BLOCK.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.AEROLYTE_INGOT.get(), 9)
                .unlockedBy("has_item", has(ModItems.AEROLYTE_INGOT.get()))
        );

        createSimpleShapeless(writer, ModItems.DESMIUM_INGOT.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.DESMIUM_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.DESMIUM_NUGGET.get()))
        );

        createSimpleShapeless(writer, ModItems.DESMIUM_NUGGET.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.DESMIUM_INGOT.get())
                .unlockedBy("has_item", has(ModItems.DESMIUM_INGOT.get()))
        );

        createSimpleShapeless(writer, "desmium_block_to_ingot", ModItems.DESMIUM_INGOT.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.DESMIUM_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.DESMIUM_BLOCK.get()))
        );

        createSimpleShapeless(writer, ModItems.DESMIUM_BLOCK.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.DESMIUM_INGOT.get(), 9)
                .unlockedBy("has_item", has(ModItems.DESMIUM_INGOT.get()))
        );

        createSimpleShapeless(writer, ModItems.THERMALYTE_INGOT.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.THERMALYTE_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.THERMALYTE_NUGGET.get()))
        );

        createSimpleShapeless(writer, ModItems.THERMALYTE_NUGGET.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.THERMALYTE_INGOT.get())
                .unlockedBy("has_item", has(ModItems.THERMALYTE_INGOT.get()))
        );

        createSimpleShapeless(writer, "thermalyte_block_to_ingot", ModItems.THERMALYTE_INGOT.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.THERMALYTE_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.THERMALYTE_BLOCK.get()))
        );

        createSimpleShapeless(writer, ModItems.THERMALYTE_BLOCK.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.THERMALYTE_INGOT.get(), 9)
                .unlockedBy("has_item", has(ModItems.THERMALYTE_INGOT.get()))
        );

        createSimpleShapeless(writer, ModItems.VESNIUM.get(), 9, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.VESNIUM_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.VESNIUM_BLOCK.get()))
        );

        createSimpleShapeless(writer, ModItems.VESNIUM_BLOCK.get(), 1, shapelessRecipeBuilder -> shapelessRecipeBuilder
                .requires(ModItems.VESNIUM.get(), 9)
                .unlockedBy("has_item", has(ModItems.VESNIUM.get()))
        );

        createSimple(writer, ModItems.ETRIONIC_CORE.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('I', ModTags.Items.STEEL_PLATES)
                .define('C', ModItems.ETRIUM_PLATE.get())
                .pattern("II")
                .pattern("CC")
                .pattern("II")
                .unlockedBy("has_item", has(ModItems.ETRIUM_NUGGET.get()))
        );

        createSimple(writer, ModItems.ETRIONIC_CAPACITOR.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('E', ModItems.ETRIUM_INGOT.get())
                .define('C', ModItems.ETRIONIC_CORE.get())
                .define('N', ModTags.Items.STEEL_NUGGETS)
                .define('I', ModTags.Items.STEEL_INGOTS)
                .pattern("NIN")
                .pattern("ECE")
                .pattern("NIN")
                .unlockedBy("has_item", has(ModItems.ETRIONIC_CORE.get()))
        );

        createSimple(writer, ModItems.PHOTOVOLTAIC_ETRIUM_CELL.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('I', ModItems.ETRIUM_PLATE.get())
                .define('P', ModTags.Items.STEEL_PLATES)
                .pattern("II")
                .pattern("PP")
                .unlockedBy("has_item", has(ModItems.ETRIUM_PLATE.get()))
        );

        createSimple(writer, ModItems.ETRIONIC_SOLAR_PANEL.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('I', ModItems.ETRIONIC_CORE.get())
                .define('P', ModTags.Items.STEEL_PLATES)
                .define('R', ModTags.Items.STEEL_RODS)
                .define('C', ModItems.PHOTOVOLTAIC_ETRIUM_CELL.get())
                .pattern("CCC")
                .pattern("PRP")
                .pattern("PIP")
                .unlockedBy("has_item", has(ModItems.ETRIONIC_CORE.get()))
        );

        createSimple(writer, ModItems.VESNIUM_SOLAR_PANEL.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('I', ModItems.ETRIONIC_CORE.get())
                .define('P', ModTags.Items.STEEL_PLATES)
                .define('R', ModTags.Items.STEEL_RODS)
                .define('C', ModItems.PHOTOVOLTAIC_VESNIUM_CELL.get())
                .pattern("CCC")
                .pattern("PRP")
                .pattern("PIP")
                .unlockedBy("has_item", has(ModItems.ETRIONIC_CORE.get()))
        );

        createSimple(writer, ModItems.OXYGEN_SENSOR.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('P', ModTags.Items.DESMIUM_PLATES)
                .define('R', Items.REDSTONE)
                .define('Q', Items.QUARTZ)
                .pattern("PPP")
                .pattern("QRQ")
                .pattern("PPP")
                .unlockedBy("has_item", has(ModItems.DESMIUM_INGOT.get()))
        );

        createSimple(writer, ModItems.ETRIONIC_GENERATOR.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('E', ModItems.ETRIUM_INGOT.get())
                .define('N', ModItems.ETRIUM_NUGGET.get())
                .define('I', Items.IRON_INGOT)
                .pattern("IIN")
                .pattern("IIN")
                .pattern("EEE")
                .unlockedBy("has_item", has(ModItems.ETRIUM_NUGGET.get()))
        );

        createSimple(writer, ModItems.ETRIONIC_BLAST_FURNACE.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('E', ModItems.ETRIUM_INGOT.get())
                .define('N', ModItems.ETRIUM_NUGGET.get())
                .define('B', Items.BLAST_FURNACE)
                .define('I', Items.IRON_INGOT)
                .pattern("III")
                .pattern("NBN")
                .pattern("EEE")
                .unlockedBy("has_item", has(ModItems.ETRIUM_NUGGET.get()))
        );

        createSimple(writer, ModItems.HYDRAULIC_PRESS.get(), 1, shapedRecipeBuilder -> shapedRecipeBuilder
                .define('E', ModItems.ETRIUM_INGOT.get())
                .define('N', ModItems.ETRIUM_NUGGET.get())
                .define('B', Items.IRON_BLOCK)
                .define('I', Items.IRON_INGOT)
                .pattern("III")
                .pattern("NBN")
                .pattern("EEE")
                .unlockedBy("has_item", has(ModItems.ETRIUM_NUGGET.get()))
        );
    }

    public static void createSimple(Consumer<FinishedRecipe> consumer, Item output, int count, Function<ShapedRecipeBuilder, ShapedRecipeBuilder> func) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output)).getPath();
        func.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count))
                .group(name)
                .save(consumer);
    }

    public static void createSimpleShapeless(Consumer<FinishedRecipe> consumer, Item output, int count, Function<ShapelessRecipeBuilder, ShapelessRecipeBuilder> func) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output)).getPath();
        createSimpleShapeless(consumer, name, output, count, func);
    }
    
    public static void createSimpleShapeless(Consumer<FinishedRecipe> consumer, String name, Item output, int count, Function<ShapelessRecipeBuilder, ShapelessRecipeBuilder> func) {
        String group = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output)).getPath();
        func.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count))
                .group(group)
                .save(consumer, name);
    }
}
