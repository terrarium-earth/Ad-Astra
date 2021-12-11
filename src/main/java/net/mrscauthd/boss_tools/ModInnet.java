package net.mrscauthd.boss_tools;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;
import net.mrscauthd.boss_tools.armor.SpaceSuit;
import net.mrscauthd.boss_tools.block.*;
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
import net.mrscauthd.boss_tools.machines.tile.BlastingFurnaceBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.CoalGeneratorBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.CompressorBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.FuelRefineryBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.NASAWorkbenchBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.OxygenBubbleDistributorBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.OxygenLoaderBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.SolarPanelBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.WaterPumpBlockEntity;
import net.mrscauthd.boss_tools.entity.pygro.PygroEntity;
import net.mrscauthd.boss_tools.flag.*;
import net.mrscauthd.boss_tools.fluid.FuelFluid;
import net.mrscauthd.boss_tools.armor.NetheriteSpaceSuit;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.itemgroup.BossToolsItemGroups;
import net.mrscauthd.boss_tools.entity.pygro.PygroMobsSensor;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInnet {

    //Todo: Add Blocks to need Tool Tag

    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, BossToolsMod.ModId);

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITYS = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BossToolsMod.ModId);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BossToolsMod.ModId);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, BossToolsMod.ModId);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BossToolsMod.ModId);

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BossToolsMod.ModId);

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BossToolsMod.ModId);

    public static final DeferredRegister<MenuType<?>> GUIS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BossToolsMod.ModId);

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BossToolsMod.ModId);

    //Vehicle Items
    public static final RegistryObject<Item> TIER_1_ROCKET_ITEM = ITEMS.register("rocket_t1", () -> new Tier1RocketItem(new Item.Properties().tab(BossToolsItemGroups.tab_normal).stacksTo(1)));
    public static final RegistryObject<Item> TIER_2_ROCKET_ITEM = ITEMS.register("rocket_t2", () -> new Tier2RocketItem(new Item.Properties().tab(BossToolsItemGroups.tab_normal).stacksTo(1)));
    public static final RegistryObject<Item> TIER_3_ROCKET_ITEM = ITEMS.register("rocket_t3", () -> new Tier3RocketItem(new Item.Properties().tab(BossToolsItemGroups.tab_normal).stacksTo(1)));
    public static final RegistryObject<Item> ROVER_ITEM = ITEMS.register("rover", () -> new RoverItem(new Item.Properties().tab(BossToolsItemGroups.tab_normal).stacksTo(1)));

    //Entitys
    public static RegistryObject<EntityType<AlienEntity>> ALIEN = ENTITYS.register("alien", () -> EntityType.Builder.of(AlienEntity::new, MobCategory.CREATURE).sized(0.75f, 2.5f).build(new ResourceLocation("boss_tools", "alien").toString()));
    public static RegistryObject<EntityType<AlienZombieEntity>> ALIEN_ZOMBIE = ENTITYS.register("alien_zombie", () -> EntityType.Builder.of(AlienZombieEntity::new, MobCategory.MONSTER).sized(0.6f, 2.4f).build(new ResourceLocation("boss_tools", "alien_zombie").toString()));
    public static RegistryObject<EntityType<StarCrawlerEntity>> STAR_CRAWLER = ENTITYS.register("star_crawler", () -> EntityType.Builder.of(StarCrawlerEntity::new, MobCategory.MONSTER).sized(1.3f, 1f).build(new ResourceLocation("boss_tools", "star_crawler").toString()));
    public static RegistryObject<EntityType<PygroEntity>> PYGRO = ENTITYS.register("pygro", () -> EntityType.Builder.of(PygroEntity::new, MobCategory.MONSTER).fireImmune().sized(0.6f, 1.8f).build(new ResourceLocation("boss_tools", "pygro").toString()));
    public static RegistryObject<EntityType<MoglerEntity>> MOGLER = ENTITYS.register("mogler", () -> EntityType.Builder.of(MoglerEntity::new, MobCategory.MONSTER).sized(1.4f, 1.4f).build(new ResourceLocation("boss_tools", "mogler").toString()));

    //VEHICLES
    public static RegistryObject<EntityType<RocketTier1Entity>> TIER_1_ROCKET = ENTITYS.register("rocket_t1", () -> EntityType.Builder.of(RocketTier1Entity::new, MobCategory.MISC).sized(1.1f, 4.4f).fireImmune().build(new ResourceLocation("boss_tools", "rocket_t1").toString()));
    public static RegistryObject<EntityType<RocketTier2Entity>> TIER_2_ROCKET = ENTITYS.register("rocket_t2", () -> EntityType.Builder.of(RocketTier2Entity::new, MobCategory.MISC).sized(1.1f, 4.6f).fireImmune().build(new ResourceLocation("boss_tools", "rocket_t2").toString()));
    public static RegistryObject<EntityType<RocketTier3Entity>> TIER_3_ROCKET = ENTITYS.register("rocket_t3", () -> EntityType.Builder.of(RocketTier3Entity::new, MobCategory.MISC).sized(1.1f, 4.8f).fireImmune().build(new ResourceLocation("boss_tools", "rocket_t3").toString()));
    public static RegistryObject<EntityType<LanderEntity>> LANDER = ENTITYS.register("lander", () -> EntityType.Builder.of(LanderEntity::new, MobCategory.MISC).sized(0.6f, 2.0f).fireImmune().build(new ResourceLocation("boss_tools", "lander").toString()));
    public static RegistryObject<EntityType<RoverEntity>> ROVER = ENTITYS.register("rover", () -> EntityType.Builder.of(RoverEntity::new, MobCategory.MISC).sized(2.5f, 1.0f).fireImmune().build(new ResourceLocation("boss_tools", "rover").toString()));

    //Rocket Launch Pad
    public static final RegistryObject<Block> ROCKET_LAUNCH_PAD = BLOCKS.register("rocket_launch_pad", () -> new RocketLaunchPad(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));


    //Alien Spit Entity
    public static RegistryObject<EntityType<? extends AlienSpitEntity>> ALIEN_SPIT_ENTITY = ENTITYS.register("alien_spit_entity", () -> EntityType.Builder.<AlienSpitEntity>of(AlienSpitEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation("boss_tools", "alien_spit_entity").toString()));

    //pygro
    public static final DeferredRegister<SensorType<?>> SENSOR = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, BossToolsMod.ModId);
    public static final RegistryObject<SensorType<PygroMobsSensor>> PYGRO_SENSOR = SENSOR.register("pygro_sensor", ()->new SensorType<>(PygroMobsSensor::new));

    //Sounds
    public static RegistryObject<SoundEvent> ROCKET_SOUND = SOUNDS.register("rocket_fly",() -> new SoundEvent(new ResourceLocation(BossToolsMod.ModId, "rocket_fly")));

    //Blocks
    public static RegistryObject<Block> COAL_TORCH_BLOCK = BLOCKS.register("coal_torch",() -> new CoalTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD)));
    public static RegistryObject<Block> WALL_COAL_TORCH_BLOCK = BLOCKS.register("wall_coal_torch",() -> new WallCoalTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD).lootFrom(COAL_TORCH_BLOCK::get)));
    public static RegistryObject<Block> COAL_LANTERN_BLOCK = BLOCKS.register("coal_lantern",() -> new CoalLanternBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.LANTERN).noOcclusion()));


    //Flag Blocks
    public static RegistryObject<Block> FLAG_BLOCK = BLOCKS.register("flag",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_BLUE_BLOCK = BLOCKS.register("flag_blue",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_BROWN_BLOCK = BLOCKS.register("flag_brown",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_CYAN_BLOCK = BLOCKS.register("flag_cyan",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_GRAY_BLOCK = BLOCKS.register("flag_gray",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_GREEN_BLOCK = BLOCKS.register("flag_green",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_LIGHT_BLUE_BLOCK = BLOCKS.register("flag_light_blue",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_LIME_BLOCK = BLOCKS.register("flag_lime",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_MAGENTA_BLOCk = BLOCKS.register("flag_magenta",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_ORANGE_BLOCK = BLOCKS.register("flag_orange",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_PINK_BLOCK = BLOCKS.register("flag_pink",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_PURPLE_BLOCK = BLOCKS.register("flag_purple",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_RED_BLOCK = BLOCKS.register("flag_red",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> FLAG_YELLOW_BLOCK = BLOCKS.register("flag_yellow",() -> new FlagBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).sound(SoundType.STONE).noOcclusion().lightLevel(s -> 1).isRedstoneConductor((bs, br, bp) -> false)));

    //Tile Entity RegistryObject
    public static final RegistryObject<BlockEntityType<?>> FUEL_REFINERY = TILE_ENTITYS.register("fuel_refinery", () -> BlockEntityType.Builder.of(FuelRefineryBlockEntity::new,ModInnet.FUEL_REFINERY_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> BLAST_FURNACE = TILE_ENTITYS.register("blast_furnace", () -> BlockEntityType.Builder.of(BlastingFurnaceBlockEntity::new,ModInnet.BLAST_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> COMPRESSOR = TILE_ENTITYS.register("compressor", () -> BlockEntityType.Builder.of(CompressorBlockEntity::new,ModInnet.COMPRESSOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> COAL_GENERATOR = TILE_ENTITYS.register("coal_generator", () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new,ModInnet.COAL_GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> OXYGEN_LOADER = TILE_ENTITYS.register("oxygen_loader", () -> BlockEntityType.Builder.of(OxygenLoaderBlockEntity::new,ModInnet.OXYGEN_LOADER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> SOLAR_PANEL = TILE_ENTITYS.register("solar_panel", () -> BlockEntityType.Builder.of(SolarPanelBlockEntity::new,ModInnet.SOLAR_PANEL_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> NASA_WORKBENCH = TILE_ENTITYS.register("nasa_workbench", () -> BlockEntityType.Builder.of(NASAWorkbenchBlockEntity::new,ModInnet.NASA_WORKBENCH_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<OxygenBubbleDistributorBlockEntity>> OXYGEN_BUBBLE_DISTRIBUTOR = TILE_ENTITYS.register("oxygen_bubble_distributor", () -> BlockEntityType.Builder.of(OxygenBubbleDistributorBlockEntity::new,ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> WATER_PUMP = TILE_ENTITYS.register("water_pump", () -> BlockEntityType.Builder.of(WaterPumpBlockEntity::new,ModInnet.WATER_PUMP_BLOCK.get()).build(null));


    //Tile Entitys Flags
    public static final RegistryObject<BlockEntityType<FlagTileEntity>> FLAG = TILE_ENTITYS.register("flag", () -> BlockEntityType.Builder.of(FlagTileEntity::new, ModInnet.FLAG_BLOCK.get(), ModInnet.FLAG_BLUE_BLOCK.get(), ModInnet.FLAG_BROWN_BLOCK.get(), ModInnet.FLAG_CYAN_BLOCK.get(),ModInnet.FLAG_GRAY_BLOCK.get(),ModInnet.FLAG_GRAY_BLOCK.get(),ModInnet.FLAG_GREEN_BLOCK.get(),ModInnet.FLAG_LIGHT_BLUE_BLOCK.get(),ModInnet.FLAG_LIME_BLOCK.get(),ModInnet.FLAG_MAGENTA_BLOCk.get(),ModInnet.FLAG_ORANGE_BLOCK.get(),ModInnet.FLAG_PINK_BLOCK.get(),ModInnet.FLAG_PURPLE_BLOCK.get(),ModInnet.FLAG_RED_BLOCK.get(),ModInnet.FLAG_YELLOW_BLOCK.get()).build(null));

    //Machines
    public static RegistryObject<Block> FUEL_REFINERY_BLOCK = BLOCKS.register("fuel_refinery",() -> new FuelRefineryBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> BLAST_FURNACE_BLOCK = BLOCKS.register("blast_furnace",() -> new BlastingFurnaceBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> COMPRESSOR_BLOCK = BLOCKS.register("compressor",() -> new CompressorBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> COAL_GENERATOR_BLOCK = BLOCKS.register("coal_generator",() -> new CoalGeneratorBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> OXYGEN_LOADER_BLOCK = BLOCKS.register("oxygen_loader",() -> new OxygenLoaderBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> SOLAR_PANEL_BLOCK = BLOCKS.register("solar_panel",() -> new SolarPanelBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 1).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> NASA_WORKBENCH_BLOCK = BLOCKS.register("nasa_workbench",() -> new NASAWorkbenchBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 1).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static RegistryObject<Block> OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK = BLOCKS.register("oxygen_bubble_distributor",() -> new OxygenBubbleDistributorBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));
    public static RegistryObject<Block> WATER_PUMP_BLOCK = BLOCKS.register("water_pump",() -> new WaterPump(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).noOcclusion().strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops()));


    //Block Item
    public static final RegistryObject<BlockItem> NASA_WORKBENCH_ITEM = ITEMS.register("nasa_workbench", () -> new BlockItem(ModInnet.NASA_WORKBENCH_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> SOLAR_PANEL_ITEM = ITEMS.register("solar_panel", () -> new BlockItem(ModInnet.SOLAR_PANEL_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COAL_GENERATOR_ITEM = ITEMS.register("coal_generator", () -> new BlockItem(ModInnet.COAL_GENERATOR_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> BLAST_FURNACE_ITEM = ITEMS.register("blast_furnace", () -> new BlockItem(ModInnet.BLAST_FURNACE_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> COMPRESSOR_ITEM = ITEMS.register("compressor", () -> new BlockItem(ModInnet.COMPRESSOR_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> FUEL_REFINERY_ITEM = ITEMS.register("fuel_refinery", () -> new BlockItem(ModInnet.FUEL_REFINERY_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> OXYGEN_LOADER_ITEM = ITEMS.register("oxygen_loader", () -> new BlockItem(ModInnet.OXYGEN_LOADER_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> OXYGEN_BUBBLE_DISTRIBUTOR_ITEM = ITEMS.register("oxygen_bubble_distributor", () -> new BlockItem(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));
    public static final RegistryObject<BlockItem> WATER_PUMP_ITEM = ITEMS.register("water_pump", () -> new BlockItem(ModInnet.WATER_PUMP_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_machines)));

    //Tools
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new SwordItem(SteelItemTier.ITEM_TIER,3,-2.4f,new Item.Properties().tab(BossToolsItemGroups.tab_basics).fireResistant()));

    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new ShovelItem(SteelItemTier.ITEM_TIER,1.5f,-3f,new Item.Properties().tab(BossToolsItemGroups.tab_basics).fireResistant()));

    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(SteelItemTier.ITEM_TIER,1,-2.8f,new Item.Properties().tab(BossToolsItemGroups.tab_basics).fireResistant()));

    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new AxeItem(SteelItemTier.ITEM_TIER,6,-3f,new Item.Properties().tab(BossToolsItemGroups.tab_basics).fireResistant()));

    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new HoeItem(SteelItemTier.ITEM_TIER,-2,0f,new Item.Properties().tab(BossToolsItemGroups.tab_basics).fireResistant()));

    //Fuel Fluid
    public static final RegistryObject<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", ()-> new FuelFluid.Flowing());
    public static final RegistryObject<FlowingFluid> FUEL_STILL = FLUIDS.register("fuel", ()-> new FuelFluid.Source());
    public static RegistryObject<LiquidBlock> FUEL_BLOCK = BLOCKS.register("fuel",() -> new LiquidBlock(ModInnet.FUEL_STILL, Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));
    public static final RegistryObject<Item> FUEL_BUCKET = ITEMS.register("fuel_bucket", () -> new BucketItem(ModInnet.FUEL_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(BossToolsItemGroups.tab_normal)));

    public static final ResourceLocation FLUID_VEHICLE_FUEL_TAG = new ResourceLocation("boss_tools", "vehicle_fuel");

    //Oil Fluid
    public static final RegistryObject<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", ()-> new OilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> OIL_STILL = FLUIDS.register("oil", ()-> new OilFluid.Source());
    public static RegistryObject<LiquidBlock> OIL_BLOCK = BLOCKS.register("oil",() -> new LiquidBlock(ModInnet.OIL_STILL, Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(ModInnet.OIL_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(BossToolsItemGroups.tab_normal)));

    public static final ResourceLocation OIL_FLUID_TAG = new ResourceLocation("boss_tools", "oil");

    //Item
    public static final RegistryObject<Item> COAL_TORCH_ITEM = ITEMS.register("coal_torch", () -> new CoalTorchItem(COAL_TORCH_BLOCK.get(), WALL_COAL_TORCH_BLOCK.get(),new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    //Lanterns
    public static final RegistryObject<BlockItem> COAL_LANTERN_ITEM = ITEMS.register("coal_lantern", () -> new BlockItem(ModInnet.COAL_LANTERN_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_basics)));

    //Spawn Eggs
    public static final RegistryObject<ForgeSpawnEggItem> ALIEN_SPAWN_EGG = ITEMS.register("alien_spawn_egg", () -> new ForgeSpawnEggItem(ALIEN::get, -13382401, -11650781, new Item.Properties().tab(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ForgeSpawnEggItem> ALIEN_ZOMBIE_SPAWN_EGG = ITEMS.register("alien_zombie_spawn_egg",() -> new ForgeSpawnEggItem(ALIEN_ZOMBIE::get, -14804199, -16740159, new Item.Properties().tab(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ForgeSpawnEggItem> STAR_CRAWLER_SPAWN_EGG = ITEMS.register("star_crawler_spawn_egg",() -> new ForgeSpawnEggItem(STAR_CRAWLER::get, -13421773, -16724788, new Item.Properties().tab(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ForgeSpawnEggItem> PYGRO_SPAWN_EGG = ITEMS.register("pygro_spawn_egg",() -> new ForgeSpawnEggItem(PYGRO::get, -3381760, -6750208, new Item.Properties().tab(BossToolsItemGroups.tab_spawn_eggs)));
    public static final RegistryObject<ForgeSpawnEggItem> MOGLER_SPAWN_EGG = ITEMS.register("mogler_spawn_egg",() -> new ForgeSpawnEggItem(MOGLER::get, -13312, -3407872, new Item.Properties().tab(BossToolsItemGroups.tab_spawn_eggs)));

    //Generel Items
    public static final RegistryObject<Item> CHESE = ITEMS.register("chesse", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_normal).food((new FoodProperties.Builder()).nutrition(4).saturationMod(3f).build())));
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new HammerItem(new Item.Properties().tab(BossToolsItemGroups.tab_basics).durability(9).setNoRepair()));
    public static final RegistryObject<Item> IRON_STICK = ITEMS.register("iron_stick", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_GEAR = ITEMS.register("oxygen_gear", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_FRAME = ITEMS.register("engine_frame", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ENGINE_FAN = ITEMS.register("engine_fan", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ROCKET_NOSE_CONE = ITEMS.register("rocket_nose_cone", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DIAMOND_ENGINE = ITEMS.register("diamond_engine", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> IRON_ENGINE = ITEMS.register("iron_engine", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_ENGINE = ITEMS.register("golden_engine", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> IRON_TANK = ITEMS.register("iron_tank", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> GOLDEN_TANK = ITEMS.register("golden_tank", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> DIAMOND_TANK = ITEMS.register("diamond_tank", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));
    public static final RegistryObject<Item> ROCKET_FIN = ITEMS.register("rocket_fin", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_basics)));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DESH_INGOT = ITEMS.register("desh_ingot", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> SILICON_INGOT = ITEMS.register("silicon_ingot", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DESH_PLATE = ITEMS.register("desh_plate", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> COMPRESSED_STEEL = ITEMS.register("compressed_steel", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_DESH = ITEMS.register("compressed_desh", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> COMPRESSED_SILICON = ITEMS.register("compressed_silicon", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));

    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> DESH_NUGGET = ITEMS.register("desh_nugget", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));
    public static final RegistryObject<Item> SILICON_NUGGET = ITEMS.register("silicon_nugget", () -> new Item(new Item.Properties().tab(BossToolsItemGroups.tab_materials)));

    //Generel Blocks
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DESH_BLOCK = BLOCKS.register("desh_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SILICON_BLOCK = BLOCKS.register("silicon_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> IRON_PLATING_BLOCK = BLOCKS.register("iron_plating_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RUSTED_IRON_PILLAR_BLOCK = BLOCKS.register("rusted_iron_pillar_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RUSTED_IRON_PLATING_BLOCK = BLOCKS.register("rusted_iron_plating_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLUE_IRON_PLATING_BLOCK = BLOCKS.register("blue_iron_plating_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).lightLevel(state -> 15).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> IRON_MARK_BLOCK = BLOCKS.register("iron_mark_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 2.5f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_MOON_BRICKS = BLOCKS.register("cracked_moon_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_MOON_BRICK_SALB = BLOCKS.register("cracked_moon_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_MOON_BRICK_STAIRS = BLOCKS.register("cracked_moon_brick_stairs", () -> new StairBlock(() -> ModInnet.CRACKED_MOON_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(ModInnet.CRACKED_MOON_BRICKS.get()).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_BRICKS = BLOCKS.register("moon_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_BRICK_SLAB = BLOCKS.register("moon_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_BRICK_STAIRS = BLOCKS.register("moon_brick_stairs", () -> new StairBlock(() -> ModInnet.CRACKED_MOON_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(ModInnet.MOON_BRICKS.get()).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SKY_STONE = BLOCKS.register("sky_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MARS_STONE = BLOCKS.register("mars_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MERCURY_STONE = BLOCKS.register("mercury_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PURPLE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_STONE = BLOCKS.register("moon_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MERCURY_COBBLESTONE = BLOCKS.register("mercury_cobblestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PURPLE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VENUS_SANDSTONE = BLOCKS.register("venus_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VENUS_STONE = BLOCKS.register("venus_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> INFERNAL_SPIRE_BLOCK = BLOCKS.register("infernal_spire_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 1f).requiresCorrectToolForDrops()));


    //Faling Block
    public static final RegistryObject<Block> MOON_SAND = BLOCKS.register("moon_sand", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_GRAY).sound(SoundType.SAND).strength(0.5f, 0.5f)));
    public static final RegistryObject<Block> MARS_SAND = BLOCKS.register("mars_sand", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.SAND).strength(0.5f, 0.5f)));
    public static final RegistryObject<Block> VENUS_SAND = BLOCKS.register("venus_sand", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.SAND).strength(0.5f, 0.5f)));


    //ORES
    public static final RegistryObject<Block> MOON_CHESSE_ORE = BLOCKS.register("moon_cheese_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_DESH_ORE = BLOCKS.register("moon_desh_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_GLOWSTONE_ORE = BLOCKS.register("moon_glowstone_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).lightLevel(state -> 15).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_IRON_ORE = BLOCKS.register("moon_iron_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MARS_ICE_SHARD_ORE = BLOCKS.register("mars_ice_shard_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MARS_IRON_ORE = BLOCKS.register("mars_iron_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MARS_DIAMOND_ORE = BLOCKS.register("mars_diamond_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MARS_SILICON_ORE = BLOCKS.register("mars_silicon_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MERCURY_IRON_ORE = BLOCKS.register("mercury_iron_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VENUS_COAL_ORE = BLOCKS.register("venus_coal_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VENUS_DIAMOND_ORE = BLOCKS.register("venus_diamond_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VENUS_GOLD_ORE = BLOCKS.register("venus_gold_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));

    //Generel Block Items
    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = ITEMS.register("steel_block", () -> new BlockItem(ModInnet.STEEL_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> DESH_BLOCK_ITEM = ITEMS.register("desh_block", () -> new BlockItem(ModInnet.DESH_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> SILICON_BLOCK_ITEM = ITEMS.register("silicon_block", () -> new BlockItem(ModInnet.SILICON_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> IRON_PLATING_BLOCK_ITEM = ITEMS.register("iron_plating_block", () -> new BlockItem(ModInnet.IRON_PLATING_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_PILLAR_BLOCK_ITEM = ITEMS.register("rusted_iron_pillar_block", () -> new BlockItem(ModInnet.RUSTED_IRON_PILLAR_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_PLATING_BLOCK_ITEM = ITEMS.register("rusted_iron_plating_block", () -> new BlockItem(ModInnet.RUSTED_IRON_PLATING_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> BLUE_IRON_PLATING_BLOCK_ITEM = ITEMS.register("blue_iron_plating_block", () -> new BlockItem(ModInnet.BLUE_IRON_PLATING_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> INFERNAL_SPIRE_BLOCK_ITEM = ITEMS.register("infernal_spire_block", () -> new BlockItem(ModInnet.INFERNAL_SPIRE_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> IRON_MARK_BLOCK_ITEM = ITEMS.register("iron_mark_block", () -> new BlockItem(ModInnet.IRON_MARK_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_STONE_ITEM = ITEMS.register("mars_stone", () -> new BlockItem(ModInnet.MARS_STONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MERCURY_STONE_ITEM = ITEMS.register("mercury_stone", () -> new BlockItem(ModInnet.MERCURY_STONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> SKY_STONE_ITEM = ITEMS.register("sky_stone", () -> new BlockItem(ModInnet.SKY_STONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_BRICKS_ITEM = ITEMS.register("moon_bricks", () -> new BlockItem(ModInnet.MOON_BRICKS.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_BRICK_SLAB_ITEM = ITEMS.register("moon_brick_slab", () -> new BlockItem(ModInnet.MOON_BRICK_SLAB.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_BRICK_STAIRS_ITEM = ITEMS.register("moon_brick_stairs", () -> new BlockItem(ModInnet.MOON_BRICK_STAIRS.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> CRACKED_MOON_BRICKS_ITEM = ITEMS.register("cracked_moon_bricks", () -> new BlockItem(ModInnet.CRACKED_MOON_BRICKS.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> CRACKED_MOON_BRICK_SLAB_ITEM = ITEMS.register("cracked_moon_brick_slab", () -> new BlockItem(ModInnet.CRACKED_MOON_BRICK_SALB.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> CRACKED_MOON_BRICK_STAIRS_ITEM = ITEMS.register("cracked_moon_brick_stairs", () -> new BlockItem(ModInnet.CRACKED_MOON_BRICK_STAIRS.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_STONE_ITEM = ITEMS.register("moon_stone", () -> new BlockItem(ModInnet.MOON_STONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MERCURY_COBBLESTONE_ITEM = ITEMS.register("mercury_cobblestone", () -> new BlockItem(ModInnet.MERCURY_COBBLESTONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_SANDSTONE_ITEM = ITEMS.register("venus_sandstone", () -> new BlockItem(ModInnet.VENUS_SANDSTONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_STONE_ITEM = ITEMS.register("venus_stone", () -> new BlockItem(ModInnet.VENUS_STONE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));

    public static final RegistryObject<BlockItem> ROCKET_LAUNCH_PAD_ITEM = ITEMS.register("rocket_launch_pad", () -> new BlockItem(ModInnet.ROCKET_LAUNCH_PAD.get(), new Item.Properties().tab(BossToolsItemGroups.tab_normal)));

    public static final RegistryObject<BlockItem> MOON_SAND_ITEM = ITEMS.register("moon_sand", () -> new BlockItem(ModInnet.MOON_SAND.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_SAND_ITEM = ITEMS.register("mars_sand", () -> new BlockItem(ModInnet.MARS_SAND.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_SAND_ITEM = ITEMS.register("venus_sand", () -> new BlockItem(ModInnet.VENUS_SAND.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));

    public static final RegistryObject<BlockItem> MOON_CHESSE_ORE_ITEM = ITEMS.register("moon_cheese_ore", () -> new BlockItem(ModInnet.MOON_CHESSE_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_DESH_ORE_ITEM = ITEMS.register("moon_desh_ore", () -> new BlockItem(ModInnet.MOON_DESH_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_GLOWSTONE_ITEM = ITEMS.register("moon_glowstone_ore", () -> new BlockItem(ModInnet.MOON_GLOWSTONE_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MOON_IRON_ORE_ITEM = ITEMS.register("moon_iron_ore", () -> new BlockItem(ModInnet.MOON_IRON_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_ICE_SHARD_ORE_ITEM = ITEMS.register("mars_ice_shard_ore", () -> new BlockItem(ModInnet.MARS_ICE_SHARD_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_IRON_ORE_ITEM = ITEMS.register("mars_iron_ore", () -> new BlockItem(ModInnet.MARS_IRON_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_DIAMOND_ORE_ITEM = ITEMS.register("mars_diamond_ore", () -> new BlockItem(ModInnet.MARS_DIAMOND_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MARS_SILICON_ORE_ITEM = ITEMS.register("mars_silicon_ore", () -> new BlockItem(ModInnet.MARS_SILICON_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> MERCURY_IRON_ORE_ITEM = ITEMS.register("mercury_iron_ore", () -> new BlockItem(ModInnet.MERCURY_IRON_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_COAL_ORE_ITEM = ITEMS.register("venus_coal_ore", () -> new BlockItem(ModInnet.VENUS_COAL_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_DIAMOND_ORE_ITEM = ITEMS.register("venus_diamond_ore", () -> new BlockItem(ModInnet.VENUS_DIAMOND_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));
    public static final RegistryObject<BlockItem> VENUS_GOLD_ORE_ITEM = ITEMS.register("venus_gold_ore", () -> new BlockItem(ModInnet.VENUS_GOLD_ORE.get(), new Item.Properties().tab(BossToolsItemGroups.tab_blocks)));

    //Effects
    public static final RegistryObject<MobEffect> OXYGEN_EFFECT = EFFECTS.register("oxygen_bubble_effect", () -> new OxygenEffect(MobEffectCategory.BENEFICIAL,3035801));

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
    public static final RegistryObject<Item> FLAG_ITEM = ITEMS.register("flag", () -> new DoubleHighBlockItem(FLAG_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_BLUE_ITEM = ITEMS.register("flag_blue", () -> new DoubleHighBlockItem(FLAG_BLUE_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_BROWN_ITEM = ITEMS.register("flag_brown", () -> new DoubleHighBlockItem(FLAG_BROWN_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_CYAN_ITEM = ITEMS.register("flag_cyan", () -> new DoubleHighBlockItem(FLAG_CYAN_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_GRAY_ITEM = ITEMS.register("flag_gray", () -> new DoubleHighBlockItem(FLAG_GRAY_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_GREEN_ITEM = ITEMS.register("flag_green", () -> new DoubleHighBlockItem(FLAG_GREEN_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_LIGHT_BLUE_ITEM = ITEMS.register("flag_light_blue", () -> new DoubleHighBlockItem(FLAG_LIGHT_BLUE_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_LIME_ITEM = ITEMS.register("flag_lime", () -> new DoubleHighBlockItem(FLAG_LIME_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_MAGENTA_ITEM = ITEMS.register("flag_magenta", () -> new DoubleHighBlockItem(FLAG_MAGENTA_BLOCk.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_ORANGE_ITEM = ITEMS.register("flag_orange", () -> new DoubleHighBlockItem(FLAG_ORANGE_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_PINK_ITEM = ITEMS.register("flag_pink", () -> new DoubleHighBlockItem(FLAG_PINK_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_PURPLE_ITEM = ITEMS.register("flag_purple", () -> new DoubleHighBlockItem(FLAG_PURPLE_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_RED_ITEM = ITEMS.register("flag_red", () -> new DoubleHighBlockItem(FLAG_RED_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));
    public static final RegistryObject<Item> FLAG_YELLOW_ITEM = ITEMS.register("flag_yellow", () -> new DoubleHighBlockItem(FLAG_YELLOW_BLOCK.get(), new Item.Properties().tab(BossToolsItemGroups.tab_flags)));

    //GUIS
    public static final RegistryObject<MenuType<RocketGui.GuiContainer>> ROCKET_GUI = GUIS.register("rocket_gui", () -> new MenuType(new RocketGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<BlastFurnaceGui.GuiContainer>> BLAST_FURNACE_GUI = GUIS.register("blast_furnace_gui", () -> new MenuType(new BlastFurnaceGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<CompressorGui.GuiContainer>> COMPRESSOR_GUI = GUIS.register("compressor_gui", () -> new MenuType(new CompressorGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<FuelRefineryGui.GuiContainer>> FUEL_REFINERY_GUI = GUIS.register("fuel_refinery_gui", () -> new MenuType(new FuelRefineryGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<CoalGeneratorGui.GuiContainer>> COAL_GENERATOR_GUI = GUIS.register("coal_generator_gui", () -> new MenuType(new CoalGeneratorGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<NasaWorkbenchGui.GuiContainer>> NASA_WORKBENCH_GUI = GUIS.register("nasa_workbench_gui", () -> new MenuType(new NasaWorkbenchGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<OxygenLoaderGui.GuiContainer>> OXYGEN_LOADER_GUI = GUIS.register("oxygen_loader_gui", () -> new MenuType(new OxygenLoaderGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<SolarPanelGui.GuiContainer>> SOLAR_PANEL_GUI = GUIS.register("solar_panel_gui", () -> new MenuType(new SolarPanelGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<WaterPumpGui.GuiContainer>> WATER_PUMP_GUI = GUIS.register("water_pump_gui", () -> new MenuType(new WaterPumpGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<OxygenBubbleDistributorGui.GuiContainer>> OXYGEN_BUBBLE_DISTRIBUTOR_GUI = GUIS.register("oxygen_bubble_distributor_gui", () -> new MenuType(new OxygenBubbleDistributorGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<LanderGui.GuiContainer>> LANDER_GUI = GUIS.register("lander_gui", () -> new MenuType(new LanderGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<RoverGui.GuiContainer>> ROVER_GUI = GUIS.register("rover_gui", () -> new MenuType(new RoverGui.GuiContainerFactory()));
    public static final RegistryObject<MenuType<PlanetSelectionGui.GuiContainer>> PLANET_SELECTION_GUI = GUIS.register("planet_selection_gui", () -> new MenuType(new PlanetSelectionGui.GuiContainerFactory()));


    //Particle
    public static final RegistryObject<ParticleType<SimpleParticleType>> VENUS_RAIN_PARTICLE = PARTICLES.register("venus_rain", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> LARGE_FLAME_PARTICLE = PARTICLES.register("large_flame", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> SMOKE_PARTICLE = PARTICLES.register("smoke", () -> new SimpleParticleType(true));


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
    public static final DamageSource DAMAGE_SOURCE_OXYGEN = new DamageSource("oxygen").bypassArmor();
    public static final DamageSource DAMAGE_SOURCE_ACID_RAIN = new DamageSource("venus.acid").bypassArmor();

    //Todo rework with Biomes
    /*
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
        MARS_ICE_SPIKE = new MarsIceSpikeFeature(NoneFeatureConfiguration.CODEC);
        MARS_ICE_SPIKE.setRegistryName(BossToolsMod.ModId, "mars_ice_spike");
        feature.getRegistry().register(MARS_ICE_SPIKE);

        //VENUS DELTAS
        VENUS_DELTAS = new VenusDeltas(ColumnConfig.CODEC);
        VENUS_DELTAS.setRegistryName(BossToolsMod.ModId, "venus_deltas");
        feature.getRegistry().register(VENUS_DELTAS);
    }
    */

    /*
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

     */
/*
    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            STStructures.setupStructures();
            STStructures2.setupStructures();
            STConfiguredStructures.registerConfiguredStructures();
        });
    }

 */

    /*
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

     */
/*
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
    */

    @SubscribeEvent
    public static void defaultAttributes(EntityAttributeCreationEvent event) {
        event.put(ALIEN.get(), AlienEntity.setCustomAttributes().build());
        event.put(PYGRO.get(), PygroEntity.setCustomAttributes().build());
        event.put(MOGLER.get(), MoglerEntity.setCustomAttributes().build());
        event.put(ALIEN_ZOMBIE.get(), AlienZombieEntity.setCustomAttributes().build());
        event.put(STAR_CRAWLER.get(), StarCrawlerEntity.setCustomAttributes().build());
        event.put(TIER_1_ROCKET.get(), RocketTier1Entity.setCustomAttributes().build());
        event.put(TIER_2_ROCKET.get(), RocketTier2Entity.setCustomAttributes().build());
        event.put(TIER_3_ROCKET.get(), RocketTier3Entity.setCustomAttributes().build());
        event.put(LANDER.get(), LanderEntity.setCustomAttributes().build());
        event.put(ROVER.get(), RoverEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            BossToolsRecipeTypes.init();

            /*
            ICE_SPIKE = registerFeature("mars_ice_spike", ModInnet.MARS_ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
            //Venus Deltas
            DELTAS = registerFeature("venus_deltas_1", ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 8))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(4))));
            //Venus Deltas2
            DELTAS2 = registerFeature("venus_deltas_2", ModInnet.VENUS_DELTAS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 6))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
        */
        });
    }
/*
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
    */
}