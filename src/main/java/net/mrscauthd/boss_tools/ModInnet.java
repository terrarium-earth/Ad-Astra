package net.mrscauthd.boss_tools;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.*;
import net.mrscauthd.boss_tools.armor.SpaceSuit;
import net.mrscauthd.boss_tools.block.*;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.crafting.BlastingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.CompressingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.OxygenBubbleDistributorRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.OxygenLoaderRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipeSerializer;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.effects.OxygenEffect;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.feature.DesertSurfaceBuilder;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;
import net.mrscauthd.boss_tools.fluid.OilFluid;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGui;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGui;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGui;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGui;
import net.mrscauthd.boss_tools.gui.screens.lander.LanderGui;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGui;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGui;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGui;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGui;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui;
import net.mrscauthd.boss_tools.gui.screens.rover.RoverGui;
import net.mrscauthd.boss_tools.gui.screens.solarpanel.SolarPanelGui;
import net.mrscauthd.boss_tools.gui.screens.waterpump.WaterPumpGui;
import net.mrscauthd.boss_tools.item.*;
import net.mrscauthd.boss_tools.itemtiers.SteelItemTier;
import net.mrscauthd.boss_tools.machines.*;
import net.mrscauthd.boss_tools.machines.tile.WaterPumpTileEntity;
import net.mrscauthd.boss_tools.spawneggs.ModSpawnEggs;
import net.mrscauthd.boss_tools.entity.pygro.PygroEntity;
import net.mrscauthd.boss_tools.flag.*;
import net.mrscauthd.boss_tools.fluid.FuelFluid;
import net.mrscauthd.boss_tools.armor.NetheriteSpaceSuit;
import net.mrscauthd.boss_tools.world.biomes.BiomeRegistry;
import net.mrscauthd.boss_tools.events.Config;
import net.mrscauthd.boss_tools.feature.MarsIceSpikeFeature;
import net.mrscauthd.boss_tools.feature.VenusDeltas;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;
import net.mrscauthd.boss_tools.entity.pygro.PygroMobsSensor;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureEntity;
import net.mrscauthd.boss_tools.world.structure.configuration.STConfiguredStructures;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInnet {

    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, BossToolsMod.ModId);

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITYS = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BossToolsMod.ModId);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BossToolsMod.ModId);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, BossToolsMod.ModId);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BossToolsMod.ModId);

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BossToolsMod.ModId);

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BossToolsMod.ModId);

    public static final DeferredRegister<ContainerType<?>> GUIS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BossToolsMod.ModId);

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BossToolsMod.ModId);

    //Vehicle Items
    public static final RegistryObject<Item> TIER_1_ROCKET_ITEM = ITEMS.register("rocket_t1", () -> new Tier1RocketItem(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1)));
    public static final RegistryObject<Item> TIER_2_ROCKET_ITEM = ITEMS.register("rocket_t2", () -> new Tier2RocketItem(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1)));
    public static final RegistryObject<Item> TIER_3_ROCKET_ITEM = ITEMS.register("rocket_t3", () -> new Tier3RocketItem(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1)));
    public static final RegistryObject<Item> ROVER_ITEM = ITEMS.register("rover", () -> new RoverItem(new Item.Properties().group(BossToolsItemGroups.tab_normal).maxStackSize(1)));

    //Entitys
    public static RegistryObject<EntityType<?>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder.create(AlienEntity::new, EntityClassification.CREATURE).size(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));
    public static RegistryObject<EntityType<?>> ALIEN_ZOMBIE = ENTITYS.register("alien_zombie", () -> EntityType.Builder.create(AlienZombieEntity::new, EntityClassification.MONSTER).size(0.6f, 2.4f).build(new ResourceLocation("boss_tools", "alien_zombie").toString()));
    public static RegistryObject<EntityType<?>> STAR_CRAWLER = ENTITYS.register("star_crawler", () -> EntityType.Builder.create(StarCrawlerEntity::new, EntityClassification.MONSTER).size(1.3f, 1f).build(new ResourceLocation("boss_tools", "star_crawler").toString()));
    public static RegistryObject<EntityType<?>> PYGRO = ENTITYS.register("pygro", () -> EntityType.Builder.create(PygroEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.6f, 1.8f).build(new ResourceLocation("boss_tools", "pygro").toString()));
    public static RegistryObject<EntityType<?>> MOGLER = ENTITYS.register("mogler", () -> EntityType.Builder.create(MoglerEntity::new, EntityClassification.MONSTER).size(1.4f, 1.4f).build(new ResourceLocation("boss_tools", "mogler").toString()));

    //VEHICLES
    public static RegistryObject<EntityType<?>> TIER_1_ROCKET = ENTITYS.register("rocket_t1", () -> EntityType.Builder.create(RocketTier1Entity::new, EntityClassification.MISC).size(1.1f, 4.4f).immuneToFire().build(new ResourceLocation("boss_tools", "rocket_t1").toString()));
    public static RegistryObject<EntityType<?>> TIER_2_ROCKET = ENTITYS.register("rocket_t2", () -> EntityType.Builder.create(RocketTier2Entity::new, EntityClassification.MISC).size(1.1f, 4.6f).immuneToFire().build(new ResourceLocation("boss_tools", "rocket_t2").toString()));
    public static RegistryObject<EntityType<?>> TIER_3_ROCKET = ENTITYS.register("rocket_t3", () -> EntityType.Builder.create(RocketTier3Entity::new, EntityClassification.MISC).size(1.1f, 4.8f).immuneToFire().build(new ResourceLocation("boss_tools", "rocket_t3").toString()));
    public static RegistryObject<EntityType<?>> LANDER = ENTITYS.register("lander", () -> EntityType.Builder.create(LanderEntity::new, EntityClassification.MISC).size(0.6f, 2.0f).immuneToFire().build(new ResourceLocation("boss_tools", "lander").toString()));
    public static RegistryObject<EntityType<?>> ROVER = ENTITYS.register("rover", () -> EntityType.Builder.create(RoverEntity::new, EntityClassification.MISC).size(2.5f, 1.0f).immuneToFire().build(new ResourceLocation("boss_tools", "rover").toString()));

    //Rocket Launch Pad
    public static final RegistryObject<Block> ROCKET_LAUNCH_PAD = BLOCKS.register("rocket_launch_pad", () -> new RocketLaunchPad(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));


    //Alien Spit Entity
    public static RegistryObject<EntityType<? extends AlienSpitEntity>> ALIEN_SPIT_ENTITY = ENTITYS.register("alien_spit_entity", () -> EntityType.Builder.<AlienSpitEntity>create(AlienSpitEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation("boss_tools", "alien_spit_entity").toString()));

    //pygro
    public static final DeferredRegister<SensorType<?>> SENSOR = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, BossToolsMod.ModId);
    public static final RegistryObject<SensorType<PygroMobsSensor>> PYGRO_SENSOR = SENSOR.register("pygro_sensor", ()->new SensorType<>(PygroMobsSensor::new));

    //Sounds
    public static RegistryObject<SoundEvent> ROCKET_SOUND = SOUNDS.register("rocket_fly",() -> new SoundEvent(new ResourceLocation(BossToolsMod.ModId, "rocket_fly")));

    //Blocks
    public static RegistryObject<Block> COAL_TORCH_BLOCK = BLOCKS.register("coal_torch",() -> new CoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static RegistryObject<Block> WALL_COAL_TORCH_BLOCK = BLOCKS.register("wall_coal_torch",() -> new WallCoalTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD).lootFrom(COAL_TORCH_BLOCK.get())));
    public static RegistryObject<Block> COAL_LANTERN_BLOCK = BLOCKS.register("coal_lantern",() -> new CoalLanternBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(3.5F).sound(SoundType.LANTERN).notSolid()));


    //Flag Blocks
    public static RegistryObject<Block> FLAG_BLOCK = BLOCKS.register("flag",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_BLUE_BLOCK = BLOCKS.register("flag_blue",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_BROWN_BLOCK = BLOCKS.register("flag_brown",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_CYAN_BLOCK = BLOCKS.register("flag_cyan",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_GRAY_BLOCK = BLOCKS.register("flag_gray",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_GREEN_BLOCK = BLOCKS.register("flag_green",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_LIGHT_BLUE_BLOCK = BLOCKS.register("flag_light_blue",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_LIME_BLOCK = BLOCKS.register("flag_lime",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_MAGENTA_BLOCk = BLOCKS.register("flag_magenta",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_ORANGE_BLOCK = BLOCKS.register("flag_orange",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_PINK_BLOCK = BLOCKS.register("flag_pink",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_PURPLE_BLOCK = BLOCKS.register("flag_purple",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_RED_BLOCK = BLOCKS.register("flag_red",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> FLAG_YELLOW_BLOCK = BLOCKS.register("flag_yellow",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false).harvestTool(ToolType.PICKAXE)));

    //Tile Entity RegistryObject
    public static final RegistryObject<TileEntityType<?>> FUEL_REFINERY = TILE_ENTITYS.register("fuel_refinery", () -> TileEntityType.Builder.create(FuelRefineryBlock.CustomTileEntity::new,ModInnet.FUEL_REFINERY_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BLAST_FURNACE = TILE_ENTITYS.register("blast_furnace", () -> TileEntityType.Builder.create(BlastingFurnaceBlock.CustomTileEntity::new,ModInnet.BLAST_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> COMPRESSOR = TILE_ENTITYS.register("compressor", () -> TileEntityType.Builder.create(CompressorBlock.CustomTileEntity::new,ModInnet.COMPRESSOR_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> COAL_GENERATOR = TILE_ENTITYS.register("coal_generator", () -> TileEntityType.Builder.create(CoalGeneratorBlock.CustomTileEntity::new,ModInnet.COAL_GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> OXYGEN_LOADER = TILE_ENTITYS.register("oxygen_loader", () -> TileEntityType.Builder.create(OxygenLoaderBlock.CustomTileEntity::new,ModInnet.OXYGEN_LOADER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> SOLAR_PANEL = TILE_ENTITYS.register("solar_panel", () -> TileEntityType.Builder.create(SolarPanelBlock.CustomTileEntity::new,ModInnet.SOLAR_PANEL_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> NASA_WORKBENCH = TILE_ENTITYS.register("nasa_workbench", () -> TileEntityType.Builder.create(NASAWorkbenchBlock.CustomTileEntity::new,ModInnet.NASA_WORKBENCH_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<OxygenBubbleDistributorBlock.CustomTileEntity>> OXYGEN_BUBBLE_DISTRIBUTOR = TILE_ENTITYS.register("oxygen_bubble_distributor", () -> TileEntityType.Builder.create(OxygenBubbleDistributorBlock.CustomTileEntity::new,ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> WATER_PUMP = TILE_ENTITYS.register("water_pump", () -> TileEntityType.Builder.create(WaterPumpTileEntity::new,ModInnet.WATER_PUMP_BLOCK.get()).build(null));


    //Tile Entitys Flags
    public static final RegistryObject<BlockEntityType<FlagTileEntity>> FLAG = TILE_ENTITYS.register("flag", () -> BlockEntityType.Builder.of(FlagTileEntity::new, ModInnet.FLAG_BLOCK.get(), ModInnet.FLAG_BLUE_BLOCK.get(), ModInnet.FLAG_BROWN_BLOCK.get(), ModInnet.FLAG_CYAN_BLOCK.get(),ModInnet.FLAG_GRAY_BLOCK.get(),ModInnet.FLAG_GRAY_BLOCK.get(),ModInnet.FLAG_GREEN_BLOCK.get(),ModInnet.FLAG_LIGHT_BLUE_BLOCK.get(),ModInnet.FLAG_LIME_BLOCK.get(),ModInnet.FLAG_MAGENTA_BLOCk.get(),ModInnet.FLAG_ORANGE_BLOCK.get(),ModInnet.FLAG_PINK_BLOCK.get(),ModInnet.FLAG_PURPLE_BLOCK.get(),ModInnet.FLAG_RED_BLOCK.get(),ModInnet.FLAG_YELLOW_BLOCK.get()).build(null));

    //Machines
    public static RegistryObject<Block> FUEL_REFINERY_BLOCK = BLOCKS.register("fuel_refinery",() -> new FuelRefineryBlock.CustomBlock());
    public static RegistryObject<Block> BLAST_FURNACE_BLOCK = BLOCKS.register("blast_furnace",() -> new BlastingFurnaceBlock.CustomBlock());
    public static RegistryObject<Block> COMPRESSOR_BLOCK = BLOCKS.register("compressor",() -> new CompressorBlock.CustomBlock());
    public static RegistryObject<Block> COAL_GENERATOR_BLOCK = BLOCKS.register("coal_generator",() -> new CoalGeneratorBlock.CustomBlock());
    public static RegistryObject<Block> OXYGEN_LOADER_BLOCK = BLOCKS.register("oxygen_loader",() -> new OxygenLoaderBlock.CustomBlock());
    public static RegistryObject<Block> SOLAR_PANEL_BLOCK = BLOCKS.register("solar_panel",() -> new SolarPanelBlock.CustomBlock());
    public static RegistryObject<Block> NASA_WORKBENCH_BLOCK = BLOCKS.register("nasa_workbench",() -> new NASAWorkbenchBlock.CustomBlock());
    public static RegistryObject<Block> OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK = BLOCKS.register("oxygen_bubble_distributor",() -> new OxygenBubbleDistributorBlock.CustomBlock());
    public static RegistryObject<Block> WATER_PUMP_BLOCK = BLOCKS.register("water_pump",() -> new WaterPump(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).notSolid().hardnessAndResistance(5f, 1f).setLightLevel(s -> 0).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()));


    //Block Item
    public static final RegistryObject<BlockItem> NASA_WORKBENCH_ITEM = ITEMS.register("nasa_workbench", () -> new BlockItem(ModInnet.NASA_WORKBENCH_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> SOLAR_PANEL_ITEM = ITEMS.register("solar_panel", () -> new BlockItem(ModInnet.SOLAR_PANEL_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COAL_GENERATOR_ITEM = ITEMS.register("coal_generator", () -> new BlockItem(ModInnet.COAL_GENERATOR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> BLAST_FURNACE_ITEM = ITEMS.register("blast_furnace", () -> new BlockItem(ModInnet.BLAST_FURNACE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COMPRESSOR_ITEM = ITEMS.register("compressor", () -> new BlockItem(ModInnet.COMPRESSOR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> FUEL_REFINERY_ITEM = ITEMS.register("fuel_refinery", () -> new BlockItem(ModInnet.FUEL_REFINERY_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> OXYGEN_LOADER_ITEM = ITEMS.register("oxygen_loader", () -> new BlockItem(ModInnet.OXYGEN_LOADER_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> OXYGEN_BUBBLE_DISTRIBUTOR_ITEM = ITEMS.register("oxygen_bubble_distributor", () -> new BlockItem(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> WATER_PUMP_ITEM = ITEMS.register("water_pump", () -> new BlockItem(ModInnet.WATER_PUMP_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_machines)));

    //Tools
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new SwordItem(SteelItemTier.ITEM_TIER,3,-2.4f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new ShovelItem(SteelItemTier.ITEM_TIER,1.5f,-3f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(SteelItemTier.ITEM_TIER,1,-2.8f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new AxeItem(SteelItemTier.ITEM_TIER,6,-3f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new HoeItem(SteelItemTier.ITEM_TIER,-2,0f,new Item.Properties().group(BossToolsItemGroups.tab_basics).isImmuneToFire()));

    //Fuel Fluid
    public static final RegistryObject<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", ()-> new FuelFluid.Flowing());
    public static final RegistryObject<FlowingFluid> FUEL_STILL = FLUIDS.register("fuel", ()-> new FuelFluid.Source());
    public static RegistryObject<FlowingFluidBlock> FUEL_BLOCK = BLOCKS.register("fuel",() -> new FlowingFluidBlock(ModInnet.FUEL_STILL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));
    public static final RegistryObject<Item> FUEL_BUCKET = ITEMS.register("fuel_bucket", () -> new BucketItem(ModInnet.FUEL_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(BossToolsItemGroups.tab_normal)));

    public static final ResourceLocation FLUID_VEHICLE_FUEL_TAG = new ResourceLocation("boss_tools", "vehicle_fuel");

    //Oil Fluid
    public static final RegistryObject<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", ()-> new OilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> OIL_STILL = FLUIDS.register("oil", ()-> new OilFluid.Source());
    public static RegistryObject<FlowingFluidBlock> OIL_BLOCK = BLOCKS.register("oil",() -> new FlowingFluidBlock(ModInnet.OIL_STILL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(ModInnet.OIL_STILL, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(BossToolsItemGroups.tab_normal)));

    public static final ResourceLocation OIL_FLUID_TAG = new ResourceLocation("boss_tools", "oil");

    //Item
    public static final RegistryObject<Item> COAL_TORCH_ITEM = ITEMS.register("coal_torch", () -> new CoalTorchItem(COAL_TORCH_BLOCK.get(), WALL_COAL_TORCH_BLOCK.get(),new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    //Lanterns
    public static final RegistryObject<BlockItem> COAL_LANTERN_ITEM = ITEMS.register("coal_lantern", () -> new BlockItem(ModInnet.COAL_LANTERN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_basics)));

    //Spawn Eggs
    public static final RegistryObject<ModSpawnEggs> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg", () -> new ModSpawnEggs(ALIEN, -13382401, -11650781, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> PYGRO_SPAWN_EGG = ITEMS.register("pygro_spawn_egg",() -> new ModSpawnEggs(PYGRO, -3381760, -6750208, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> MOGLER_SPAWN_EGG = ITEMS.register("mogler_spawn_egg",() -> new ModSpawnEggs(MOGLER, -13312, -3407872, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> ALIEN_ZOMBIE_SPAWN_EGG = ITEMS.register("alien_zombie_spawn_egg",() -> new ModSpawnEggs(ALIEN_ZOMBIE, -14804199, -16740159, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ModSpawnEggs> STAR_CRAWLER_SPAWN_EGG = ITEMS.register("star_crawler_spawn_egg",() -> new ModSpawnEggs(STAR_CRAWLER, -13421773, -16724788, new Item.Properties().group(BossToolsItemGroups.tab_spawn_eggs)));

    //Generel Items
    public static final RegistryObject<Item> CHESE = ITEMS.register("chesse", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_normal).food((new Food.Builder()).hunger(4).saturation(3f).build())));
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new HammerItem(new Item.Properties().group(BossToolsItemGroups.tab_basics).maxDamage(9).setNoRepair()));
    public static final RegistryObject<Item> IRON_STICK = ITEMS.register("iron_stick", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_GEAR = ITEMS.register("oxygen_gear", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_FRAME = ITEMS.register("engine_frame", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_FAN = ITEMS.register("engine_fan", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ROCKET_NOSE_CONE = ITEMS.register("rocket_nose_cone", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DIAMOND_ENGINE = ITEMS.register("diamond_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> IRON_ENGINE = ITEMS.register("iron_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_ENGINE = ITEMS.register("golden_engine", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> IRON_TANK = ITEMS.register("iron_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_TANK = ITEMS.register("golden_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DIAMOND_TANK = ITEMS.register("diamond_tank", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ROCKET_FIN = ITEMS.register("rocket_fin", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_basics)));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DESH_INGOT = ITEMS.register("desh_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> SILICON_INGOT = ITEMS.register("silicon_ingot", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DESH_PLATE = ITEMS.register("desh_plate", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> COMPRESSED_STEEL = ITEMS.register("compressed_steel", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_DESH = ITEMS.register("compressed_desh", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_SILICON = ITEMS.register("compressed_silicon", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DESH_NUGGET = ITEMS.register("desh_nugget", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> SILICON_NUGGET = ITEMS.register("silicon_nugget", () -> new Item(new Item.Properties().group(BossToolsItemGroups.tab_materials)));

    //Generel Blocks
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> DESH_BLOCK = BLOCKS.register("desh_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> SILICON_BLOCK = BLOCKS.register("silicon_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> IRON_PLATING_BLOCK = BLOCKS.register("iron_plating_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> RUSTED_IRON_PILLAR_BLOCK = BLOCKS.register("rusted_iron_pillar_block", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> RUSTED_IRON_PLATING_BLOCK = BLOCKS.register("rusted_iron_plating_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> BLUE_IRON_PLATING_BLOCK = BLOCKS.register("blue_iron_plating_block", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setLightLevel(state -> 15).setRequiresTool()));
    public static final RegistryObject<Block> IRON_MARK_BLOCK = BLOCKS.register("iron_mark_block", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 2.5f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> CRACKED_MOON_BRICKS = BLOCKS.register("cracked_moon_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> CRACKED_MOON_BRICK_SALB = BLOCKS.register("cracked_moon_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> CRACKED_MOON_BRICK_STAIRS = BLOCKS.register("cracked_moon_brick_stairs", () -> new StairsBlock(() -> ModInnet.CRACKED_MOON_BRICKS.get().getDefaultState(), AbstractBlock.Properties.from(ModInnet.CRACKED_MOON_BRICKS.get())));
    public static final RegistryObject<Block> MOON_BRICKS = BLOCKS.register("moon_bricks", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MOON_BRICK_SLAB = BLOCKS.register("moon_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MOON_BRICK_STAIRS = BLOCKS.register("moon_brick_stairs", () -> new StairsBlock(() -> ModInnet.CRACKED_MOON_BRICKS.get().getDefaultState(), AbstractBlock.Properties.from(ModInnet.MOON_BRICKS.get())));
    public static final RegistryObject<Block> SKY_STONE = BLOCKS.register("sky_stone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MARS_STONE = BLOCKS.register("mars_stone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MERCURY_STONE = BLOCKS.register("mercury_stone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MOON_STONE = BLOCKS.register("moon_stone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MERCURY_COBBLESTONE = BLOCKS.register("mercury_cobblestone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> VENUS_SANDSTONE = BLOCKS.register("venus_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> VENUS_STONE = BLOCKS.register("venus_stone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> INFERNAL_SPIRE_BLOCK = BLOCKS.register("infernal_spire_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 1f).harvestTool(ToolType.PICKAXE).setRequiresTool()));


    //Faling Block
    public static final RegistryObject<Block> MOON_SAND = BLOCKS.register("moon_sand", () -> new FallingBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.GRAY).sound(SoundType.SAND).hardnessAndResistance(0.5f, 0.5f).harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Block> MARS_SAND = BLOCKS.register("mars_sand", () -> new FallingBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.ORANGE_TERRACOTTA).sound(SoundType.SAND).hardnessAndResistance(0.5f, 0.5f).harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Block> VENUS_SAND = BLOCKS.register("venus_sand", () -> new FallingBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.ORANGE_TERRACOTTA).sound(SoundType.SAND).hardnessAndResistance(0.5f, 0.5f).harvestTool(ToolType.SHOVEL)));


    //ORES
    public static final RegistryObject<Block> MOON_CHESSE_ORE = BLOCKS.register("moon_cheese_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MOON_DESH_ORE = BLOCKS.register("moon_desh_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MOON_GLOWSTONE_ORE = BLOCKS.register("moon_glowstone_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setLightLevel(state -> 15).setRequiresTool()));
    public static final RegistryObject<Block> MOON_IRON_ORE = BLOCKS.register("moon_iron_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MARS_ICE_SHARD_ORE = BLOCKS.register("mars_ice_shard_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MARS_IRON_ORE = BLOCKS.register("mars_iron_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MARS_DIAMOND_ORE = BLOCKS.register("mars_diamond_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MARS_SILICON_ORE = BLOCKS.register("mars_silicon_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> MERCURY_IRON_ORE = BLOCKS.register("mercury_iron_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> VENUS_COAL_ORE = BLOCKS.register("venus_coal_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> VENUS_DIAMOND_ORE = BLOCKS.register("venus_diamond_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> VENUS_GOLD_ORE = BLOCKS.register("venus_gold_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE).setRequiresTool()));

    //Generel Block Items
    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = ITEMS.register("steel_block", () -> new BlockItem(ModInnet.STEEL_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> DESH_BLOCK_ITEM = ITEMS.register("desh_block", () -> new BlockItem(ModInnet.DESH_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> SILICON_BLOCK_ITEM = ITEMS.register("silicon_block", () -> new BlockItem(ModInnet.SILICON_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> IRON_PLATING_BLOCK_ITEM = ITEMS.register("iron_plating_block", () -> new BlockItem(ModInnet.IRON_PLATING_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_PILLAR_BLOCK_ITEM = ITEMS.register("rusted_iron_pillar_block", () -> new BlockItem(ModInnet.RUSTED_IRON_PILLAR_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_PLATING_BLOCK_ITEM = ITEMS.register("rusted_iron_plating_block", () -> new BlockItem(ModInnet.RUSTED_IRON_PLATING_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> BLUE_IRON_PLATING_BLOCK_ITEM = ITEMS.register("blue_iron_plating_block", () -> new BlockItem(ModInnet.BLUE_IRON_PLATING_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> INFERNAL_SPIRE_BLOCK_ITEM = ITEMS.register("infernal_spire_block", () -> new BlockItem(ModInnet.INFERNAL_SPIRE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> IRON_MARK_BLOCK_ITEM = ITEMS.register("iron_mark_block", () -> new BlockItem(ModInnet.IRON_MARK_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_STONE_ITEM = ITEMS.register("mars_stone", () -> new BlockItem(ModInnet.MARS_STONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MERCURY_STONE_ITEM = ITEMS.register("mercury_stone", () -> new BlockItem(ModInnet.MERCURY_STONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> SKY_STONE_ITEM = ITEMS.register("sky_stone", () -> new BlockItem(ModInnet.SKY_STONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_BRICKS_ITEM = ITEMS.register("moon_bricks", () -> new BlockItem(ModInnet.MOON_BRICKS.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_BRICK_SLAB_ITEM = ITEMS.register("moon_brick_slab", () -> new BlockItem(ModInnet.MOON_BRICK_SLAB.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_BRICK_STAIRS_ITEM = ITEMS.register("moon_brick_stairs", () -> new BlockItem(ModInnet.MOON_BRICK_STAIRS.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> CRACKED_MOON_BRICKS_ITEM = ITEMS.register("cracked_moon_bricks", () -> new BlockItem(ModInnet.CRACKED_MOON_BRICKS.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> CRACKED_MOON_BRICK_SLAB_ITEM = ITEMS.register("cracked_moon_brick_slab", () -> new BlockItem(ModInnet.CRACKED_MOON_BRICK_SALB.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> CRACKED_MOON_BRICK_STAIRS_ITEM = ITEMS.register("cracked_moon_brick_stairs", () -> new BlockItem(ModInnet.CRACKED_MOON_BRICK_STAIRS.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_STONE_ITEM = ITEMS.register("moon_stone", () -> new BlockItem(ModInnet.MOON_STONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MERCURY_COBBLESTONE_ITEM = ITEMS.register("mercury_cobblestone", () -> new BlockItem(ModInnet.MERCURY_COBBLESTONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_SANDSTONE_ITEM = ITEMS.register("venus_sandstone", () -> new BlockItem(ModInnet.VENUS_SANDSTONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_STONE_ITEM = ITEMS.register("venus_stone", () -> new BlockItem(ModInnet.VENUS_STONE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));

    public static final RegistryObject<BlockItem> ROCKET_LAUNCH_PAD_ITEM = ITEMS.register("rocket_launch_pad", () -> new BlockItem(ModInnet.ROCKET_LAUNCH_PAD.get(), new Item.Properties().group(BossToolsItemGroups.tab_normal)));

    public static final RegistryObject<BlockItem> MOON_SAND_ITEM = ITEMS.register("moon_sand", () -> new BlockItem(ModInnet.MOON_SAND.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_SAND_ITEM = ITEMS.register("mars_sand", () -> new BlockItem(ModInnet.MARS_SAND.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_SAND_ITEM = ITEMS.register("venus_sand", () -> new BlockItem(ModInnet.VENUS_SAND.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));

    public static final RegistryObject<BlockItem> MOON_CHESSE_ORE_ITEM = ITEMS.register("moon_cheese_ore", () -> new BlockItem(ModInnet.MOON_CHESSE_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_DESH_ORE_ITEM = ITEMS.register("moon_desh_ore", () -> new BlockItem(ModInnet.MOON_DESH_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_GLOWSTONE_ITEM = ITEMS.register("moon_glowstone_ore", () -> new BlockItem(ModInnet.MOON_GLOWSTONE_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_IRON_ORE_ITEM = ITEMS.register("moon_iron_ore", () -> new BlockItem(ModInnet.MOON_IRON_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_ICE_SHARD_ORE_ITEM = ITEMS.register("mars_ice_shard_ore", () -> new BlockItem(ModInnet.MARS_ICE_SHARD_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_IRON_ORE_ITEM = ITEMS.register("mars_iron_ore", () -> new BlockItem(ModInnet.MARS_IRON_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_DIAMOND_ORE_ITEM = ITEMS.register("mars_diamond_ore", () -> new BlockItem(ModInnet.MARS_DIAMOND_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_SILICON_ORE_ITEM = ITEMS.register("mars_silicon_ore", () -> new BlockItem(ModInnet.MARS_SILICON_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MERCURY_IRON_ORE_ITEM = ITEMS.register("mercury_iron_ore", () -> new BlockItem(ModInnet.MERCURY_IRON_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_COAL_ORE_ITEM = ITEMS.register("venus_coal_ore", () -> new BlockItem(ModInnet.VENUS_COAL_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_DIAMOND_ORE_ITEM = ITEMS.register("venus_diamond_ore", () -> new BlockItem(ModInnet.VENUS_DIAMOND_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_GOLD_ORE_ITEM = ITEMS.register("venus_gold_ore", () -> new BlockItem(ModInnet.VENUS_GOLD_ORE.get(), new Item.Properties().group(BossToolsItemGroups.tab_blocks)));

    //Effects
    public static final RegistryObject<Effect> OXYGEN_EFFECT = EFFECTS.register("oxygen_bubble_effect", () -> new OxygenEffect(EffectType.BENEFICIAL,3035801));

    //Space Suit Items
    public static final RegistryObject<Item> OXYGEN_MASK = ITEMS.register("oxygen_mask", () -> SpaceSuit.OXYGEN_MASK);
    public static final RegistryObject<Item> SPACE_SUIT = ITEMS.register("space_suit", () -> SpaceSuit.SPACE_SUIT);
    public static final RegistryObject<Item> SPACE_PANTS = ITEMS.register("space_pants", () -> SpaceSuit.SPACE_PANTS);
    public static final RegistryObject<Item> SPACE_BOOTS = ITEMS.register("space_boots", () -> SpaceSuit.SPACE_BOOTS);


    //Netherite Space Suit Items
    public static final RegistryObject<Item> NETHERITE_OXYGEN_MASK = ITEMS.register("netherite_oxygen_mask", () -> NetheriteSpaceSuit.NETHERITE_OXYGEN_MASK);
    public static final RegistryObject<Item> NETHERITE_SPACE_SUIT = ITEMS.register("netherite_space_suit", () -> NetheriteSpaceSuit.NETHERITE_SPACE_SUIT);
    public static final RegistryObject<Item> NETHERITE_SPACE_PANTS = ITEMS.register("netherite_space_pants", () -> NetheriteSpaceSuit.NETHERITE_SPACE_PANTS);
    public static final RegistryObject<Item> NETHERITE_SPACE_BOOTS = ITEMS.register("netherite_space_boots", () -> NetheriteSpaceSuit.NETHERITE_SPACE_BOOTS);

    //Flag Items
    public static final RegistryObject<Item> FLAG_ITEM = ITEMS.register("flag", () -> new TallBlockItem(FLAG_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_BLUE_ITEM = ITEMS.register("flag_blue", () -> new TallBlockItem(FLAG_BLUE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_BROWN_ITEM = ITEMS.register("flag_brown", () -> new TallBlockItem(FLAG_BROWN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_CYAN_ITEM = ITEMS.register("flag_cyan", () -> new TallBlockItem(FLAG_CYAN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_GRAY_ITEM = ITEMS.register("flag_gray", () -> new TallBlockItem(FLAG_GRAY_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_GREEN_ITEM = ITEMS.register("flag_green", () -> new TallBlockItem(FLAG_GREEN_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_LIGHT_BLUE_ITEM = ITEMS.register("flag_light_blue", () -> new TallBlockItem(FLAG_LIGHT_BLUE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_LIME_ITEM = ITEMS.register("flag_lime", () -> new TallBlockItem(FLAG_LIME_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_MAGENTA_ITEM = ITEMS.register("flag_magenta", () -> new TallBlockItem(FLAG_MAGENTA_BLOCk.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_ORANGE_ITEM = ITEMS.register("flag_orange", () -> new TallBlockItem(FLAG_ORANGE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_PINK_ITEM = ITEMS.register("flag_pink", () -> new TallBlockItem(FLAG_PINK_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_PURPLE_ITEM = ITEMS.register("flag_purple", () -> new TallBlockItem(FLAG_PURPLE_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_RED_ITEM = ITEMS.register("flag_red", () -> new TallBlockItem(FLAG_RED_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_YELLOW_ITEM = ITEMS.register("flag_yellow", () -> new TallBlockItem(FLAG_YELLOW_BLOCK.get(), new Item.Properties().group(BossToolsItemGroups.tab_flags)));

    //GUIS
    public static final RegistryObject<ContainerType<RocketGui.GuiContainer>> ROCKET_GUI = GUIS.register("rocket_gui", () -> new ContainerType<>(new RocketGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<BlastFurnaceGui.GuiContainer>> BLAST_FURNACE_GUI = GUIS.register("blast_furnace_gui", () -> new ContainerType<>(new BlastFurnaceGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<CompressorGui.GuiContainer>> COMPRESSOR_GUI = GUIS.register("compressor_gui", () -> new ContainerType<>(new CompressorGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<FuelRefineryGui.GuiContainer>> FUEL_REFINERY_GUI = GUIS.register("fuel_refinery_gui", () -> new ContainerType<>(new FuelRefineryGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<CoalGeneratorGui.GuiContainer>> COAL_GENERATOR_GUI = GUIS.register("coal_generator_gui", () -> new ContainerType<>(new CoalGeneratorGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<NasaWorkbenchGui.GuiContainer>> NASA_WORKBENCH_GUI = GUIS.register("nasa_workbench_gui", () -> new ContainerType<>(new NasaWorkbenchGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<OxygenLoaderGui.GuiContainer>> OXYGEN_LOADER_GUI = GUIS.register("oxygen_loader_gui", () -> new ContainerType<>(new OxygenLoaderGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<SolarPanelGui.GuiContainer>> SOLAR_PANEL_GUI = GUIS.register("solar_panel_gui", () -> new ContainerType<>(new SolarPanelGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<WaterPumpGui.GuiContainer>> WATER_PUMP_GUI = GUIS.register("water_pump_gui", () -> new ContainerType<>(new WaterPumpGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<OxygenBubbleDistributorGui.GuiContainer>> OXYGEN_BUBBLE_DISTRIBUTOR_GUI = GUIS.register("oxygen_bubble_distributor_gui", () -> new ContainerType<>(new OxygenBubbleDistributorGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<LanderGui.GuiContainer>> LANDER_GUI = GUIS.register("lander_gui", () -> new ContainerType<>(new LanderGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<RoverGui.GuiContainer>> ROVER_GUI = GUIS.register("rover_gui", () -> new ContainerType<>(new RoverGui.GuiContainerFactory()));
    public static final RegistryObject<ContainerType<PlanetSelectionGui.GuiContainer>> PLANET_SELECTION_GUI = GUIS.register("planet_selection_gui", () -> new ContainerType<>(new PlanetSelectionGui.GuiContainerFactory()));


    //Particle
    public static final RegistryObject<ParticleType<BasicParticleType>> VENUS_RAIN_PARTICLE = PARTICLES.register("venus_rain", () -> new BasicParticleType(true));
    public static final RegistryObject<ParticleType<BasicParticleType>> LARGE_FLAME_PARTICLE = PARTICLES.register("large_flame", () -> new BasicParticleType(true));
    public static final RegistryObject<ParticleType<BasicParticleType>> SMOKE_PARTICLE = PARTICLES.register("smoke", () -> new BasicParticleType(true));


    //Recpies
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BossToolsMod.ModId);
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_BLASTING = RECIPE_SERIALIZERS.register("blasting", () -> new BlastingRecipeSerializer());
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_COMPRESSING = RECIPE_SERIALIZERS.register("compressing", () -> new CompressingRecipeSerializer());
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_GENERATING = RECIPE_SERIALIZERS.register("generating", () -> new GeneratingRecipeSerializer());
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_OXYGENLOADER = RECIPE_SERIALIZERS.register("oxygenloader", () -> new OxygenLoaderRecipeSerializer());
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_OXYGENBUBBLEDISTRIBUTOR = RECIPE_SERIALIZERS.register("oxygenbubbledistributor", () -> new OxygenBubbleDistributorRecipeSerializer());
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_WORKBENCHING = RECIPE_SERIALIZERS.register("workbenching", () -> new WorkbenchingRecipeSerializer());
    public static final RegistryObject<RecipeSerializer<?>> RECIPE_SERIALIZER_FUELREFINING = RECIPE_SERIALIZERS.register("fuelrefining", () -> new FuelRefiningRecipeSerializer());
    
    //RocketParts
    public static final IForgeRegistry<RocketPart> ROCKET_PARTS_REGISTRY = new RegistryBuilder<RocketPart>().setType(RocketPart.class).setName(new ResourceLocation(BossToolsMod.ModId, "rocket_part")).create();
    public static final DeferredRegister<RocketPart> ROCKET_PARTS = DeferredRegister.create(ROCKET_PARTS_REGISTRY, BossToolsMod.ModId);

    public static final RegistryObject<RocketPart> ROCKET_PART_EMPTY = ROCKET_PARTS.register("emtpy", () -> RocketPart.EMPTY);
    public static final RegistryObject<RocketPart> ROCKET_PART_NOSE = ROCKET_PARTS.register("nose", () -> new RocketPart(1));
	public static final RegistryObject<RocketPart> ROCKET_PART_BODY = ROCKET_PARTS.register("body", () -> new RocketPart(6));
	public static final RegistryObject<RocketPart> ROCKET_PART_TANK = ROCKET_PARTS.register("tank", () -> new RocketPart(2));
	public static final RegistryObject<RocketPart> ROCKET_PART_FIN_LEFT = ROCKET_PARTS.register("fin_left", () -> new RocketPart(2));
	public static final RegistryObject<RocketPart> ROCKET_PART_FIN_RIGHT = ROCKET_PARTS.register("fin_right", () -> new RocketPart(2));
	public static final RegistryObject<RocketPart> ROCKET_PART_ENGINE = ROCKET_PARTS.register("engine", () -> new RocketPart(1));

    //DamgeSources
    public static final DamageSource DAMAGE_SOURCE_OXYGEN = new DamageSource("oxygen").setDamageBypassesArmor();
    public static final DamageSource DAMAGE_SOURCE_ACID_RAIN = new DamageSource("venus.acid").setDamageBypassesArmor();

    //ICE SPIKE
    public static ConfiguredFeature<?, ?> ICE_SPIKE;
    public static MarsIceSpikeFeature MARS_ICE_SPIKE;

    //VENUS DELTAS
    public static ConfiguredFeature<?, ?> DELTAS;
    public static ConfiguredFeature<?, ?> DELTAS2;
    public static VenusDeltas VENUS_DELTAS;

    //Desert Builder
    public static SurfaceBuilder<SurfaceBuilderConfig> DESERT_SURFACE_BUILDER;
    
    @SubscribeEvent
    public static void RegistryFeature(RegistryEvent.Register<Feature<?>> feature) {
        MARS_ICE_SPIKE = new MarsIceSpikeFeature(NoFeatureConfig.field_236558_a_);
        MARS_ICE_SPIKE.setRegistryName(BossToolsMod.ModId, "mars_ice_spike");
        feature.getRegistry().register(MARS_ICE_SPIKE);

        //VENUS DELTAS
        VENUS_DELTAS = new VenusDeltas(ColumnConfig.CODEC);
        VENUS_DELTAS.setRegistryName(BossToolsMod.ModId, "venus_deltas");
        feature.getRegistry().register(VENUS_DELTAS);
    }

    @SubscribeEvent
    public static void RegistrySurfaceBuilder(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        //Desert Builder
        DESERT_SURFACE_BUILDER = new DesertSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_);
        DESERT_SURFACE_BUILDER.setRegistryName(BossToolsMod.ModId, "desert_surface_builder");
        event.getRegistry().register(DESERT_SURFACE_BUILDER);
    }

    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerFeature(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            STStructures.setupStructures();
            STStructures2.setupStructures();
            STConfiguredStructures.registerConfiguredStructures();
        });
    }

    public static void biomeModification(final BiomeLoadingEvent event) {
        RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if (event.getName().equals(new ResourceLocation(BossToolsMod.ModId ,"moon")) && Config.AlienVillageStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.ALIEN_VILLAGE);
        }
        if (event.getName().equals(new ResourceLocation("plains")) && Config.MeteorStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR);
        }
        if (event.getName().equals(new ResourceLocation("snowy_tundra")) && Config.MeteorStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR);
        }
        if (event.getName().equals(new ResourceLocation("forest")) && Config.MeteorStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR);
        }
        if (event.getName().equals(new ResourceLocation("desert")) && Config.MeteorStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.METEOR);
        }
        if (event.getName().equals(new ResourceLocation(BossToolsMod.ModId ,"venus")) && Config.VenusBulletStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_BULLET);
        }
        if (event.getName().equals(new ResourceLocation(BossToolsMod.ModId ,"venus")) && Config.VenusTowerStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.VENUS_TOWER);
        }
        if (event.getName().equals(new ResourceLocation(BossToolsMod.ModId ,"venus")) && Config.CrimsonVillageStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.CRIMSON);
        }
        if (event.getName().equals(new ResourceLocation("ocean")) && Config.OILWellStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL);
        }
        if (event.getName().equals(new ResourceLocation("deep_cold_ocean")) && Config.OILWellStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL);
        }
        if (event.getName().equals(new ResourceLocation("deep_ocean")) && Config.OILWellStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL);
        }
        if (event.getName().equals(new ResourceLocation("lukewarm_ocean")) && Config.OILWellStructure) {
            event.getGeneration().getStructures().add(() -> STConfiguredStructures.OIL);
        }
    }

    private static Method GETCODEC_METHOD;

    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {

            }
            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator || serverWorld.getDimensionType().equals(World.OVERWORLD)){
                return;
            }
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.putIfAbsent(STStructures.ALIEN_VILLAGE.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.ALIEN_VILLAGE.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;

            Map<Structure<?>, StructureSeparationSettings> tempMap1 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap1.putIfAbsent(STStructures.METEOR.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.METEOR.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap1;

            Map<Structure<?>, StructureSeparationSettings> tempMap2 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap2.putIfAbsent(STStructures2.VENUS_BULLET.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures2.VENUS_BULLET.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap2;

            Map<Structure<?>, StructureSeparationSettings> tempMap3 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap3.putIfAbsent(STStructures2.VENUS_TOWER.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures2.VENUS_TOWER.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap3;

            Map<Structure<?>, StructureSeparationSettings> tempMap4 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap4.putIfAbsent(STStructures.OIL.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.OIL.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap4;

            Map<Structure<?>, StructureSeparationSettings> tempMap5 = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap5.putIfAbsent(STStructures2.CRIMSON.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures2.CRIMSON.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap5;
        }
    }

    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN.get(), AlienEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) PYGRO.get(), PygroEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) MOGLER.get(), MoglerEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ALIEN_ZOMBIE.get(), AlienZombieEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) STAR_CRAWLER.get(), StarCrawlerEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) TIER_1_ROCKET.get(), RocketTier1Entity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) TIER_2_ROCKET.get(), RocketTier2Entity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) TIER_3_ROCKET.get(), RocketTier3Entity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) LANDER.get(), LanderEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put((EntityType<? extends CreatureEntity>) ROVER.get(), RoverEntity.setCustomAttributes().create());
        });
        event.enqueueWork(() -> {

            BossToolsRecipeTypes.init();
            CapabilityOxygen.register();

            ICE_SPIKE = registerFeature("mars_ice_spike", ModInnet.MARS_ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
            //Venus Deltas
            DELTAS = registerFeature("venus_deltas_1", ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 8))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(4))));
            //Venus Deltas2
            DELTAS2 = registerFeature("venus_deltas_2", ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 6))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
        });
    }

    public static void biomesLoading(final BiomeLoadingEvent event) {
        if (event.getName().getPath().equals(BiomeRegistry.mars_ice_spike.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ICE_SPIKE);
        }
        //Venus Deltas
        if (event.getName().getPath().equals(BiomeRegistry.infernal_venus_barrens.getRegistryName().getPath())){
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS);
            event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, DELTAS2);
        }
        //Desert Structure Builder
        if (event.getName().getPath().equals(BiomeRegistry.venus.getRegistryName().getPath())){
            event.getGeneration().withSurfaceBuilder(ModInnet.DESERT_SURFACE_BUILDER.func_242929_a(new SurfaceBuilderConfig(ModInnet.VENUS_SAND.get().getDefaultState(), ModInnet.VENUS_SAND.get().getDefaultState(), ModInnet.VENUS_SANDSTONE.get().getDefaultState())));
        }
    }
}