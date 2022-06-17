package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.blocks.fluid.FuelFluid;
import com.github.alexnijjar.beyond_earth.blocks.fluid.OilFluid;
import com.github.alexnijjar.beyond_earth.blocks.fluid.OxygenFluid;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModFluids {

    public static FlowableFluid FUEL_STILL;
    public static FlowableFluid FLOWING_FUEL;
    public static Block FUEL_BLOCK;
    public static Item FUEL_BUCKET;

    public static FlowableFluid OIL_STILL;
    public static FlowableFluid FLOWING_OIL;
    public static Block OIL_BLOCK;
    public static Item OIL_BUCKET;

    public static FlowableFluid OXYGEN_STILL;
    public static Block OXYGEN_BLOCK;
    public static Item OXYGEN_BUCKET;

    public static void register() {
        // Fuel Fluid.
        FUEL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("fuel"), new FuelFluid.Still());
        FLOWING_FUEL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_fuel"), new FuelFluid.Flowing());

        FUEL_BLOCK = new FluidBlock(FUEL_STILL, FabricBlockSettings.copy(Blocks.WATER).strength(100.0f).dropsNothing());
        ModBlocks.register("fuel", FUEL_BLOCK);

        FUEL_BUCKET = new BucketItem(FUEL_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
        ModItems.register("fuel_bucket", FUEL_BUCKET);

        // Oil Fluid.
        OIL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("oil"), new OilFluid.Still());
        FLOWING_OIL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_oil"), new OilFluid.Flowing());

        OIL_BLOCK = new FluidBlock(OIL_STILL, FabricBlockSettings.copy(Blocks.WATER).strength(100.0f).dropsNothing());
        ModBlocks.register("oil", OIL_BLOCK);

        OIL_BUCKET = new BucketItem(OIL_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
        ModItems.register("oil_bucket", OIL_BUCKET);

        // Oxygen Fluid.
        OXYGEN_STILL = Registry.register(Registry.FLUID, new ModIdentifier("oxygen"), new OxygenFluid.Still());

        // Oxygen is not a placable fluid.
        OXYGEN_BLOCK = new FluidBlock(OXYGEN_STILL, FabricBlockSettings.copy(Blocks.WATER).strength(100.0f).dropsNothing());
        ModBlocks.register("oxygen", OXYGEN_BLOCK);
        OXYGEN_BUCKET = new BucketItem(OXYGEN_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)) {
            @Override
            public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
                ItemStack itemStack = user.getStackInHand(hand);
                return TypedActionResult.fail(itemStack);
            }
        };
        ModItems.register("oxygen_bucket", OXYGEN_BUCKET);
    }
}