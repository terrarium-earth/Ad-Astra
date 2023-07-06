package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.BatteryBlockEntity;
import earth.terrarium.adastra.common.blockentities.HydraulicPressBlockEntity;
import earth.terrarium.adastra.common.blockentities.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.blockentities.SolarPanelBlockEntity;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register(
        "oxygen_distributor",
        () -> RegistryHelpers.createBlockEntityType(
            OxygenDistributorBlockEntity::new,
            ModBlocks.OXYGEN_DISTRIBUTOR.get()));

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

    public static final RegistryEntry<BlockEntityType<BatteryBlockEntity>> BATTERY = BLOCK_ENTITY_TYPES.register(
        "battery",
        () -> RegistryHelpers.createBlockEntityType(
            BatteryBlockEntity::new,
            ModBlocks.BATTERY.get()));
}
