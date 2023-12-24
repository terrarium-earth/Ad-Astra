package earth.terrarium.adastra.datagen.provider.client;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.codehaus.plexus.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, AdAstra.MOD_ID, "en_us");
    }

    private static final Set<RegistryEntry<Item>> CUSTOM_ITEM_NAMES = Set.of();
    private static final Set<RegistryEntry<Block>> CUSTOM_BLOCK_NAMES = Set.of();

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCKS.stream()
            .filter(b -> !(CUSTOM_BLOCK_NAMES.contains(b)))
            .forEach(entry -> addBlock(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModItems.ITEMS.stream()
            .filter(i -> !(i.get() instanceof BlockItem))
            .filter(i -> !(CUSTOM_ITEM_NAMES.contains(i)))
            .forEach(entry -> addItem(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModEntityTypes.ENTITY_TYPES.stream()
            .forEach(entry -> addEntityType(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));


        ModFluids.FLUIDS.stream()
            .forEach(entry -> addFluid(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        add("itemGroup.ad_astra.main", "Ad Astra");

        add("death.attack.oxygen", "%1$s couldn't breathe anymore");
        add("death.attack.oxygen.player", "%1$s lost their breath whilst trying to escape %2$s");

        add("death.attack.cryo_fuel", "%1$s became an ice cube");
        add("death.attack.cryo_fuel.player", "%1$s became an ice cube whilst trying to escape %2$s");

        add("death.attack.ran_over", "%1$s was ran over");
        add("death.attack.ran_over.player", "%1$s was ran over by %2$s");

        add("death.attack.rocket_flames", "%1$s melted under an active rocket");
        add("death.attack.rocket_flames.player", "%1$s melted under an active rocket whilst trying to escape %2$s");

        add(ConstantComponents.TOGGLE_SUIT_FLIGHT_KEY.getString(), "Toggle suit flight");
        add(ConstantComponents.OPEN_RADIO_KEY.getString(), "Open radio");
        add(ConstantComponents.AD_ASTRA_CATEGORY.getString(), "Ad Astra");

        add(ConstantComponents.SUIT_FLIGHT_ENABLED.getString(), "Suit flight enabled");
        add(ConstantComponents.SUIT_FLIGHT_DISABLED.getString(), "Suit flight disabled");

        add(ConstantComponents.TRUE.getString(), "✔");
        add(ConstantComponents.FALSE.getString(), "✘");
        add("text.ad_astra.temperature", "%s°C");
        add("text.ad_astra.gravity", "%sm/s²");
        add("text.ad_astra.radio.none", "No station");

        add("text.ad_astra.weather.temperature", "Now: %s°C");
        add("text.ad_astra.weather.temperature.in", "In: %s");

        add("tooltip.ad_astra.energy", "%s ⚡ / %s ⚡");
        add("tooltip.ad_astra.energy_in", "In: %s ⚡/t");
        add("tooltip.ad_astra.energy_out", "Out: %s ⚡/t");
        add("tooltip.ad_astra.max_energy_in", "Max In: %s ⚡/t");
        add("tooltip.ad_astra.max_energy_out", "Max Out: %s ⚡/t");
        add("tooltip.ad_astra.energy_use_per_tick", "Uses %s ⚡ per tick");
        add("tooltip.ad_astra.energy_generation_per_tick", "Generates %s ⚡ per tick");

        add("tooltip.ad_astra.fluid", "%s 🪣 / %s 🪣 %s");
        add("tooltip.ad_astra.fluid_in", "In: %s 🪣/t");
        add("tooltip.ad_astra.fluid_out", "Out: %s 🪣/t");
        add("tooltip.ad_astra.max_fluid_in", "Max In: %s 🪣/t");
        add("tooltip.ad_astra.max_fluid_out", "Max Out: %s 🪣/t");
        add("tooltip.ad_astra.fluid_use_per_iteration", "Uses %s 🪣 per iteration");
        add("tooltip.ad_astra.fluid_generation_per_iteration", "Generates %s 🪣 per iteration");

        add("tooltip.ad_astra.ticks_per_iteration", "Takes %s ticks per iteration");

        add("tooltip.ad_astra.progress", "%s / %s");
        add("tooltip.ad_astra.eta", "ETA: %s seconds");

        add("tooltip.ad_astra.energy_per_tick", "%s ⚡/t");
        add("tooltip.ad_astra.max_generation", "Max: %s ⚡/t");

        add("tooltip.ad_astra.fluid_per_tick", "%s 🪣/t");

        add("tooltip.ad_astra.blocks_distributed", "%s/%s");
        add(ConstantComponents.SHOW.getString(), "Show");
        add(ConstantComponents.HIDE.getString(), "Hide");
        add(ConstantComponents.OXYGEN_DISTRIBUTION_AREA.getString(), "Show where the oxygen is being distributed");

        add(ConstantComponents.CLEAR_FLUID_TANK.getString(), "Shift-right-click to clear");

        add(ConstantComponents.SIDE_CONFIG.getString(), "Side Config");
        add(ConstantComponents.REDSTONE_CONTROL.getString(), "Redstone Control");

        add("tooltip.ad_astra.redstone_control.always_on", "Always on");
        add("tooltip.ad_astra.redstone_control.on_when_powered", "On when powered");
        add("tooltip.ad_astra.redstone_control.on_when_not_powered", "On when not powered");
        add("tooltip.ad_astra.redstone_control.never_on", "Never on");
        add("tooltip.ad_astra.redstone_control.mode", "Mode: %s");

        add(ConstantComponents.ACTIVE.getString(), "Active");
        add(ConstantComponents.INACTIVE.getString(), "Inactive");

        add(ConstantComponents.SEQUENTIAL.getString(), "Sequential");
        add(ConstantComponents.ROUND_ROBIN.getString(), "Round Robin");

        add(ConstantComponents.CAPACITOR_ENABLED.getString(), "Capacitor enabled");
        add(ConstantComponents.CAPACITOR_DISABLED.getString(), "Capacitor disabled");
        add(ConstantComponents.CHANGE_MODE_SEQUENTIAL.getString(), "Set capacitor mode to \"Sequential\"");
        add(ConstantComponents.CHANGE_MODE_ROUND_ROBIN.getString(), "Set capacitor mode to \"Round Robin\"");

        add(ConstantComponents.SHIFT_DESCRIPTION.getString(), "Hold SHIFT for more information");
        add(ConstantComponents.TI_69_INFO.getString(), "Displays important information. Right-click to change the current app.");
        add(ConstantComponents.ETRIONIC_CAPACITOR_INFO.getString(), "Stores energy. Right-click to toggle. Shift-right-click to change the distribution mode.");
        add(ConstantComponents.ZIP_GUN_INFO.getString(), "Propels you forward. Use a second one in your offhand to propel even further.");
        add(ConstantComponents.GAS_TANK_INFO.getString(), "Stores fluids and gases. Right-click to distribute into your inventory.");
        add(ConstantComponents.CABLE_INFO.getString(), "Transfers energy. Right-click with a wrench to change the distribution mode.");
        add(ConstantComponents.FLUID_PIPE_INFO.getString(), "Transfers fluids. Right-click with a wrench to change the distribution mode.");
        add(ConstantComponents.CABLE_DUCT.getString(), "Works like cables, but fully-conceals oxygen, preventing leaks.");
        add(ConstantComponents.FLUID_DUCT_INFO.getString(), "Works like fluid pipes, but fully-conceals oxygen, preventing leaks.");
        add(ConstantComponents.SPACE_SUIT_INFO.getString(), "Supplies the wearer with oxygen and protects them from the extreme-cold of space.");
        add(ConstantComponents.NETHERITE_SPACE_SUIT_INFO.getString(), "Works like the Space Suit, with the added advantage of protecting the wearer from extreme heat.");
        add(ConstantComponents.JET_SUIT_INFO.getString(), "Works like the Netherite Space Suit, with jets installed to propel the wearer forward with extreme force.");
        add(ConstantComponents.SLIDING_DOOR_INFO.getString(), "A large 3x3 door. Shift-right-click with a wrench to lock it. Locked doors can only be opened with redstone.");
        add(ConstantComponents.FLAG_INFO.getString(), "A flag with your face on it. Right-click to add an image from a URL.");
        add(ConstantComponents.LAUNCH_PAD_INFO.getString(), "Launches rockets. Power the center part with redstone to automatically launch placed rockets.");
        add(ConstantComponents.GLOBE_INFO.getString(), "Right-click to spin the globe. Power it with redstone to make it constantly spin.");

        add(ConstantComponents.ROCKET_INFO.getString(), "A rocket used for interplanetary travel. Must be placed on a launch pad. Shift-right-click to access its fuel and inventory.");
        add(ConstantComponents.ROVER_INFO.getString(), "An all-terrain vehicle. Shift-right-click to access its inventory. Click on the screen to access the rover's radio. This radio can play real radio stations.");

        add(ConstantComponents.COAL_GENERATOR_INFO.getString(), "The simplest of generators. Generates energy from burnable materials.");
        add(ConstantComponents.COMPRESSOR_INFO.getString(), "Compresses materials into plates.");
        add(ConstantComponents.NASA_WORKBENCH_INFO.getString(), "Used to craft rockets.");
        add(ConstantComponents.FUEL_REFINERY_INFO.getString(), "Refines fuel from oil.");
        add(ConstantComponents.OXYGEN_LOADER_INFO.getString(), "Converts water into oxygen. Use it to fill up space suits and gas tanks.");
        add(ConstantComponents.SOLAR_PANEL_INFO.getString(), "Generates energy from the sun during the dat. Note that the energy generated is dependent on the planet.");
        add(ConstantComponents.WATER_PUMP_INFO.getString(), "Pumps water. Ensure a water source is below the pump for it to function.");
        add(ConstantComponents.OXYGEN_DISTRIBUTOR_INFO.getString(), "Distributes oxygen and regulates the temperature. Use it to create livable environments.");
        add(ConstantComponents.ENERGIZER_INFO.getString(), "Stores a large amount of energy. Right-click with an item to charge it. Retains its charge when broken.");
        add(ConstantComponents.CRYO_FREEZER_INFO.getString(), "Converts ice materials into cryo fuel.");
        add(ConstantComponents.OXYGEN_SENSOR_INFO.getString(), "Emits a redstone signal when oxygen is detected. Shift-right-click to invert the signal.");

        add("tooltip.ad_astra.energy_transfer_tick", "Transfer: %s ⚡/t");
        add("tooltip.ad_astra.fluid_transfer_tick", "Transfer: %s 🪣/t");

        add(ConstantComponents.UPGRADES.getString(), "Upgrades");
        add("tooltip.ad_astra.upgrades.entry", "- %sx %s");
        add("upgrade.ad_astra.speed", "Speed Upgrade");
        add("upgrade.ad_astra.capacity", "Capacity Upgrade");

        add(ConstantComponents.SIDE_CONFIG_SLOTS.getString(), "Slots");
        add(ConstantComponents.SIDE_CONFIG_ENERGY.getString(), "Energy");
        add(ConstantComponents.SIDE_CONFIG_FLUID.getString(), "Fluid");
        add(ConstantComponents.SIDE_CONFIG_INPUT_SLOTS.getString(), "Input slots");
        add(ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS.getString(), "Output slots");
        add(ConstantComponents.SIDE_CONFIG_EXTRACTION_SLOTS.getString(), "Extraction slots");
        add(ConstantComponents.SIDE_CONFIG_INPUT_FLUID.getString(), "Input fluid");
        add(ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID.getString(), "Output fluid");

        add("side_config.ad_astra.title", "Side Config [%s]");
        add("side_config.ad_astra.type.type", "Type: %s");
        add("side_config.ad_astra.type.direction", "Direction: %s (%s)");
        add("side_config.ad_astra.type.action", "Action: %s");

        add("side_config.ad_astra.type.none", "None");
        add("side_config.ad_astra.type.push", "Push");
        add("side_config.ad_astra.type.pull", "Pull");
        add("side_config.ad_astra.type.push_pull", "Push/Pull");

        add("side_config.ad_astra.type.slot", "Slot");
        add("side_config.ad_astra.type.energy", "Energy");
        add("side_config.ad_astra.type.fluid", "Fluid");

        add("direction.ad_astra.up", "Up");
        add("direction.ad_astra.down", "Down");
        add("direction.ad_astra.north", "North");
        add("direction.ad_astra.east", "East");
        add("direction.ad_astra.south", "South");
        add("direction.ad_astra.west", "West");

        add("direction.ad_astra.relative.up", "Top");
        add("direction.ad_astra.relative.down", "Bottom");
        add("direction.ad_astra.relative.north", "Front");
        add("direction.ad_astra.relative.east", "Left");
        add("direction.ad_astra.relative.south", "Back");
        add("direction.ad_astra.relative.west", "Right");

        add(ConstantComponents.NEXT.getString(), "Next");
        add(ConstantComponents.PREVIOUS.getString(), "Previous");
        add(ConstantComponents.RESET_TO_DEFAULT.getString(), "Reset to default");

        add(ConstantComponents.DETECTOR_INVERTED_TRUE.getString(), "Set to inverted");
        add(ConstantComponents.DETECTOR_INVERTED_FALSE.getString(), "Set to normal");
        add(ConstantComponents.DETECTOR_OXYGEN_MODE.getString(), "Set to oxygen detection mode");
        add(ConstantComponents.DETECTOR_GRAVITY_MODE.getString(), "Set to normal gravity detection mode");
        add(ConstantComponents.DETECTOR_TEMPERATURE_MODE.getString(), "Set to safe temperature detection Mode");

        add(ConstantComponents.FLAG_URL.getString(), "Flag Url (https://i.imgur.com/urURL.png)");
        add(ConstantComponents.CONFIRM.getString(), "Confirm");
        add(ConstantComponents.NOT_THE_OWNER.getString(), "You do not own this flag!");

        add(ConstantComponents.NOT_ENOUGH_FUEL.getString(), "Not enough fuel!");

        add(ConstantComponents.CATALOG.getString(), "Catalog");
        add(ConstantComponents.LAND.getString(), "Land");
        add(ConstantComponents.SPACE_STATION.getString(), "Space Station");
        add(ConstantComponents.CONSTRUCT.getString(), "Construct");
        add("tooltip.ad_astra.land", "Land on %s at [%s, %s]");
        add("tooltip.ad_astra.space_station_land", "Land on space station in %s at [%s, %s]");
        add("tooltip.ad_astra.construct_space_station_at", "Construct space station in %s at [%s, %s]");

        add(ConstantComponents.CONSTRUCTION_COST.getString(), "Construction Cost:");
        add("tooltip.ad_astra.requirement", "%s/%s %s");

        add(ConstantComponents.SPACE_STATION_ALREADY_EXISTS.getString(), "A space station already exists at this location!");

        add("solar_system.ad_astra.solar_system", "Solar System");
        add("solar_system.ad_astra.proxima_centauri", "Proxima Centauri");

        add("planet.minecraft.overworld", "Earth");
        add("planet.ad_astra.moon", "Moon");
        add("planet.ad_astra.mars", "Mars");
        add("planet.ad_astra.venus", "Venus");
        add("planet.ad_astra.mercury", "Mercury");
        add("planet.ad_astra.glacio", "Glacio");

        add("planet.ad_astra.earth_orbit", "Earth Orbit");
        add("planet.ad_astra.moon_orbit", "Moon Orbit");
        add("planet.ad_astra.mars_orbit", "Mars Orbit");
        add("planet.ad_astra.venus_orbit", "Venus Orbit");
        add("planet.ad_astra.mercury_orbit", "Mercury Orbit");
        add("planet.ad_astra.glacio_orbit", "Glacio Orbit");

        add("tag.item.ad_astra.globes", "Globes");
        add("tag.item.ad_astra.flags", "Flags");
        add("tag.item.ad_astra.sliding_doors", "Sliding Doors");
        add("tag.item.ad_astra.space_suits", "Space Suits");
        add("tag.item.ad_astra.netherite_space_suits", "Netherite Space Suits");
        add("tag.item.ad_astra.jet_suits", "Jet Suits");
        add("tag.item.ad_astra.freeze_resistant_armor", "Freeze-Resistant Armor");
        add("tag.item.ad_astra.heat_resistant_armor", "Heat-Resistant Armor");
        add("tag.item.ad_astra.held_over_head", "Held Over Head");
        add("tag.item.ad_astra.cable_ducts", "Cable Ducts");
        add("tag.item.ad_astra.fluid_pipe_ducts", "Fluid Pipe Ducts");
        add("tag.item.ad_astra.iron_plates", "Iron Plates");
        add("tag.item.ad_astra.iron_rods", "Iron Rods");
        add("tag.item.ad_astra.steel_ingots", "Steel Ingots");
        add("tag.item.ad_astra.steel_nuggets", "Steel Nuggets");
        add("tag.item.ad_astra.steel_plates", "Steel Plates");
        add("tag.item.ad_astra.steel_rods", "Steel Rods");
        add("tag.item.ad_astra.steel_blocks", "Steel Blocks");
        add("tag.item.ad_astra.desh_ingots", "Desh Ingots");
        add("tag.item.ad_astra.desh_nuggets", "Desh Nuggets");
        add("tag.item.ad_astra.desh_plates", "Desh Plates");
        add("tag.item.ad_astra.raw_desh", "Raw Desh");
        add("tag.item.ad_astra.desh_blocks", "Desh Blocks");
        add("tag.item.ad_astra.raw_desh_blocks", "Raw Desh Blocks");
        add("tag.item.ad_astra.ostrum_ingots", "Ostrum Ingots");
        add("tag.item.ad_astra.ostrum_nuggets", "Ostrum Nuggets");
        add("tag.item.ad_astra.ostrum_plates", "Ostrum Plates");
        add("tag.item.ad_astra.raw_ostrum", "Raw Ostrum");
        add("tag.item.ad_astra.ostrum_blocks", "Ostrum Blocks");
        add("tag.item.ad_astra.raw_ostrum_blocks", "Raw Ostrum Blocks");
        add("tag.item.ad_astra.calorite_ingots", "Calorite Ingots");
        add("tag.item.ad_astra.calorite_nuggets", "Calorite Nuggets");
        add("tag.item.ad_astra.calorite_plates", "Calorite Plates");
        add("tag.item.ad_astra.raw_calorite", "Raw Calorite");
        add("tag.item.ad_astra.calorite_blocks", "Calorite Blocks");
        add("tag.item.ad_astra.raw_calorite_blocks", "Raw Calorite Blocks");

        add("config.ad_astra.allowFlagImages", "Allow flag images");

        add("subtitles.ad_astra.block.rocket_launch", "Rocket launches");
        add("subtitles.ad_astra.block.rocket", "Rocket flies");
        add("subtitles.ad_astra.block.wrench", "Wrench ratchets");
        add("subtitles.ad_astra.block.sliding_door_open", "Sliding door opens");
        add("subtitles.ad_astra.block.sliding_door_close", "Sliding door closes");
        add("subtitles.ad_astra.entity.oxygen_intake", "Oxygen intake");
        add("subtitles.ad_astra.entity.oxygen_outtake", "Oxygen outtake");
    }

    public void addFluid(Supplier<? extends Fluid> key, String name) {
        if (key.get() instanceof BotariumFlowingFluid) return;
        add("fluid_type.%s.%s".formatted(AdAstra.MOD_ID, Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key.get())).getPath()), name);
    }
}
