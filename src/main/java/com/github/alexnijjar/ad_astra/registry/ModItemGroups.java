package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.items.OxygenTankItem;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.items.vehicles.VehicleItem;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.Collections;

public interface ModItemGroups {
	public static final ItemGroup ITEM_GROUP_NORMAL = FabricItemGroupBuilder.create(new ModIdentifier("tab_normal")).icon(() -> new ItemStack(ModItems.TIER_1_ROCKET)).appendItems(stacks -> {

		ItemStack tier1Rocket = ModItems.TIER_1_ROCKET.getDefaultStack();
		((VehicleItem) tier1Rocket.getItem()).setAmount(tier1Rocket, ((VehicleItem) tier1Rocket.getItem()).getTankSize());
		((VehicleItem) tier1Rocket.getItem()).setFluid(tier1Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier2Rocket = ModItems.TIER_2_ROCKET.getDefaultStack();
		((VehicleItem) tier2Rocket.getItem()).setAmount(tier2Rocket, ((VehicleItem) tier2Rocket.getItem()).getTankSize());
		((VehicleItem) tier2Rocket.getItem()).setFluid(tier2Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier3Rocket = ModItems.TIER_3_ROCKET.getDefaultStack();
		((VehicleItem) tier3Rocket.getItem()).setAmount(tier3Rocket, ((VehicleItem) tier3Rocket.getItem()).getTankSize());
		((VehicleItem) tier3Rocket.getItem()).setFluid(tier3Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier4Rocket = ModItems.TIER_4_ROCKET.getDefaultStack();
		((VehicleItem) tier4Rocket.getItem()).setAmount(tier4Rocket, ((VehicleItem) tier4Rocket.getItem()).getTankSize());
		((VehicleItem) tier4Rocket.getItem()).setFluid(tier4Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier1Rover = ModItems.TIER_1_ROVER.getDefaultStack();
		((VehicleItem) tier1Rover.getItem()).setAmount(tier1Rover, ((VehicleItem) tier1Rover.getItem()).getTankSize());
		((VehicleItem) tier1Rover.getItem()).setFluid(tier1Rover, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack spaceSuit = ModItems.SPACE_SUIT.getDefaultStack();
		((SpaceSuit) spaceSuit.getItem()).setAmount(spaceSuit, ((SpaceSuit) spaceSuit.getItem()).getTankSize());
		((SpaceSuit) spaceSuit.getItem()).setFluid(spaceSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));

		ItemStack netheriteSpaceSuit = ModItems.NETHERITE_SPACE_SUIT.getDefaultStack();
		((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).setAmount(netheriteSpaceSuit, ((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).getTankSize());
		((SpaceSuit) netheriteSpaceSuit.getItem()).setFluid(netheriteSpaceSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));

		ItemStack jetSuit = ModItems.JET_SUIT.getDefaultStack();
		((JetSuit) jetSuit.getItem()).setAmount(jetSuit, ((JetSuit) jetSuit.getItem()).getTankSize());
		((SpaceSuit) jetSuit.getItem()).setFluid(jetSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));
		((JetSuit) jetSuit.getItem()).setStoredEnergy(jetSuit, AdAstra.CONFIG.spaceSuit.jetSuitMaxEnergy);

		stacks.addAll(Collections.nCopies(54, ItemStack.EMPTY));

		stacks.set(0, tier1Rocket);
		stacks.set(1, tier2Rocket);
		stacks.set(2, tier3Rocket);
		stacks.set(3, tier4Rocket);

		stacks.set(9, ModItems.TIER_1_ROCKET.getDefaultStack());
		stacks.set(10, ModItems.TIER_2_ROCKET.getDefaultStack());
		stacks.set(11, ModItems.TIER_3_ROCKET.getDefaultStack());
		stacks.set(12, ModItems.TIER_4_ROCKET.getDefaultStack());

		stacks.set(19, tier1Rover);
		stacks.set(18, ModItems.TIER_1_ROVER.getDefaultStack());

		stacks.set(6, ModItems.SPACE_HELMET.getDefaultStack());
		stacks.set(15, spaceSuit);
		stacks.set(24, ModItems.SPACE_SUIT.getDefaultStack());
		stacks.set(33, ModItems.SPACE_PANTS.getDefaultStack());
		stacks.set(42, ModItems.SPACE_BOOTS.getDefaultStack());

		stacks.set(7, ModItems.NETHERITE_SPACE_HELMET.getDefaultStack());
		stacks.set(16, netheriteSpaceSuit);
		stacks.set(25, ModItems.NETHERITE_SPACE_SUIT.getDefaultStack());
		stacks.set(34, ModItems.NETHERITE_SPACE_PANTS.getDefaultStack());
		stacks.set(43, ModItems.NETHERITE_SPACE_BOOTS.getDefaultStack());

		stacks.set(8, ModItems.JET_SUIT_HELMET.getDefaultStack());
		stacks.set(17, jetSuit);
		stacks.set(26, ModItems.JET_SUIT.getDefaultStack());
		stacks.set(35, ModItems.JET_SUIT_PANTS.getDefaultStack());
		stacks.set(44, ModItems.JET_SUIT_BOOTS.getDefaultStack());

		stacks.set(27, ModItems.OXYGEN_TANK.getDefaultStack());
		stacks.set(28, OxygenTankItem.createOxygenatedTank());

		stacks.set(36, ModItems.ASTRODUX.getDefaultStack());
		stacks.set(37, ModItems.SPACE_PAINTING.getDefaultStack());
		stacks.set(38, ModItems.CHEESE.getDefaultStack());
		stacks.set(39, ModItems.ROCKET_LAUNCH_PAD.getDefaultStack());

		stacks.set(45, ModItems.OIL_BUCKET.getDefaultStack());
		stacks.set(46, ModItems.FUEL_BUCKET.getDefaultStack());
		stacks.set(47, ModItems.CRYO_FUEL_BUCKET.getDefaultStack());
		stacks.set(48, ModItems.OXYGEN_BUCKET.getDefaultStack());

	}).build();

	public static final ItemGroup ITEM_GROUP_MACHINES = FabricItemGroupBuilder.create(new ModIdentifier("tab_machines")).icon(() -> new ItemStack(ModItems.NASA_WORKBENCH)).appendItems(stacks -> {
		stacks.add(ModItems.COAL_GENERATOR.getDefaultStack());
		stacks.add(ModItems.COMPRESSOR.getDefaultStack());
		stacks.add(ModItems.NASA_WORKBENCH.getDefaultStack());
		stacks.add(ModItems.FUEL_REFINERY.getDefaultStack());
		stacks.add(ModItems.OXYGEN_LOADER.getDefaultStack());
		stacks.add(ModItems.SOLAR_PANEL.getDefaultStack());
		stacks.add(ModItems.OXYGEN_DISTRIBUTOR.getDefaultStack());
		stacks.add(ModItems.WATER_PUMP.getDefaultStack());
		ItemStack energizer = ModItems.ENERGIZER.getDefaultStack();
		energizer.getOrCreateNbt().putLong("energy", AdAstra.CONFIG.energizer.maxEnergy);
		stacks.add(ModItems.ENERGIZER.getDefaultStack());
		stacks.add(energizer);
		stacks.add(ModItems.CRYO_FREEZER.getDefaultStack());
		stacks.add(ModItems.OXYGEN_SENSOR.getDefaultStack());
		stacks.add(ModItems.STEEL_CABLE.getDefaultStack());
		stacks.add(ModItems.DESH_CABLE.getDefaultStack());
		stacks.add(ModItems.DESH_FLUID_PIPE.getDefaultStack());
		stacks.add(ModItems.OSTRUM_FLUID_PIPE.getDefaultStack());
		stacks.add(ModItems.WRENCH.getDefaultStack());
	}).build();
	ItemGroup ITEM_GROUP_BASICS = FabricItemGroupBuilder.build(new ModIdentifier("tab_basics"), () -> new ItemStack(ModItems.DESH_ENGINE));
	ItemGroup ITEM_GROUP_MATERIALS = FabricItemGroupBuilder.build(new ModIdentifier("tab_materials"), () -> new ItemStack(ModItems.IRON_PLATE));
	ItemGroup ITEM_GROUP_FLAGS = FabricItemGroupBuilder.build(new ModIdentifier("tab_flags"), () -> new ItemStack(ModBlocks.FLAG_PURPLE));
	ItemGroup ITEM_GROUP_GLOBES = FabricItemGroupBuilder.build(new ModIdentifier("tab_globes"), () -> new ItemStack(ModItems.GLACIO_GLOBE));
	ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(new ModIdentifier("tab_blocks"), () -> new ItemStack(ModBlocks.MOON_IRON_ORE));
	ItemGroup ITEM_GROUP_SPAWN_EGGS = FabricItemGroupBuilder.build(new ModIdentifier("tab_spawn_eggs"), () -> new ItemStack(ModItems.LUNARIAN_SPAWN_EGG));
}