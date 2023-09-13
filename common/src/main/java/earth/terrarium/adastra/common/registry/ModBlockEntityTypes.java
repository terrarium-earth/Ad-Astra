package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineExtensionBlockEntity;
import earth.terrarium.adastra.common.blockentities.machines.*;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<ContainerMachineExtensionBlockEntity>> MACHINE_EXTENSION = BLOCK_ENTITY_TYPES.register(
        "machine_extension",
        () -> RegistryHelpers.createBlockEntityType(
            ContainerMachineExtensionBlockEntity::new,
            ModBlocks.MACHINES.stream().map(RegistryEntry::get).toArray(Block[]::new)));

    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register(
        "oxygen_distributor",
        () -> RegistryHelpers.createBlockEntityType(
            OxygenDistributorBlockEntity::new,
            ModBlocks.OXYGEN_DISTRIBUTOR.get()));

    public static final RegistryEntry<BlockEntityType<GravityNormalizerBlockEntity>> GRAVITY_NORMALIZER = BLOCK_ENTITY_TYPES.register(
        "gravity_normalizer",
        () -> RegistryHelpers.createBlockEntityType(
            GravityNormalizerBlockEntity::new,
            ModBlocks.GRAVITY_NORMALIZER.get()));

    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register(
        "solar_panel",
        () -> RegistryHelpers.createBlockEntityType(
            SolarPanelBlockEntity::new,
            ModBlocks.ETRIONIC_SOLAR_PANEL.get(),
            ModBlocks.VESNIUM_SOLAR_PANEL.get()
        ));

    public static final RegistryEntry<BlockEntityType<HydraulicPressBlockEntity>> HYDRAULIC_PRESS = BLOCK_ENTITY_TYPES.register(
        "hydraulic_press",
        () -> RegistryHelpers.createBlockEntityType(
            HydraulicPressBlockEntity::new,
            ModBlocks.HYDRAULIC_PRESS.get()));

    public static final RegistryEntry<BlockEntityType<OilRefineryBlockEntity>> OIL_REFINERY = BLOCK_ENTITY_TYPES.register(
        "oil_refinery",
        () -> RegistryHelpers.createBlockEntityType(
            OilRefineryBlockEntity::new,
            ModBlocks.OIL_REFINERY.get()));

    public static final RegistryEntry<BlockEntityType<SeparatorBlockEntity>> SEPARATOR = BLOCK_ENTITY_TYPES.register(
        "separator",
        () -> RegistryHelpers.createBlockEntityType(
            SeparatorBlockEntity::new,
            ModBlocks.SEPARATOR.get()));

    public static final RegistryEntry<BlockEntityType<SteamGeneratorBlockEntity>> STEAM_GENERATOR = BLOCK_ENTITY_TYPES.register(
        "steam_generator",
        () -> RegistryHelpers.createBlockEntityType(
            SteamGeneratorBlockEntity::new,
            ModBlocks.STEAM_GENERATOR.get()));

    public static final RegistryEntry<BlockEntityType<EnergyControllerBlockEntity>> ENERGY_CONTROLLER = BLOCK_ENTITY_TYPES.register(
        "energy_controller",
        () -> RegistryHelpers.createBlockEntityType(
            EnergyControllerBlockEntity::new,
            ModBlocks.ENERGY_CONTROLLER.get()));

    public static final RegistryEntry<BlockEntityType<EnergyRelayBlockEntity>> ENERGY_RELAY = BLOCK_ENTITY_TYPES.register(
        "energy_relay",
        () -> RegistryHelpers.createBlockEntityType(
            EnergyRelayBlockEntity::new,
            ModBlocks.ENERGY_RELAY.get()));

    public static final RegistryEntry<BlockEntityType<EnergyRelayBlockEntity>> ENERGY_INJECTOR = BLOCK_ENTITY_TYPES.register(
        "energy_injector",
        () -> RegistryHelpers.createBlockEntityType(
            EnergyRelayBlockEntity::new,
            ModBlocks.ENERGY_INJECTOR.get()));

    public static final RegistryEntry<BlockEntityType<EnergyRelayBlockEntity>> ENERGY_RECEIVER = BLOCK_ENTITY_TYPES.register(
        "energy_receiver",
        () -> RegistryHelpers.createBlockEntityType(
            EnergyRelayBlockEntity::new,
            ModBlocks.ENERGY_RECEIVER.get()));

    public static final RegistryEntry<BlockEntityType<VesniumCoilBlockEntity>> VESNIUM_COIL = BLOCK_ENTITY_TYPES.register(
        "vesnium_coil",
        () -> RegistryHelpers.createBlockEntityType(
            VesniumCoilBlockEntity::new,
            ModBlocks.VESNIUM_COIL.get()));

    public static final RegistryEntry<BlockEntityType<TinkerersWorkbenchBlockEntity>> TINKERERS_WORKBENCH = BLOCK_ENTITY_TYPES.register(
        "tinkerers_workbench",
        () -> RegistryHelpers.createBlockEntityType(
            TinkerersWorkbenchBlockEntity::new,
            ModBlocks.TINKERERS_WORKBENCH.get()));

    public static final RegistryEntry<BlockEntityType<BatteryBlockEntity>> BATTERY = BLOCK_ENTITY_TYPES.register(
        "battery",
        () -> RegistryHelpers.createBlockEntityType(
            BatteryBlockEntity::new,
            ModBlocks.BATTERY.get()));

    public static final RegistryEntry<BlockEntityType<EtrionicBlastFurnaceBlockEntity>> ETRIONIC_BLAST_FURNACE = BLOCK_ENTITY_TYPES.register(
        "etreonic_blast_furnace",
        () -> RegistryHelpers.createBlockEntityType(
            EtrionicBlastFurnaceBlockEntity::new,
            ModBlocks.ETRIONIC_BLAST_FURNACE.get()));

    public static final RegistryEntry<BlockEntityType<RecyclerBlockEntity>> RECYCLER = BLOCK_ENTITY_TYPES.register(
        "recycler",
        () -> RegistryHelpers.createBlockEntityType(
            RecyclerBlockEntity::new,
            ModBlocks.RECYCLER.get()));

    public static final RegistryEntry<BlockEntityType<DetectorBlockEntity>> DETECTOR = BLOCK_ENTITY_TYPES.register(
        "detector",
        () -> RegistryHelpers.createBlockEntityType(
            DetectorBlockEntity::new,
            ModBlocks.DETECTOR.get()));
}
