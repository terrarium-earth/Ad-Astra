package earth.terrarium.adastra.datagen.provider.server;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModRecipeProvider extends RecipeProvider {
    private Consumer<FinishedRecipe> output;

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        this.output = writer;
        ModMachineRecipeProvider.createRecipes(writer);

        shaped(ModItems.ROVER, 1, ModItems.DESH_ENGINE, r -> r
            .define('W', ModItems.WHEEL.get())
            .define('D', ModItemTags.DESH_BLOCKS)
            .define('R', ModItems.RADIO.get())
            .define('P', ModItemTags.DESH_PLATES)
            .define('G', ModItems.LARGE_GAS_TANK.get())
            .define('|', ModItemTags.STEEL_RODS)
            .pattern("D |")
            .pattern("GR#")
            .pattern("WPW"));

        shaped(ModItems.LAUNCH_PAD, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItemTags.STEEL_RODS)
            .pattern("#|#")
            .pattern("|#|")
            .pattern("#|#"));

        shaped(ModItems.STEEL_CABLE, 16, ModItemTags.STEEL_PLATES, r -> r
            .define('C', Items.COPPER_INGOT)
            .pattern("###")
            .pattern("CCC")
            .pattern("###"));

        shaped(ModItems.DESH_CABLE, 16, ModItemTags.DESH_PLATES, r -> r
            .define('C', Items.COPPER_INGOT)
            .pattern("###")
            .pattern("CCC")
            .pattern("###"));

        shaped(ModItems.DESH_FLUID_PIPE, 16, ModItemTags.DESH_PLATES, r -> r
            .define('G', Items.GLASS)
            .pattern("###")
            .pattern("GGG")
            .pattern("###"));

        shaped(ModItems.OSTRUM_FLUID_PIPE, 16, ModItemTags.OSTRUM_PLATES, r -> r
            .define('G', Items.GLASS)
            .pattern("###")
            .pattern("GGG")
            .pattern("###"));

        shaped(ModItems.CABLE_DUCT, 2, ModItemTags.STEEL_PLATES, r -> r
            .define('S', ModItems.STEEL_CABLE.get())
            .define('C', Items.COPPER_INGOT)
            .pattern("#C#")
            .pattern("CSC")
            .pattern("#C#"));

        shaped(ModItems.FLUID_PIPE_DUCT, 2, ModItemTags.DESH_PLATES, r -> r
            .define('S', ModItems.DESH_FLUID_PIPE.get())
            .define('C', Items.COPPER_INGOT)
            .pattern("#C#")
            .pattern("CSC")
            .pattern("#C#"));

        shaped(ModItems.COAL_GENERATOR, 1, () -> Items.IRON_INGOT, r -> r
            .define('F', Items.FURNACE)
            .define('C', Items.COAL_BLOCK)
            .pattern("###")
            .pattern("CFC")
            .pattern("###"));

        shaped(ModItems.COMPRESSOR, 1, () -> Items.IRON_INGOT, r -> r
            .define('P', Items.PISTON)
            .pattern("#P#")
            .pattern("# #")
            .pattern("#P#"));

        shaped(ModItems.ETRIONIC_BLAST_FURNACE, 1, ModItemTags.IRON_PLATES, r -> r
            .define('B', Items.BLAST_FURNACE)
            .define('R', Items.REDSTONE)
            .pattern("###")
            .pattern("RBR")
            .pattern("###"));

        shaped(ModItems.NASA_WORKBENCH, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('C', Items.CRAFTING_TABLE)
            .define('B', ModItemTags.STEEL_BLOCKS)
            .define('|', ModItemTags.IRON_RODS)
            .define('T', Items.REDSTONE_TORCH)
            .pattern("|#|")
            .pattern("TCT")
            .pattern("#B#"));

        shaped(ModItems.FUEL_REFINERY, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('B', Items.BUCKET)
            .define('F', Items.FURNACE)
            .pattern("###")
            .pattern("BFB")
            .pattern("###"));

        shaped(ModItems.OXYGEN_LOADER, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('G', ModItems.GAS_TANK.get())
            .define('F', ModItems.FAN.get())
            .define('R', Items.REDSTONE_BLOCK)
            .define('|', Items.LIGHTNING_ROD)
            .pattern("#F#")
            .pattern("G|G")
            .pattern("#R#"));

        shaped(ModItems.SOLAR_PANEL, 1, ModItemTags.DESH_PLATES, r -> r
            .define('P', ModItems.PHOTOVOLTAIC_ETRIUM_CELL.get())
            .define('S', ModItemTags.STEEL_PLATES)
            .pattern("PPP")
            .pattern("S#S")
            .pattern("###"));

        shaped(ModItems.WATER_PUMP, 1, ModItemTags.DESH_PLATES, r -> r
            .define('H', Items.HOPPER)
            .define('S', ModItemTags.STEEL_PLATES)
            .define('G', ModItems.GAS_TANK.get())
            .pattern("S#S")
            .pattern("SGS")
            .pattern("#H#"));

        shaped(ModItems.OXYGEN_DISTRIBUTOR, 1, ModItemTags.DESH_PLATES, r -> r
            .define('L', ModItems.OXYGEN_LOADER.get())
            .define('G', ModItems.LARGE_GAS_TANK.get())
            .define('O', ModItems.OXYGEN_GEAR.get())
            .define('F', ModItems.FAN.get())
            .pattern("FGF")
            .pattern("FLF")
            .pattern("#O#"));

        shaped(ModItems.GRAVITY_NORMALIZER, 1, ModItemTags.DESH_PLATES, r -> r
            .define('D', Items.DIAMOND_BLOCK)
            .define('E', ModItems.ETRIONIC_CAPACITOR.get())
            .pattern("EDE")
            .pattern("###"));

        shaped(ModItems.ENERGIZER, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('D', Items.DIAMOND_BLOCK)
            .define('B', ModItemTags.OSTRUM_BLOCKS)
            .define('E', ModItems.ETRIONIC_CAPACITOR.get())
            .pattern("#E#")
            .pattern("BDB")
            .pattern("#E#"));

        shaped(ModItems.CRYO_FREEZER, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('I', Items.BLUE_ICE)
            .define('G', ModItems.LARGE_GAS_TANK.get())
            .pattern("###")
            .pattern("IGI")
            .pattern("###"));

        shaped(ModItems.OXYGEN_SENSOR, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('R', Items.REDSTONE_BLOCK)
            .define('F', ModItems.FAN.get())
            .pattern("#F#")
            .pattern("#R#")
            .pattern("###"));

        coloredSet(ModItems.FLAGS, 1, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("|##")
            .pattern("|##")
            .pattern("|  "));

        shaped(ModItems.RADIO, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItemTags.STEEL_RODS)
            .pattern("|  ")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.SPACE_HELMET, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('P', Items.GLASS_PANE)
            .pattern("###")
            .pattern("#P#"));

        shaped(ModItems.SPACE_SUIT, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('O', ModItems.OXYGEN_GEAR.get())
            .define('T', ModItems.GAS_TANK.get())
            .define('W', ItemTags.WOOL)
            .pattern("# #")
            .pattern("TOT")
            .pattern("#W#"));

        shaped(ModItems.SPACE_PANTS, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('W', ItemTags.WOOL)
            .pattern("###")
            .pattern("W W")
            .pattern("# #"));

        shaped(ModItems.SPACE_BOOTS, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('W', ItemTags.WOOL)
            .pattern("W W")
            .pattern("# #"));

        shaped(ModItems.NETHERITE_SPACE_HELMET, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('G', Items.GLASS)
            .define('N', Items.NETHERITE_HELMET)
            .pattern("#N#")
            .pattern("#G#"));

        shaped(ModItems.NETHERITE_SPACE_SUIT, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('O', ModItems.OXYGEN_GEAR.get())
            .define('T', ModItems.LARGE_GAS_TANK.get())
            .define('N', Items.NETHERITE_CHESTPLATE)
            .pattern("# #")
            .pattern("TOT")
            .pattern("#N#"));

        shaped(ModItems.NETHERITE_SPACE_PANTS, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('D', ModItemTags.DESH_PLATES)
            .define('N', Items.NETHERITE_LEGGINGS)
            .pattern("#N#")
            .pattern("D D")
            .pattern("# #"));

        shaped(ModItems.NETHERITE_SPACE_BOOTS, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('D', ModItemTags.DESH_PLATES)
            .define('N', Items.NETHERITE_BOOTS)
            .pattern(" N ")
            .pattern("D D")
            .pattern("# #"));

        shaped(ModItems.JET_SUIT_HELMET, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('N', ModItems.NETHERITE_SPACE_HELMET.get())
            .define('G', Items.TINTED_GLASS)
            .pattern("#N#")
            .pattern("#G#"));

        shaped(ModItems.JET_SUIT, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('N', ModItems.NETHERITE_SPACE_SUIT.get())
            .define('E', ModItems.CALORITE_ENGINE.get())
            .define('T', ModItems.CALORITE_TANK.get())
            .define('C', ModItems.ETRIONIC_CAPACITOR.get())
            .pattern("# #")
            .pattern("TNT")
            .pattern("CEC"));

        shaped(ModItems.JET_SUIT_PANTS, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('N', ModItems.NETHERITE_SPACE_PANTS.get())
            .define('B', ModItems.CALORITE_BLOCK.get())
            .pattern("BNB")
            .pattern("# #")
            .pattern("# #"));

        shaped(ModItems.JET_SUIT_BOOTS, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('N', ModItems.NETHERITE_SPACE_BOOTS.get())
            .define('B', ModItems.CALORITE_BLOCK.get())
            .pattern(" N ")
            .pattern("# #")
            .pattern("B B"));

        shaped(ModItems.TI_69, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('G', Items.GLASS_PANE)
            .define('R', Items.REDSTONE)
            .pattern("GGG")
            .pattern("#R#")
            .pattern("###"));

        shaped(ModItems.WRENCH, 1, ModItemTags.IRON_PLATES, r -> r
            .define('|', ModItemTags.IRON_RODS)
            .pattern("# #")
            .pattern(" | ")
            .pattern(" | "));

        shaped(ModItems.ZIP_GUN, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('G', ModItems.LARGE_GAS_TANK.get())
            .define('|', ModItemTags.STEEL_RODS)
            .pattern("##|")
            .pattern("G  "));

        shaped(ModItems.ETRIONIC_CAPACITOR, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('D', Items.DIAMOND)
            .define('R', Items.REDSTONE)
            .pattern("R#R")
            .pattern("#D#")
            .pattern("D#D"));

        shaped(ModItems.GAS_TANK, 1, ModItemTags.IRON_PLATES, r -> r
            .define('|', ModItems.IRON_ROD.get())
            .pattern("| ")
            .pattern("##")
            .pattern("##"));

        shaped(ModItems.LARGE_GAS_TANK, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .define('G', ModItems.GAS_TANK.get())
            .pattern(" | ")
            .pattern("#G#")
            .pattern("#G#"));

        shaped(ModItems.CHEESE_BLOCK, 1, ModItems.CHEESE, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.CHEESE, 9, ModItems.CHEESE_BLOCK, r -> r);

        shaped(ModItems.IRON_ROD, 4, ModItemTags.IRON_PLATES, r -> r
            .pattern("#")
            .pattern("#"));

        shaped(ModItems.STEEL_INGOT, 1, ModItemTags.STEEL_NUGGETS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.STEEL_NUGGET, 9, ModItems.STEEL_INGOT, r -> r);

        shaped(ModItems.STEEL_ROD, 4, ModItemTags.STEEL_PLATES, r -> r
            .pattern("#")
            .pattern("#"));

        shaped(ModItems.STEEL_BLOCK, 1, ModItemTags.STEEL_INGOTS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.STEEL_INGOT, 9, ModItems.STEEL_BLOCK, "steel_ingot_from_steel_block", r -> r);

        shaped(ModItems.DESH_INGOT, 1, ModItemTags.DESH_NUGGETS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.DESH_NUGGET, 9, ModItems.DESH_INGOT, r -> r);

        shaped(ModItems.DESH_BLOCK, 1, ModItemTags.DESH_INGOTS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.DESH_INGOT, 9, ModItems.DESH_BLOCK, "desh_ingot_from_desh_block", r -> r);

        shaped(ModItems.OSTRUM_INGOT, 1, ModItemTags.OSTRUM_NUGGETS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.OSTRUM_NUGGET, 9, ModItems.OSTRUM_INGOT, r -> r);

        shaped(ModItems.OSTRUM_BLOCK, 1, ModItemTags.OSTRUM_INGOTS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.OSTRUM_INGOT, 9, ModItems.OSTRUM_BLOCK, "ostrum_ingot_from_ostrum_block", r -> r);

        shaped(ModItems.CALORITE_INGOT, 1, ModItemTags.CALORITE_NUGGETS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.CALORITE_NUGGET, 9, ModItems.CALORITE_INGOT, r -> r);

        shaped(ModItems.CALORITE_BLOCK, 1, ModItemTags.CALORITE_INGOTS, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shapeless(ModItems.CALORITE_INGOT, 9, ModItems.CALORITE_BLOCK, "calorite_ingot_from_calorite_block", r -> r);

        shapeless(ModItems.RAW_DESH, 9, ModItems.RAW_DESH_BLOCK, r -> r);
        shapeless(ModItems.RAW_OSTRUM, 9, ModItems.RAW_OSTRUM_BLOCK, r -> r);
        shapeless(ModItems.RAW_CALORITE, 9, ModItems.RAW_CALORITE_BLOCK, r -> r);

        shaped(ModItems.RAW_DESH_BLOCK, 1, ModItemTags.RAW_DESH, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.RAW_OSTRUM_BLOCK, 1, ModItemTags.RAW_OSTRUM, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.RAW_CALORITE_BLOCK, 1, ModItemTags.RAW_CALORITE, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.PHOTOVOLTAIC_ETRIUM_CELL, 1, ModItemTags.DESH_PLATES, r -> r
            .define('D', Items.DIAMOND)
            .define('G', Items.BLUE_STAINED_GLASS)
            .pattern("GGG")
            .pattern("#D#"));

        shaped(ModItems.OXYGEN_GEAR, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern(" | ")
            .pattern("#|#")
            .pattern("#|#"));

        shaped(ModItems.WHEEL, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('B', Items.BLACK_WOOL)
            .pattern(" B ")
            .pattern("B#B")
            .pattern(" B "));

        shaped(ModItems.ENGINE_FRAME, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("|||")
            .pattern("|#|")
            .pattern("|||"));

        shaped(ModItems.FAN, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("# #")
            .pattern(" | ")
            .pattern("# #"));

        shaped(ModItems.ROCKET_NOSE_CONE, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('|', Items.LIGHTNING_ROD)
            .pattern(" | ")
            .pattern(" # ")
            .pattern("###"));

        shaped(ModItems.STEEL_ENGINE, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('F', ModItems.FAN.get())
            .define('E', ModItems.ENGINE_FRAME.get())
            .pattern("###")
            .pattern("#E#")
            .pattern(" F "));

        shaped(ModItems.DESH_ENGINE, 1, ModItemTags.DESH_PLATES, r -> r
            .define('F', ModItems.FAN.get())
            .define('E', ModItems.STEEL_ENGINE.get())
            .pattern("###")
            .pattern("#E#")
            .pattern(" F "));

        shaped(ModItems.OSTRUM_ENGINE, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('F', ModItems.FAN.get())
            .define('E', ModItems.DESH_ENGINE.get())
            .pattern("###")
            .pattern("#E#")
            .pattern(" F "));

        shaped(ModItems.CALORITE_ENGINE, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('F', ModItems.FAN.get())
            .define('E', ModItems.OSTRUM_ENGINE.get())
            .pattern("###")
            .pattern("#E#")
            .pattern(" F "));

        shaped(ModItems.STEEL_TANK, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('T', ModItems.GAS_TANK.get())
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("## ")
            .pattern("#T|")
            .pattern("## "));

        shaped(ModItems.DESH_TANK, 1, ModItemTags.DESH_PLATES, r -> r
            .define('T', ModItems.STEEL_TANK.get())
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("## ")
            .pattern("#T|")
            .pattern("## "));

        shaped(ModItems.OSTRUM_TANK, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('T', ModItems.DESH_TANK.get())
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("## ")
            .pattern("#T|")
            .pattern("## "));

        shaped(ModItems.CALORITE_TANK, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('T', ModItems.OSTRUM_TANK.get())
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("## ")
            .pattern("#T|")
            .pattern("## "));

        shaped(ModItems.ROCKET_FIN, 4, ModItemTags.STEEL_PLATES, r -> r
            .pattern(" # ")
            .pattern("###")
            .pattern("# #"));

        shaped(ModItems.VENT, 4, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("|||")
            .pattern("# #")
            .pattern("|||"));

        shaped(ModItems.IRON_FACTORY_BLOCK, 64, ModItemTags.IRON_PLATES, r -> r
            .define('I', Items.IRON_INGOT)
            .pattern("###")
            .pattern("#I#")
            .pattern("###"));

        shaped(ModItems.STEEL_FACTORY_BLOCK, 64, ModItemTags.STEEL_PLATES, r -> r
            .define('S', ModItemTags.STEEL_INGOTS)
            .pattern("###")
            .pattern("#S#")
            .pattern("###"));

        shaped(ModItems.DESH_FACTORY_BLOCK, 64, ModItemTags.DESH_PLATES, r -> r
            .define('D', ModItemTags.DESH_INGOTS)
            .pattern("###")
            .pattern("#D#")
            .pattern("###"));

        shaped(ModItems.OSTRUM_FACTORY_BLOCK, 64, ModItemTags.OSTRUM_PLATES, r -> r
            .define('O', ModItemTags.OSTRUM_INGOTS)
            .pattern("###")
            .pattern("#O#")
            .pattern("###"));

        shaped(ModItems.CALORITE_FACTORY_BLOCK, 64, ModItemTags.CALORITE_PLATES, r -> r
            .define('C', ModItemTags.CALORITE_INGOTS)
            .pattern("###")
            .pattern("#C#")
            .pattern("###"));

        shaped(ModItems.ENCASED_IRON_BLOCK, 64, ModItemTags.IRON_PLATES, r -> r
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("III")
            .pattern("###")
            .pattern("III"));

        shaped(ModItems.ENCASED_STEEL_BLOCK, 64, ModItemTags.STEEL_PLATES, r -> r
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("III")
            .pattern("###")
            .pattern("III"));

        shaped(ModItems.ENCASED_DESH_BLOCK, 64, ModItemTags.DESH_PLATES, r -> r
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("III")
            .pattern("###")
            .pattern("III"));

        shaped(ModItems.ENCASED_OSTRUM_BLOCK, 64, ModItemTags.OSTRUM_PLATES, r -> r
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("III")
            .pattern("###")
            .pattern("III"));

        shaped(ModItems.ENCASED_CALORITE_BLOCK, 64, ModItemTags.CALORITE_PLATES, r -> r
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("III")
            .pattern("###")
            .pattern("III"));

        shaped(ModItems.IRON_PLATEBLOCK, 64, ModItemTags.IRON_PLATES, r -> r
            .define('|', ModItems.IRON_ROD.get())
            .pattern("###")
            .pattern("#|#")
            .pattern("###"));

        shaped(ModItems.STEEL_PLATEBLOCK, 64, ModItemTags.STEEL_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("###")
            .pattern("#|#")
            .pattern("###"));

        shaped(ModItems.DESH_PLATEBLOCK, 64, ModItemTags.DESH_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("###")
            .pattern("#|#")
            .pattern("###"));

        shaped(ModItems.OSTRUM_PLATEBLOCK, 64, ModItemTags.OSTRUM_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("###")
            .pattern("#|#")
            .pattern("###"));

        shaped(ModItems.CALORITE_PLATEBLOCK, 64, ModItemTags.CALORITE_PLATES, r -> r
            .define('|', ModItems.STEEL_ROD.get())
            .pattern("###")
            .pattern("#|#")
            .pattern("###"));

        shaped(ModItems.IRON_PANEL, 64, ModItemTags.IRON_PLATES, r -> r
            .define('I', Items.IRON_INGOT)
            .pattern("#I#")
            .pattern("III")
            .pattern("#I#"));

        shaped(ModItems.STEEL_PANEL, 64, ModItemTags.STEEL_PLATES, r -> r
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("#I#")
            .pattern("III")
            .pattern("#I#"));

        shaped(ModItems.DESH_PANEL, 64, ModItemTags.DESH_PLATES, r -> r
            .define('I', ModItemTags.DESH_INGOTS)
            .pattern("#I#")
            .pattern("III")
            .pattern("#I#"));

        shaped(ModItems.OSTRUM_PANEL, 64, ModItemTags.OSTRUM_PLATES, r -> r
            .define('I', ModItemTags.OSTRUM_INGOTS)
            .pattern("#I#")
            .pattern("III")
            .pattern("#I#"));

        shaped(ModItems.CALORITE_PANEL, 64, ModItemTags.CALORITE_PLATES, r -> r
            .define('I', ModItemTags.CALORITE_INGOTS)
            .pattern("#I#")
            .pattern("III")
            .pattern("#I#"));

        shaped(ModItems.IRON_PLATING, 64, ModItemTags.IRON_PLATES, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.STEEL_PLATING, 64, ModItemTags.STEEL_PLATES, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.DESH_PLATING, 64, ModItemTags.DESH_PLATES, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.OSTRUM_PLATING, 64, ModItemTags.OSTRUM_PLATES, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.CALORITE_PLATING, 64, ModItemTags.CALORITE_PLATES, r -> r
            .pattern("###")
            .pattern("###")
            .pattern("###"));

        shaped(ModItems.IRON_PLATING_STAIRS, 4, ModItems.IRON_PLATING, r -> r
            .pattern("#  ")
            .pattern("## ")
            .pattern("###"));

        shaped(ModItems.STEEL_PLATING_STAIRS, 4, ModItems.STEEL_PLATING, r -> r
            .pattern("#  ")
            .pattern("## ")
            .pattern("###"));

        shaped(ModItems.DESH_PLATING_STAIRS, 4, ModItems.DESH_PLATING, r -> r
            .pattern("#  ")
            .pattern("## ")
            .pattern("###"));

        shaped(ModItems.OSTRUM_PLATING_STAIRS, 4, ModItems.OSTRUM_PLATING, r -> r
            .pattern("#  ")
            .pattern("## ")
            .pattern("###"));

        shaped(ModItems.CALORITE_PLATING_STAIRS, 4, ModItems.CALORITE_PLATING, r -> r
            .pattern("#  ")
            .pattern("## ")
            .pattern("###"));

        shaped(ModItems.IRON_PLATING_SLAB, 6, ModItems.IRON_PLATING, r -> r.pattern("###"));
        shaped(ModItems.STEEL_PLATING_SLAB, 6, ModItems.STEEL_PLATING, r -> r.pattern("###"));
        shaped(ModItems.DESH_PLATING_SLAB, 6, ModItems.DESH_PLATING, r -> r.pattern("###"));
        shaped(ModItems.OSTRUM_PLATING_SLAB, 6, ModItems.OSTRUM_PLATING, r -> r.pattern("###"));
        shaped(ModItems.CALORITE_PLATING_SLAB, 6, ModItems.CALORITE_PLATING, r -> r.pattern("###"));

        shaped(ModItems.IRON_PILLAR, 2, ModItems.IRON_PLATING, r -> r
            .pattern("#")
            .pattern("#"));

        shaped(ModItems.STEEL_PILLAR, 2, ModItems.STEEL_PLATING, r -> r
            .pattern("#")
            .pattern("#"));

        shaped(ModItems.DESH_PILLAR, 2, ModItems.DESH_PLATING, r -> r
            .pattern("#")
            .pattern("#"));

        shaped(ModItems.OSTRUM_PILLAR, 2, ModItems.OSTRUM_PLATING, r -> r
            .pattern("#")
            .pattern("#"));

        shaped(ModItems.CALORITE_PILLAR, 2, ModItems.CALORITE_PLATING, r -> r
            .pattern("#")
            .pattern("#"));

        shapeless(ModItems.GLOWING_IRON_PILLAR, 1, ModItems.IRON_PILLAR, r -> r
            .requires(Items.GLOWSTONE_DUST));

        shapeless(ModItems.GLOWING_STEEL_PILLAR, 1, ModItems.STEEL_PILLAR, r -> r
            .requires(Items.GLOWSTONE_DUST));

        shapeless(ModItems.GLOWING_DESH_PILLAR, 1, ModItems.DESH_PILLAR, r -> r
            .requires(Items.GLOWSTONE_DUST));

        shapeless(ModItems.GLOWING_OSTRUM_PILLAR, 1, ModItems.OSTRUM_PILLAR, r -> r
            .requires(Items.GLOWSTONE_DUST));

        shapeless(ModItems.GLOWING_CALORITE_PILLAR, 1, ModItems.CALORITE_PILLAR, r -> r
            .requires(Items.GLOWSTONE_DUST));

        shapeless(ModItems.MARKED_IRON_PILLAR, 1, ModItems.IRON_PILLAR, r -> r
            .requires(Items.YELLOW_DYE)
            .requires(Items.BLACK_DYE));

        shapeless(ModItems.IRON_PLATING_BUTTON, 1, ModItems.IRON_PLATING, r -> r);
        shapeless(ModItems.STEEL_PLATING_BUTTON, 1, ModItems.STEEL_PLATING, r -> r);
        shapeless(ModItems.DESH_PLATING_BUTTON, 1, ModItems.DESH_PLATING, r -> r);
        shapeless(ModItems.OSTRUM_PLATING_BUTTON, 1, ModItems.OSTRUM_PLATING, r -> r);
        shapeless(ModItems.CALORITE_PLATING_BUTTON, 1, ModItems.CALORITE_PLATING, r -> r);

        shaped(ModItems.IRON_PLATING_PRESSURE_PLATE, 1, ModItems.IRON_PLATING, r -> r.pattern("##"));
        shaped(ModItems.STEEL_PLATING_PRESSURE_PLATE, 1, ModItems.STEEL_PLATING, r -> r.pattern("##"));
        shaped(ModItems.DESH_PLATING_PRESSURE_PLATE, 1, ModItems.DESH_PLATING, r -> r.pattern("##"));
        shaped(ModItems.OSTRUM_PLATING_PRESSURE_PLATE, 1, ModItems.OSTRUM_PLATING, r -> r.pattern("##"));
        shaped(ModItems.CALORITE_PLATING_PRESSURE_PLATE, 1, ModItems.CALORITE_PLATING, r -> r.pattern("##"));

        shaped(ModItems.IRON_SLIDING_DOOR, 1, ModItems.IRON_PLATING, r -> r
            .define('P', Items.GLASS_PANE)
            .define('B', ModItemTags.STEEL_BLOCKS)
            .pattern("###")
            .pattern("PBP")
            .pattern("###"));

        shaped(ModItems.STEEL_SLIDING_DOOR, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('P', Items.GLASS_PANE)
            .define('B', ModItemTags.STEEL_BLOCKS)
            .pattern("###")
            .pattern("PBP")
            .pattern("###"));

        shaped(ModItems.DESH_SLIDING_DOOR, 1, ModItemTags.DESH_PLATES, r -> r
            .define('P', Items.GLASS_PANE)
            .define('B', ModItemTags.DESH_BLOCKS)
            .pattern("###")
            .pattern("PBP")
            .pattern("###"));

        shaped(ModItems.OSTRUM_SLIDING_DOOR, 1, ModItemTags.OSTRUM_PLATES, r -> r
            .define('P', Items.GLASS_PANE)
            .define('B', ModItemTags.OSTRUM_BLOCKS)
            .pattern("###")
            .pattern("PBP")
            .pattern("###"));

        shaped(ModItems.CALORITE_SLIDING_DOOR, 1, ModItemTags.CALORITE_PLATES, r -> r
            .define('P', Items.GLASS_PANE)
            .define('B', ModItemTags.CALORITE_BLOCKS)
            .pattern("###")
            .pattern("PBP")
            .pattern("###"));

        shaped(ModItems.AIRLOCK, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('B', ModItemTags.STEEL_BLOCKS)
            .pattern("##B")
            .pattern("###")
            .pattern("B##"));

        shaped(ModItems.REINFORCED_DOOR, 1, ModItemTags.STEEL_PLATES, r -> r
            .define('B', ModItemTags.STEEL_BLOCKS)
            .define('G', Items.GLASS)
            .define('O', Items.OBSIDIAN)
            .pattern("O#O")
            .pattern("GBG")
            .pattern("O#O"));

        shaped(ModItems.STEEL_DOOR, 3, ModItemTags.STEEL_PLATES, r -> r
            .pattern("##")
            .pattern("##")
            .pattern("##"));

        shaped(ModItems.STEEL_TRAPDOOR, 2, ModItemTags.STEEL_PLATES, r -> r
            .pattern("###")
            .pattern("###"));

        dyeSet(ModItems.INDUSTRIAL_LAMPS, 4, 0, r -> r
            .define('G', Items.GLOWSTONE_DUST)
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("GGG")
            .pattern("I#I"));

        dyeSet(ModItems.SMALL_INDUSTRIAL_LAMPS, 8, 1, r -> r
            .define('G', Items.GLOWSTONE_DUST)
            .define('I', ModItemTags.STEEL_INGOTS)
            .pattern("GG")
            .pattern("#I"));

        smelt(ModItems.MOON_STONE, ModItems.MOON_COBBLESTONE);
        smelt(ModItems.MARS_STONE, ModItems.MARS_COBBLESTONE);
        smelt(ModItems.VENUS_STONE, ModItems.VENUS_COBBLESTONE);
        smelt(ModItems.MERCURY_STONE, ModItems.MERCURY_COBBLESTONE);
        smelt(ModItems.GLACIO_STONE, ModItems.GLACIO_COBBLESTONE);

        shaped(ModItems.MOON_STONE_STAIRS, 4, ModItems.MOON_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.MARS_STONE_STAIRS, 4, ModItems.MARS_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.VENUS_STONE_STAIRS, 4, ModItems.VENUS_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.MERCURY_STONE_STAIRS, 4, ModItems.MERCURY_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.GLACIO_STONE_STAIRS, 4, ModItems.GLACIO_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));

        shaped(ModItems.MOON_STONE_SLAB, 6, ModItems.MOON_STONE, r -> r.pattern("###"));
        shaped(ModItems.MARS_STONE_SLAB, 6, ModItems.MARS_STONE, r -> r.pattern("###"));
        shaped(ModItems.VENUS_STONE_SLAB, 6, ModItems.VENUS_STONE, r -> r.pattern("###"));
        shaped(ModItems.MERCURY_STONE_SLAB, 6, ModItems.MERCURY_STONE, r -> r.pattern("###"));
        shaped(ModItems.GLACIO_STONE_SLAB, 6, ModItems.GLACIO_STONE, r -> r.pattern("###"));

        shaped(ModItems.MOON_COBBLESTONE_STAIRS, 4, ModItems.MOON_COBBLESTONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.MARS_COBBLESTONE_STAIRS, 4, ModItems.MARS_COBBLESTONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.VENUS_COBBLESTONE_STAIRS, 4, ModItems.VENUS_COBBLESTONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.MERCURY_COBBLESTONE_STAIRS, 4, ModItems.MERCURY_COBBLESTONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.GLACIO_COBBLESTONE_STAIRS, 4, ModItems.GLACIO_COBBLESTONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));

        shaped(ModItems.MOON_COBBLESTONE_SLAB, 6, ModItems.MOON_COBBLESTONE, r -> r.pattern("###"));
        shaped(ModItems.MARS_COBBLESTONE_SLAB, 6, ModItems.MARS_COBBLESTONE, r -> r.pattern("###"));
        shaped(ModItems.VENUS_COBBLESTONE_SLAB, 6, ModItems.VENUS_COBBLESTONE, r -> r.pattern("###"));
        shaped(ModItems.MERCURY_COBBLESTONE_SLAB, 6, ModItems.MERCURY_COBBLESTONE, r -> r.pattern("###"));
        shaped(ModItems.GLACIO_COBBLESTONE_SLAB, 6, ModItems.GLACIO_COBBLESTONE, r -> r.pattern("###"));

        shaped(ModItems.MOON_STONE_BRICKS, 4, ModItems.MOON_STONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.MARS_STONE_BRICKS, 4, ModItems.MARS_STONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.VENUS_STONE_BRICKS, 4, ModItems.VENUS_STONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.MERCURY_STONE_BRICKS, 4, ModItems.MERCURY_STONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.GLACIO_STONE_BRICKS, 4, ModItems.GLACIO_STONE, r -> r.pattern("##").pattern("##"));

        shaped(ModItems.MOON_STONE_BRICK_STAIRS, 4, ModItems.MOON_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.MARS_STONE_BRICK_STAIRS, 4, ModItems.MARS_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.VENUS_STONE_BRICK_STAIRS, 4, ModItems.VENUS_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.MERCURY_STONE_BRICK_STAIRS, 4, ModItems.MERCURY_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.GLACIO_STONE_BRICK_STAIRS, 4, ModItems.GLACIO_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));

        shaped(ModItems.MOON_STONE_BRICK_SLAB, 6, ModItems.MOON_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.MARS_STONE_BRICK_SLAB, 6, ModItems.MARS_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.VENUS_STONE_BRICK_SLAB, 6, ModItems.VENUS_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.MERCURY_STONE_BRICK_SLAB, 6, ModItems.MERCURY_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.GLACIO_STONE_BRICK_SLAB, 6, ModItems.GLACIO_STONE_BRICKS, r -> r.pattern("###"));

        smelt(ModItems.CRACKED_MOON_STONE_BRICKS, ModItems.MOON_STONE_BRICKS);
        smelt(ModItems.CRACKED_MARS_STONE_BRICKS, ModItems.MARS_STONE_BRICKS);
        smelt(ModItems.CRACKED_VENUS_STONE_BRICKS, ModItems.VENUS_STONE_BRICKS);
        smelt(ModItems.CRACKED_MERCURY_STONE_BRICKS, ModItems.MERCURY_STONE_BRICKS);
        smelt(ModItems.CRACKED_GLACIO_STONE_BRICKS, ModItems.GLACIO_STONE_BRICKS);

        shaped(ModItems.CHISELED_MOON_STONE_BRICKS, 1, ModItems.MOON_STONE_BRICK_SLAB, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.CHISELED_MARS_STONE_BRICKS, 1, ModItems.MARS_STONE_BRICK_SLAB, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.CHISELED_VENUS_STONE_BRICKS, 1, ModItems.VENUS_STONE_BRICK_SLAB, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.CHISELED_MERCURY_STONE_BRICKS, 1, ModItems.MERCURY_STONE_BRICK_SLAB, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.CHISELED_GLACIO_STONE_BRICKS, 1, ModItems.GLACIO_STONE_BRICK_SLAB, r -> r.pattern("#").pattern("#"));

        shaped(ModItems.CHISELED_MOON_STONE_STAIRS, 4, ModItems.CHISELED_MOON_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.CHISELED_MARS_STONE_STAIRS, 4, ModItems.CHISELED_MARS_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.CHISELED_VENUS_STONE_STAIRS, 4, ModItems.CHISELED_VENUS_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.CHISELED_MERCURY_STONE_STAIRS, 4, ModItems.CHISELED_MERCURY_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.CHISELED_GLACIO_STONE_STAIRS, 4, ModItems.CHISELED_GLACIO_STONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));

        shaped(ModItems.CHISELED_MOON_STONE_SLAB, 6, ModItems.CHISELED_MOON_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.CHISELED_MARS_STONE_SLAB, 6, ModItems.CHISELED_MARS_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.CHISELED_VENUS_STONE_SLAB, 6, ModItems.CHISELED_VENUS_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.CHISELED_MERCURY_STONE_SLAB, 6, ModItems.CHISELED_MERCURY_STONE_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.CHISELED_GLACIO_STONE_SLAB, 6, ModItems.CHISELED_GLACIO_STONE_BRICKS, r -> r.pattern("###"));

        shaped(ModItems.POLISHED_MOON_STONE, 4, ModItems.MOON_COBBLESTONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.POLISHED_MARS_STONE, 4, ModItems.MARS_COBBLESTONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.POLISHED_VENUS_STONE, 4, ModItems.VENUS_COBBLESTONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.POLISHED_MERCURY_STONE, 4, ModItems.MERCURY_COBBLESTONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.POLISHED_GLACIO_STONE, 4, ModItems.GLACIO_COBBLESTONE, r -> r.pattern("##").pattern("##"));

        shaped(ModItems.POLISHED_MOON_STONE_STAIRS, 4, ModItems.POLISHED_MOON_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.POLISHED_MARS_STONE_STAIRS, 4, ModItems.POLISHED_MARS_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.POLISHED_VENUS_STONE_STAIRS, 4, ModItems.POLISHED_VENUS_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.POLISHED_MERCURY_STONE_STAIRS, 4, ModItems.POLISHED_MERCURY_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.POLISHED_GLACIO_STONE_STAIRS, 4, ModItems.POLISHED_GLACIO_STONE, r -> r.pattern("#  ").pattern("## ").pattern("###"));

        shaped(ModItems.POLISHED_MOON_STONE_SLAB, 6, ModItems.POLISHED_MOON_STONE, r -> r.pattern("###"));
        shaped(ModItems.POLISHED_MARS_STONE_SLAB, 6, ModItems.POLISHED_MARS_STONE, r -> r.pattern("###"));
        shaped(ModItems.POLISHED_VENUS_STONE_SLAB, 6, ModItems.POLISHED_VENUS_STONE, r -> r.pattern("###"));
        shaped(ModItems.POLISHED_MERCURY_STONE_SLAB, 6, ModItems.POLISHED_MERCURY_STONE, r -> r.pattern("###"));
        shaped(ModItems.POLISHED_GLACIO_STONE_SLAB, 6, ModItems.POLISHED_GLACIO_STONE, r -> r.pattern("###"));

        shaped(ModItems.MOON_PILLAR, 2, ModItems.MOON_STONE, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.MARS_PILLAR, 2, ModItems.MARS_STONE, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.VENUS_PILLAR, 2, ModItems.VENUS_STONE, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.MERCURY_PILLAR, 2, ModItems.MERCURY_STONE, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.GLACIO_PILLAR, 2, ModItems.GLACIO_STONE, r -> r.pattern("#").pattern("#"));

        shaped(ModItems.MOON_STONE_BRICK_WALL, 6, ModItems.MOON_STONE_BRICKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.MARS_STONE_BRICK_WALL, 6, ModItems.MARS_STONE_BRICKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.VENUS_STONE_BRICK_WALL, 6, ModItems.VENUS_STONE_BRICKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.MERCURY_STONE_BRICK_WALL, 6, ModItems.MERCURY_STONE_BRICKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.GLACIO_STONE_BRICK_WALL, 6, ModItems.GLACIO_STONE_BRICKS, r -> r.pattern("###").pattern("###"));

        oreSmelt(ModItems.CHEESE, ModItems.MOON_CHEESE_ORE);
        oreSmelt(ModItems.DESH_INGOT, ModItems.MOON_DESH_ORE);
        oreSmelt(ModItems.DESH_INGOT, ModItems.DEEPSLATE_DESH_ORE);
        oreSmelt(() -> Items.IRON_INGOT, ModItems.MOON_IRON_ORE);
        oreSmelt(ModItems.ICE_SHARD, ModItems.MOON_ICE_SHARD_ORE);
        oreSmelt(ModItems.ICE_SHARD, ModItems.DEEPSLATE_ICE_SHARD_ORE);
        oreSmelt(() -> Items.IRON_INGOT, ModItems.MARS_IRON_ORE);
        oreSmelt(() -> Items.DIAMOND, ModItems.MARS_DIAMOND_ORE);
        oreSmelt(ModItems.OSTRUM_INGOT, ModItems.MARS_OSTRUM_ORE);
        oreSmelt(ModItems.OSTRUM_INGOT, ModItems.DEEPSLATE_OSTRUM_ORE);
        oreSmelt(ModItems.ICE_SHARD, ModItems.MARS_ICE_SHARD_ORE);
        oreSmelt(() -> Items.COAL, ModItems.VENUS_COAL_ORE);
        oreSmelt(() -> Items.GOLD_INGOT, ModItems.VENUS_GOLD_ORE);
        oreSmelt(() -> Items.DIAMOND, ModItems.VENUS_DIAMOND_ORE);
        oreSmelt(ModItems.CALORITE_INGOT, ModItems.VENUS_CALORITE_ORE);
        oreSmelt(ModItems.CALORITE_INGOT, ModItems.DEEPSLATE_CALORITE_ORE);
        oreSmelt(() -> Items.IRON_INGOT, ModItems.MERCURY_IRON_ORE);
        oreSmelt(ModItems.ICE_SHARD, ModItems.GLACIO_ICE_SHARD_ORE);
        oreSmelt(() -> Items.COAL, ModItems.GLACIO_COAL_ORE);
        oreSmelt(() -> Items.COPPER_INGOT, ModItems.GLACIO_COPPER_ORE);
        oreSmelt(() -> Items.IRON_INGOT, ModItems.GLACIO_IRON_ORE);
        oreSmelt(() -> Items.LAPIS_LAZULI, ModItems.GLACIO_LAPIS_ORE);

        oreSmelt(ModItems.DESH_INGOT, ModItemTags.RAW_DESH);
        oreSmelt(ModItems.OSTRUM_INGOT, ModItemTags.RAW_OSTRUM);
        oreSmelt(ModItems.CALORITE_INGOT, ModItemTags.RAW_CALORITE);

        stoneCutting(ModItems.MOON_STONE_STAIRS, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_STONE_STAIRS, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_STONE_STAIRS, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_STONE_STAIRS, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_STONE_STAIRS, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.MOON_STONE_SLAB, 2, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_STONE_SLAB, 2, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_STONE_SLAB, 2, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_STONE_SLAB, 2, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_STONE_SLAB, 2, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.MOON_STONE_BRICK_SLAB, 2, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_STONE_BRICK_SLAB, 2, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_STONE_BRICK_SLAB, 2, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_STONE_BRICK_SLAB, 2, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_STONE_BRICK_SLAB, 2, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.MOON_PILLAR, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_PILLAR, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_PILLAR, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_PILLAR, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_PILLAR, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.MOON_STONE_BRICK_STAIRS, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_STONE_BRICK_STAIRS, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_STONE_BRICK_STAIRS, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_STONE_BRICK_STAIRS, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_STONE_BRICK_STAIRS, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.CHISELED_MOON_STONE_SLAB, 2, ModItems.CHISELED_MOON_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_MARS_STONE_SLAB, 2, ModItems.CHISELED_MARS_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_VENUS_STONE_SLAB, 2, ModItems.CHISELED_VENUS_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_MERCURY_STONE_SLAB, 2, ModItems.CHISELED_MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_GLACIO_STONE_SLAB, 2, ModItems.CHISELED_GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.CHISELED_MOON_STONE_STAIRS, 1, ModItems.CHISELED_MOON_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_MARS_STONE_STAIRS, 1, ModItems.CHISELED_MARS_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_VENUS_STONE_STAIRS, 1, ModItems.CHISELED_VENUS_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_MERCURY_STONE_STAIRS, 1, ModItems.CHISELED_MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_GLACIO_STONE_STAIRS, 1, ModItems.CHISELED_GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.CHISELED_MOON_STONE_BRICKS, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.CHISELED_MARS_STONE_BRICKS, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.CHISELED_VENUS_STONE_BRICKS, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.CHISELED_MERCURY_STONE_BRICKS, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.CHISELED_GLACIO_STONE_BRICKS, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.POLISHED_MOON_STONE, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.POLISHED_MARS_STONE, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.POLISHED_VENUS_STONE, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.POLISHED_MERCURY_STONE, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.POLISHED_GLACIO_STONE, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.POLISHED_MOON_STONE_SLAB, 2, ModItems.MOON_STONE);
        stoneCutting(ModItems.POLISHED_MARS_STONE_SLAB, 2, ModItems.MARS_STONE);
        stoneCutting(ModItems.POLISHED_VENUS_STONE_SLAB, 2, ModItems.VENUS_STONE);
        stoneCutting(ModItems.POLISHED_MERCURY_STONE_SLAB, 2, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.POLISHED_GLACIO_STONE_SLAB, 2, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.POLISHED_MOON_STONE_STAIRS, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.POLISHED_MARS_STONE_STAIRS, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.POLISHED_VENUS_STONE_STAIRS, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.POLISHED_MERCURY_STONE_STAIRS, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.POLISHED_GLACIO_STONE_STAIRS, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.MOON_STONE_BRICKS, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_STONE_BRICKS, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_STONE_BRICKS, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_STONE_BRICKS, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_STONE_BRICKS, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.MOON_STONE_BRICK_WALL, 1, ModItems.MOON_STONE);
        stoneCutting(ModItems.MARS_STONE_BRICK_WALL, 1, ModItems.MARS_STONE);
        stoneCutting(ModItems.VENUS_STONE_BRICK_WALL, 1, ModItems.VENUS_STONE);
        stoneCutting(ModItems.MERCURY_STONE_BRICK_WALL, 1, ModItems.MERCURY_STONE);
        stoneCutting(ModItems.GLACIO_STONE_BRICK_WALL, 1, ModItems.GLACIO_STONE);

        stoneCutting(ModItems.CHISELED_MOON_STONE_BRICKS, 1, ModItems.MOON_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_MARS_STONE_BRICKS, 1, ModItems.MARS_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_VENUS_STONE_BRICKS, 1, ModItems.VENUS_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_MERCURY_STONE_BRICKS, 1, ModItems.MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.CHISELED_GLACIO_STONE_BRICKS, 1, ModItems.GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.POLISHED_MOON_STONE, 1, ModItems.MOON_STONE_BRICKS);
        stoneCutting(ModItems.POLISHED_MARS_STONE, 1, ModItems.MARS_STONE_BRICKS);
        stoneCutting(ModItems.POLISHED_VENUS_STONE, 1, ModItems.VENUS_STONE_BRICKS);
        stoneCutting(ModItems.POLISHED_MERCURY_STONE, 1, ModItems.MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.POLISHED_GLACIO_STONE, 1, ModItems.GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.POLISHED_MOON_STONE_SLAB, 2, ModItems.POLISHED_MOON_STONE);
        stoneCutting(ModItems.POLISHED_MARS_STONE_SLAB, 2, ModItems.POLISHED_MARS_STONE);
        stoneCutting(ModItems.POLISHED_VENUS_STONE_SLAB, 2, ModItems.POLISHED_VENUS_STONE);
        stoneCutting(ModItems.POLISHED_MERCURY_STONE_SLAB, 2, ModItems.POLISHED_MERCURY_STONE);
        stoneCutting(ModItems.POLISHED_GLACIO_STONE_SLAB, 2, ModItems.POLISHED_GLACIO_STONE);

        stoneCutting(ModItems.POLISHED_MOON_STONE_STAIRS, 1, ModItems.POLISHED_MOON_STONE);
        stoneCutting(ModItems.POLISHED_MARS_STONE_STAIRS, 1, ModItems.POLISHED_MARS_STONE);
        stoneCutting(ModItems.POLISHED_VENUS_STONE_STAIRS, 1, ModItems.POLISHED_VENUS_STONE);
        stoneCutting(ModItems.POLISHED_MERCURY_STONE_STAIRS, 1, ModItems.POLISHED_MERCURY_STONE);
        stoneCutting(ModItems.POLISHED_GLACIO_STONE_STAIRS, 1, ModItems.POLISHED_GLACIO_STONE);

        stoneCutting(ModItems.MOON_STONE_BRICK_STAIRS, 1, ModItems.MOON_STONE_BRICKS);
        stoneCutting(ModItems.MARS_STONE_BRICK_STAIRS, 1, ModItems.MARS_STONE_BRICKS);
        stoneCutting(ModItems.VENUS_STONE_BRICK_STAIRS, 1, ModItems.VENUS_STONE_BRICKS);
        stoneCutting(ModItems.MERCURY_STONE_BRICK_STAIRS, 1, ModItems.MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.GLACIO_STONE_BRICK_STAIRS, 1, ModItems.GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.MOON_STONE_BRICK_SLAB, 2, ModItems.MOON_STONE_BRICKS);
        stoneCutting(ModItems.MARS_STONE_BRICK_SLAB, 2, ModItems.MARS_STONE_BRICKS);
        stoneCutting(ModItems.VENUS_STONE_BRICK_SLAB, 2, ModItems.VENUS_STONE_BRICKS);
        stoneCutting(ModItems.MERCURY_STONE_BRICK_SLAB, 2, ModItems.MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.GLACIO_STONE_BRICK_SLAB, 2, ModItems.GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.MOON_STONE_BRICK_WALL, 1, ModItems.MOON_STONE_BRICKS);
        stoneCutting(ModItems.MARS_STONE_BRICK_WALL, 1, ModItems.MARS_STONE_BRICKS);
        stoneCutting(ModItems.VENUS_STONE_BRICK_WALL, 1, ModItems.VENUS_STONE_BRICKS);
        stoneCutting(ModItems.MERCURY_STONE_BRICK_WALL, 1, ModItems.MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.GLACIO_STONE_BRICK_WALL, 1, ModItems.GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.MOON_PILLAR, 1, ModItems.MOON_STONE_BRICKS);
        stoneCutting(ModItems.MARS_PILLAR, 1, ModItems.MARS_STONE_BRICKS);
        stoneCutting(ModItems.VENUS_PILLAR, 1, ModItems.VENUS_STONE_BRICKS);
        stoneCutting(ModItems.MERCURY_PILLAR, 1, ModItems.MERCURY_STONE_BRICKS);
        stoneCutting(ModItems.GLACIO_PILLAR, 1, ModItems.GLACIO_STONE_BRICKS);

        stoneCutting(ModItems.MOON_COBBLESTONE_STAIRS, 1, ModItems.MOON_COBBLESTONE);
        stoneCutting(ModItems.MARS_COBBLESTONE_STAIRS, 1, ModItems.MARS_COBBLESTONE);
        stoneCutting(ModItems.VENUS_COBBLESTONE_STAIRS, 1, ModItems.VENUS_COBBLESTONE);
        stoneCutting(ModItems.MERCURY_COBBLESTONE_STAIRS, 1, ModItems.MERCURY_COBBLESTONE);
        stoneCutting(ModItems.GLACIO_COBBLESTONE_STAIRS, 1, ModItems.GLACIO_COBBLESTONE);

        stoneCutting(ModItems.MOON_COBBLESTONE_SLAB, 2, ModItems.MOON_COBBLESTONE);
        stoneCutting(ModItems.MARS_COBBLESTONE_SLAB, 2, ModItems.MARS_COBBLESTONE);
        stoneCutting(ModItems.VENUS_COBBLESTONE_SLAB, 2, ModItems.VENUS_COBBLESTONE);
        stoneCutting(ModItems.MERCURY_COBBLESTONE_SLAB, 2, ModItems.MERCURY_COBBLESTONE);
        stoneCutting(ModItems.GLACIO_COBBLESTONE_SLAB, 2, ModItems.GLACIO_COBBLESTONE);

        shaped(ModItems.POLISHED_CONGLOMERATE, 4, ModItems.CONGLOMERATE, r -> r.pattern("##").pattern("##"));
        stoneCutting(ModItems.POLISHED_CONGLOMERATE, 1, ModItems.CONGLOMERATE);

        shaped(ModItems.VENUS_SANDSTONE, 4, ModItems.VENUS_SAND, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.VENUS_SANDSTONE_BRICKS, 4, ModItems.VENUS_SANDSTONE, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.VENUS_SANDSTONE_BRICK_STAIRS, 4, ModItems.VENUS_SANDSTONE_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.VENUS_SANDSTONE_BRICK_SLAB, 6, ModItems.VENUS_SANDSTONE_BRICKS, r -> r.pattern("###"));
        smelt(ModItems.CRACKED_VENUS_SANDSTONE_BRICKS, ModItems.VENUS_SANDSTONE_BRICKS);
        stoneCutting(ModItems.VENUS_SANDSTONE_BRICKS, 1, ModItems.VENUS_SANDSTONE);
        stoneCutting(ModItems.VENUS_SANDSTONE_BRICK_STAIRS, 1, ModItems.VENUS_SANDSTONE_BRICKS);
        stoneCutting(ModItems.VENUS_SANDSTONE_BRICK_SLAB, 2, ModItems.VENUS_SANDSTONE_BRICKS);

        shaped(ModItems.PERMAFROST_BRICKS, 4, ModItems.PERMAFROST, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.PERMAFROST_BRICK_STAIRS, 4, ModItems.PERMAFROST_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.PERMAFROST_BRICK_SLAB, 6, ModItems.PERMAFROST_BRICKS, r -> r.pattern("###"));
        smelt(ModItems.CRACKED_PERMAFROST_BRICKS, ModItems.PERMAFROST_BRICKS);
        shaped(ModItems.PERMAFROST_TILES, 4, ModItems.PERMAFROST_BRICKS, r -> r.pattern("##").pattern("##"));
        shaped(ModItems.CHISELED_PERMAFROST_BRICKS, 1, ModItems.PERMAFROST_BRICK_SLAB, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.CHISELED_PERMAFROST_BRICK_STAIRS, 4, ModItems.CHISELED_PERMAFROST_BRICKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.CHISELED_PERMAFROST_BRICK_SLAB, 6, ModItems.CHISELED_PERMAFROST_BRICKS, r -> r.pattern("###"));
        shaped(ModItems.POLISHED_PERMAFROST, 9, ModItems.PERMAFROST, r -> r.pattern("###").pattern("###").pattern("###"));
        shaped(ModItems.POLISHED_PERMAFROST_STAIRS, 4, ModItems.POLISHED_PERMAFROST, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.POLISHED_PERMAFROST_SLAB, 6, ModItems.POLISHED_PERMAFROST, r -> r.pattern("###"));
        shaped(ModItems.PERMAFROST_PILLAR, 2, ModItems.PERMAFROST, r -> r.pattern("#").pattern("#"));
        shaped(ModItems.PERMAFROST_BRICK_WALL, 6, ModItems.PERMAFROST_BRICKS, r -> r.pattern("###").pattern("###"));
        stoneCutting(ModItems.PERMAFROST_BRICK_STAIRS, 1, ModItems.PERMAFROST_BRICKS);
        stoneCutting(ModItems.PERMAFROST_BRICK_SLAB, 2, ModItems.PERMAFROST_BRICKS);
        stoneCutting(ModItems.PERMAFROST_TILES, 1, ModItems.PERMAFROST_BRICKS);
        stoneCutting(ModItems.CHISELED_PERMAFROST_BRICKS, 1, ModItems.PERMAFROST_BRICKS);
        stoneCutting(ModItems.CHISELED_PERMAFROST_BRICK_STAIRS, 1, ModItems.CHISELED_PERMAFROST_BRICKS);
        stoneCutting(ModItems.CHISELED_PERMAFROST_BRICK_SLAB, 2, ModItems.CHISELED_PERMAFROST_BRICKS);
        stoneCutting(ModItems.POLISHED_PERMAFROST, 1, ModItems.PERMAFROST_BRICKS);
        stoneCutting(ModItems.POLISHED_PERMAFROST_STAIRS, 1, ModItems.POLISHED_PERMAFROST);
        stoneCutting(ModItems.POLISHED_PERMAFROST_SLAB, 2, ModItems.POLISHED_PERMAFROST);
        stoneCutting(ModItems.PERMAFROST_PILLAR, 1, ModItems.PERMAFROST);
        stoneCutting(ModItems.PERMAFROST_BRICK_WALL, 1, ModItems.PERMAFROST_BRICKS);

        shapeless(ModItems.AERONOS_PLANKS, 4, ModItemTags.AERONOS_CAPS, r -> r);
        shapeless(ModItems.STROPHAR_PLANKS, 4, ModItemTags.STROPHAR_CAPS, r -> r);
        shapeless(ModItems.GLACIAN_PLANKS, 4, ModItemTags.GLACIAN_LOGS, r -> r);
        shaped(ModItems.AERONOS_STAIRS, 4, ModItems.AERONOS_PLANKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.STROPHAR_STAIRS, 4, ModItems.STROPHAR_PLANKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.GLACIAN_STAIRS, 4, ModItems.GLACIAN_PLANKS, r -> r.pattern("#  ").pattern("## ").pattern("###"));
        shaped(ModItems.AERONOS_SLAB, 6, ModItems.AERONOS_PLANKS, r -> r.pattern("###"));
        shaped(ModItems.STROPHAR_SLAB, 6, ModItems.STROPHAR_PLANKS, r -> r.pattern("###"));
        shaped(ModItems.GLACIAN_SLAB, 6, ModItems.GLACIAN_PLANKS, r -> r.pattern("###"));
        shaped(ModItems.AERONOS_FENCE, 3, ModItems.AERONOS_PLANKS, r -> r.define('|', Items.STICK).pattern("#|#").pattern("#|#"));
        shaped(ModItems.STROPHAR_FENCE, 3, ModItems.STROPHAR_PLANKS, r -> r.define('|', Items.STICK).pattern("#|#").pattern("#|#"));
        shaped(ModItems.GLACIAN_FENCE, 3, ModItems.GLACIAN_PLANKS, r -> r.define('|', Items.STICK).pattern("#|#").pattern("#|#"));
        shaped(ModItems.AERONOS_FENCE_GATE, 1, ModItems.AERONOS_PLANKS, r -> r.define('|', Items.STICK).pattern("|#|").pattern("|#|"));
        shaped(ModItems.STROPHAR_FENCE_GATE, 1, ModItems.STROPHAR_PLANKS, r -> r.define('|', Items.STICK).pattern("|#|").pattern("|#|"));
        shaped(ModItems.GLACIAN_FENCE_GATE, 1, ModItems.GLACIAN_PLANKS, r -> r.define('|', Items.STICK).pattern("|#|").pattern("|#|"));
        shaped(ModItems.AERONOS_DOOR, 3, ModItems.AERONOS_PLANKS, r -> r.pattern("##").pattern("##").pattern("##"));
        shaped(ModItems.STROPHAR_DOOR, 3, ModItems.STROPHAR_PLANKS, r -> r.pattern("##").pattern("##").pattern("##"));
        shaped(ModItems.GLACIAN_DOOR, 3, ModItems.GLACIAN_PLANKS, r -> r.pattern("##").pattern("##").pattern("##"));
        shaped(ModItems.AERONOS_TRAPDOOR, 2, ModItems.AERONOS_PLANKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.STROPHAR_TRAPDOOR, 2, ModItems.STROPHAR_PLANKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.GLACIAN_TRAPDOOR, 2, ModItems.GLACIAN_PLANKS, r -> r.pattern("###").pattern("###"));
        shaped(ModItems.AERONOS_LADDER, 6, ModItems.AERONOS_PLANKS, r -> r.pattern("# #").pattern("###").pattern("# #"));
        shaped(ModItems.STROPHAR_LADDER, 6, ModItems.STROPHAR_PLANKS, r -> r.pattern("# #").pattern("###").pattern("# #"));
        shapeless(ModItems.GLACIAN_BUTTON, 1, ModItems.GLACIAN_PLANKS, r -> r);
        shaped(ModItems.GLACIAN_PRESSURE_PLATE, 1, ModItems.GLACIAN_PLANKS, r -> r.pattern("##"));

        shaped(ModItems.SKY_STONE, 9, ModItems.MOON_STONE, r -> r
            .define('M', ModItems.MARS_STONE.get())
            .pattern("#M#")
            .pattern("M#M")
            .pattern("#M#"));
    }

    private void shaped(RegistryEntry<Item> result, int count, Supplier<Item> mainItem, Function<ShapedRecipeBuilder, ShapedRecipeBuilder> builder) {
        builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), count)
                .define('#', mainItem.get())
                .unlockedBy("has_" + result.getId().getPath(), has(mainItem.get())))
            .save(output);
    }

    private void shaped(RegistryEntry<Item> result, int count, TagKey<Item> mainItem, Function<ShapedRecipeBuilder, ShapedRecipeBuilder> builder) {
        builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), count)
                .define('#', mainItem)
                .unlockedBy("has_" + result.getId().getPath(), has(mainItem)))
            .save(output);
    }

    private void shapeless(RegistryEntry<Item> result, int count, Supplier<Item> mainItem, Function<ShapelessRecipeBuilder, ShapelessRecipeBuilder> builder) {
        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), count)
                .requires(mainItem.get())
                .unlockedBy("has_" + result.getId().getPath(), has(mainItem.get())))
            .save(output);
    }

    private void shapeless(RegistryEntry<Item> result, int count, Supplier<Item> mainItem, String file, Function<ShapelessRecipeBuilder, ShapelessRecipeBuilder> builder) {
        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), count)
                .requires(mainItem.get())
                .unlockedBy("has_" + result.getId().getPath(), has(mainItem.get())))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, file));
    }

    private void shapeless(RegistryEntry<Item> result, int count, TagKey<Item> mainItem, Function<ShapelessRecipeBuilder, ShapelessRecipeBuilder> builder) {
        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), count)
                .requires(mainItem)
                .unlockedBy("has_" + result.getId().getPath(), has(mainItem)))
            .save(output);
    }

    private void coloredSet(ResourcefulRegistry<Item> registry, int count, Function<ShapedRecipeBuilder, ShapedRecipeBuilder> builder) {
        registry.stream().forEach(result -> {
            Item wool = getWool(result);
            builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), count)
                    .define('#', wool)
                    .unlockedBy("has_" + result.getId().getPath(), has(wool)))
                .save(output);
        });
    }

    private void dyeSet(ResourcefulRegistry<Item> registry, int count, int splitPoint, Function<ShapedRecipeBuilder, ShapedRecipeBuilder> builder) {
        registry.stream().forEach(result -> {
            Item dye = getDye(result, splitPoint);
            builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), count)
                    .define('#', dye)
                    .unlockedBy("has_" + result.getId().getPath(), has(dye)))
                .save(output);
        });
    }

    private Item getWool(RegistryEntry<Item> result) {
        if (result.getId().getPath().contains("light_blue")) {
            return Items.LIGHT_BLUE_WOOL;
        }
        if (result.getId().getPath().contains("light_gray")) {
            return Items.LIGHT_GRAY_WOOL;
        }
        return BuiltInRegistries.ITEM.get(new ResourceLocation(result.getId().getPath().split("_")[0] + "_wool"));
    }

    private Item getDye(RegistryEntry<Item> result, int splitPoint) {
        if (result.getId().getPath().contains("light_blue")) {
            return Items.LIGHT_BLUE_DYE;
        }
        if (result.getId().getPath().contains("light_gray")) {
            return Items.LIGHT_GRAY_DYE;
        }
        return BuiltInRegistries.ITEM.get(new ResourceLocation(result.getId().getPath().split("_")[splitPoint] + "_dye"));
    }

    private void oreSmelt(Supplier<Item> result, Supplier<Item> mainItem) {
        smelt(result, mainItem);
        blast(result, mainItem);
    }

    private void oreSmelt(Supplier<Item> result, TagKey<Item> mainItem) {
        smelt(result, mainItem);
        blast(result, mainItem);
    }

    private void smelt(Supplier<Item> result, Supplier<Item> mainItem) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(mainItem.get()), RecipeCategory.MISC, result.get(), 0.1f, 200)
            .unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath(), has(mainItem.get()))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, "smelting/%s_from_smelting_%s"
                .formatted(ForgeRegistries.ITEMS.getKey(result.get()).getPath(), ForgeRegistries.ITEMS.getKey(mainItem.get()).getPath())));
    }

    private void smelt(Supplier<Item> result, TagKey<Item> mainItem) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(mainItem), RecipeCategory.MISC, result.get(), 0.1f, 200)
            .unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath(), has(mainItem))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, "smelting/%s_from_smelting_%s"
                .formatted(ForgeRegistries.ITEMS.getKey(result.get()).getPath(), mainItem.location().getPath())));
    }

    private void blast(Supplier<Item> result, Supplier<Item> mainItem) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(mainItem.get()), RecipeCategory.MISC, result.get(), 0.1f, 100)
            .unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath(), has(mainItem.get()))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, "blasting/%s_from_blasting_%s"
                .formatted(ForgeRegistries.ITEMS.getKey(result.get()).getPath(), ForgeRegistries.ITEMS.getKey(mainItem.get()).getPath())));
    }

    private void blast(Supplier<Item> result, TagKey<Item> mainItem) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(mainItem), RecipeCategory.MISC, result.get(), 0.1f, 100)
            .unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath(), has(mainItem))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, "blasting/%s_from_blasting_%s"
                .formatted(ForgeRegistries.ITEMS.getKey(result.get()).getPath(), mainItem.location().getPath())));
    }

    private void stoneCutting(Supplier<Item> result, int count, Supplier<Item> mainItem) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(mainItem.get()), RecipeCategory.MISC, result.get(), count)
            .unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath(), has(mainItem.get()))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, "stonecutting/%s_from_%s_stonecutting"
                .formatted(ForgeRegistries.ITEMS.getKey(result.get()).getPath(), ForgeRegistries.ITEMS.getKey(mainItem.get()).getPath())));
    }

    private void stoneCutting(Supplier<Item> result, int count, TagKey<Item> mainItem) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(mainItem), RecipeCategory.MISC, result.get(), count)
            .unlockedBy("has_" + ForgeRegistries.ITEMS.getKey(result.get()).getPath(), has(mainItem))
            .save(output, new ResourceLocation(AdAstra.MOD_ID, "stonecutting/%s_from_%s_stonecutting"
                .formatted(ForgeRegistries.ITEMS.getKey(result.get()).getPath(), mainItem.location().getPath())));
    }
}
