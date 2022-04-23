package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.fluid.OilFluid;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.fluid.FuelFluid;

public class ModFluids {

    public static FlowableFluid FUEL_STILL;
    public static FlowableFluid FLOWING_FUEL;
    public static Block FUEL_BLOCK;
    public static Item FUEL_BUCKET;

    public static FlowableFluid OIL_STILL;
    public static FlowableFluid FLOWING_OIL;
    public static Block OIL_BLOCK;
    public static Item OIL_BUCKET;

    public static void register() {
        // Fuel Fluid.
        FUEL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("fuel"), new FuelFluid.Still());
        FLOWING_FUEL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_fuel"), new FuelFluid.Flowing());

        FUEL_BLOCK = new FluidBlock(FUEL_STILL, FabricBlockSettings.copy(Blocks.WATER).strength(100.0f).dropsNothing());
        ModBlocks.register("fuel", FUEL_BLOCK);

        FUEL_BUCKET = new BucketItem(FUEL_STILL,
                new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ModItems.ITEM_GROUP_NORMAL));
        ModItems.register("fuel_bucket", FUEL_BUCKET);

        // Oil Fluid.
        OIL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("oil"), new OilFluid.Still());
        FLOWING_OIL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_oil"), new OilFluid.Flowing());

        OIL_BLOCK = new FluidBlock(OIL_STILL, FabricBlockSettings.copy(Blocks.WATER).strength(100.0f).dropsNothing());
        ModBlocks.register("oil", OIL_BLOCK);

        OIL_BUCKET = new BucketItem(OIL_STILL,
                new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ModItems.ITEM_GROUP_NORMAL));
        ModItems.register("oil_bucket", OIL_BUCKET);
    }
}