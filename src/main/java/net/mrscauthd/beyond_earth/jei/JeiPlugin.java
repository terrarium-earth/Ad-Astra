package net.mrscauthd.beyond_earth.jei;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.capability.oxygen.OxygenUtil;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeTypes;
import net.mrscauthd.beyond_earth.crafting.CompressingRecipe;
import net.mrscauthd.beyond_earth.crafting.FuelRefiningRecipe;
import net.mrscauthd.beyond_earth.crafting.GeneratingRecipe;
import net.mrscauthd.beyond_earth.crafting.IngredientStack;
import net.mrscauthd.beyond_earth.crafting.OxygenBubbleDistributorRecipe;
import net.mrscauthd.beyond_earth.crafting.OxygenLoaderRecipe;
import net.mrscauthd.beyond_earth.crafting.RocketPart;
import net.mrscauthd.beyond_earth.crafting.SpaceStationRecipe;
import net.mrscauthd.beyond_earth.crafting.WorkbenchingRecipe;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gui.helper.GridPlacer;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.gui.helper.IPlacer;
import net.mrscauthd.beyond_earth.gui.helper.RocketPartGridPlacer;
import net.mrscauthd.beyond_earth.gui.screens.coalgenerator.CoalGeneratorGui;
import net.mrscauthd.beyond_earth.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.compressor.CompressorGui;
import net.mrscauthd.beyond_earth.gui.screens.compressor.CompressorGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.fuelrefinery.FuelRefineryGui;
import net.mrscauthd.beyond_earth.gui.screens.fuelrefinery.FuelRefineryGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.nasaworkbench.NasaWorkbenchGui;
import net.mrscauthd.beyond_earth.gui.screens.nasaworkbench.NasaWorkbenchGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGui;
import net.mrscauthd.beyond_earth.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.oxygenloader.OxygenLoaderGui;
import net.mrscauthd.beyond_earth.gui.screens.oxygenloader.OxygenLoaderGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.planetselection.PlanetSelectionGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.rocket.RocketGui;
import net.mrscauthd.beyond_earth.gui.screens.rocket.RocketGuiWindow;
import net.mrscauthd.beyond_earth.gui.screens.rover.RoverGuiWindow;
import net.mrscauthd.beyond_earth.jei.jeiguihandlers.CoalGeneratorGuiContainerHandler;
import net.mrscauthd.beyond_earth.jei.jeiguihandlers.CompressorGuiContainerHandler;
import net.mrscauthd.beyond_earth.jei.jeiguihandlers.PlanetSlecetionGuiJeiHandler;
import net.mrscauthd.beyond_earth.jei.jeiguihandlers.RocketGuiContainerHandler;
import net.mrscauthd.beyond_earth.jei.jeiguihandlers.RoverGuiContainerHandler;
import net.mrscauthd.beyond_earth.machines.tile.CoalGeneratorBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.CompressorBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.FuelRefineryBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.ItemStackToItemStackBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.NASAWorkbenchBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.OxygenMakingBlockEntity;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelper;

	private Map<Fluid, List<ItemStack>> fluidFullItemStacks;
	private List<ItemStack> oxygenFullItemStacks;
	private List<Fluid> fuelTagFluids;

	private ClientLevel getLevel() {
		Minecraft mc = Minecraft.getInstance();
		return mc.level;
	}

	public List<ItemStack> getFluidFullItemStacks(Fluid fluid) {
		return this.fluidFullItemStacks.computeIfAbsent(fluid, this::generateFluidFullIngredients);
	}

	public List<ItemStack> getFluidFullItemStacks(Collection<Fluid> fluids) {
		return fluids.stream().flatMap(f -> this.getFluidFullItemStacks(f).stream()).collect(Collectors.toList());
	}

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(BeyondEarthMod.MODID, "default");
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		int inventorySlotCount = 36;
		// Oxygen Loader
		registration.addRecipeTransferHandler(OxygenLoaderGui.GuiContainer.class, OxygenLoaderJeiCategory.Uid, OxygenMakingBlockEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// Oxygen Bubble Distributor
		registration.addRecipeTransferHandler(OxygenBubbleDistributorGui.GuiContainer.class, OxygenBubbleDistributorJeiCategory.Uid, OxygenMakingBlockEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// Coal Generator
		registration.addRecipeTransferHandler(CoalGeneratorGui.GuiContainer.class, CoalGeneratorJeiCategory.Uid, CoalGeneratorBlockEntity.SLOT_FUEL, 1, CoalGeneratorBlockEntity.SLOT_FUEL + 1, inventorySlotCount);
		// Compressor
		registration.addRecipeTransferHandler(CompressorGui.GuiContainer.class, CompressorJeiCategory.Uid, ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, 1, ItemStackToItemStackBlockEntity.SLOT_OUTPUT + 1, inventorySlotCount);
		// NASA WorkBench
		int workbenchPartSlotStart = 1 + NASAWorkbenchBlockEntity.SLOT_PARTS;
		int workbenchPartSlotCount = NASAWorkbenchBlockEntity.getBasicPartSlots();
		registration.addRecipeTransferHandler(NasaWorkbenchGui.GuiContainer.class, NASAWorkbenchJeiCategory.Uid, workbenchPartSlotStart, workbenchPartSlotCount, workbenchPartSlotStart + workbenchPartSlotCount, inventorySlotCount);
		// Fuel Refinery
		registration.addRecipeTransferHandler(FuelRefineryGui.GuiContainer.class, FuelRefineryJeiCategory.Uid, FuelRefineryBlockEntity.SLOT_INPUT_SOURCE, 1, 0, inventorySlotCount);
		// Rocket tier 1
		registration.addRecipeTransferHandler(RocketGui.GuiContainer.class, RocketTier1JeiCategory.Uid, 0, 1, 0, inventorySlotCount);
		// Rocket tier 2
		registration.addRecipeTransferHandler(RocketGui.GuiContainer.class, RocketTier2JeiCategory.Uid, 0, 1, 0, inventorySlotCount);
		// Rocket tier 3
		registration.addRecipeTransferHandler(RocketGui.GuiContainer.class, RocketTier3JeiCategory.Uid, 0, 1, 0, inventorySlotCount);
		// Rocket tier 4
		registration.addRecipeTransferHandler(RocketGui.GuiContainer.class, RocketTier4JeiCategory.Uid, 0, 1, 0, inventorySlotCount);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(NasaWorkbenchGuiWindow.class, 108, 49, 14, 14, NASAWorkbenchJeiCategory.Uid);
		registration.addGuiContainerHandler(CoalGeneratorGuiWindow.class, new CoalGeneratorGuiContainerHandler());
		registration.addRecipeClickArea(FuelRefineryGuiWindow.class, FuelRefineryGuiWindow.ARROW_LEFT, FuelRefineryGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, FuelRefineryJeiCategory.Uid);
		registration.addGuiContainerHandler(CompressorGuiWindow.class, new CompressorGuiContainerHandler());
		registration.addRecipeClickArea(OxygenLoaderGuiWindow.class, OxygenLoaderGuiWindow.ARROW_LEFT, OxygenLoaderGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, OxygenLoaderJeiCategory.Uid);
		registration.addRecipeClickArea(OxygenBubbleDistributorGuiWindow.class, OxygenBubbleDistributorGuiWindow.ARROW_LEFT, OxygenBubbleDistributorGuiWindow.ARROW_TOP, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT, OxygenBubbleDistributorJeiCategory.Uid);

		registration.addGuiContainerHandler(RocketGuiWindow.class, new RocketGuiContainerHandler());
		registration.addGuiContainerHandler(RoverGuiWindow.class, new RoverGuiContainerHandler());
		registration.addGuiContainerHandler(PlanetSelectionGuiWindow.class, new PlanetSlecetionGuiJeiHandler());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelper = registration.getJeiHelpers();
		registration.addRecipeCategories(new OxygenLoaderJeiCategory(this, jeiHelper.getGuiHelper()));
		registration.addRecipeCategories(new OxygenBubbleDistributorJeiCategory(this, jeiHelper.getGuiHelper()));
		// Coal Generator
		registration.addRecipeCategories(new CoalGeneratorJeiCategory(jeiHelper.getGuiHelper()));
		// NASA Workbench
		registration.addRecipeCategories(new NASAWorkbenchJeiCategory(jeiHelper.getGuiHelper()));
		// Rocket Tier 1
		registration.addRecipeCategories(new RocketTier1JeiCategory(jeiHelper.getGuiHelper()));
		// Rocket Tier 2
		registration.addRecipeCategories(new RocketTier2JeiCategory(jeiHelper.getGuiHelper()));
		// Rocket Tier 3
		registration.addRecipeCategories(new RocketTier3JeiCategory(jeiHelper.getGuiHelper()));
		// Rocket Tier 4
		registration.addRecipeCategories(new RocketTier4JeiCategory(jeiHelper.getGuiHelper()));
		// Compressor
		registration.addRecipeCategories(new CompressorJeiCategory(jeiHelper.getGuiHelper()));
		// Fuel Maker
		registration.addRecipeCategories(new FuelRefineryJeiCategory(this, jeiHelper.getGuiHelper()));
		// Rover
		registration.addRecipeCategories(new RoverJeiCategory(jeiHelper.getGuiHelper()));
		// Space Station
		registration.addRecipeCategories(new SpaceStationJeiCategory(jeiHelper.getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		this.fluidFullItemStacks = new HashMap<>();
		this.oxygenFullItemStacks = this.generateOxygenLoadingItems();
		this.fuelTagFluids = this.generateFuelTagFluids();

		// Oxygen Loader
		registration.addRecipes(generateOxygenLoaderRecipes(), OxygenLoaderJeiCategory.Uid);
		// Oxygen Bubble Distributor
		registration.addRecipes(generateOxygenBubbleDistributorRecipes(), OxygenBubbleDistributorJeiCategory.Uid);
		// Coal Generator
		registration.addRecipes(generateGeneratorRecipes(), CoalGeneratorJeiCategory.Uid);
		// NASA Workbench
		registration.addRecipes(generateWorkbenchRecipes(), NASAWorkbenchJeiCategory.Uid);
		// Rocket Tier 1
		registration.addRecipes(this.generateFuelLoadingRecipes(), RocketTier1JeiCategory.Uid);
		// Rocket Tier 2
		registration.addRecipes(this.generateFuelLoadingRecipes(), RocketTier2JeiCategory.Uid);
		// Rocket Tier 3
		registration.addRecipes(this.generateFuelLoadingRecipes(), RocketTier3JeiCategory.Uid);
		// Rocket Tier 4
		registration.addRecipes(this.generateFuelLoadingRecipes(), RocketTier4JeiCategory.Uid);
		// Rover
		registration.addRecipes(this.generateFuelLoadingRecipes(), RoverJeiCategory.Uid);
		// Compressor
		registration.addRecipes(generateCompressingRecipes(), CompressorJeiCategory.Uid);
		// Fuel Maker
		registration.addRecipes(generateFuelMakerRecipes(), FuelRefineryJeiCategory.Uid);
		// Space Station
		registration.addRecipes(generateSpaceStationRecipes(), SpaceStationJeiCategory.Uid);
		// Oil
		Component oilDescriptionKey = new TranslatableComponent("jei.tooltip." + BeyondEarthMod.MODID + ".oil");
		registration.addIngredientInfo(new ItemStack(ModInit.OIL_BUCKET.get(), 1), VanillaTypes.ITEM, oilDescriptionKey);
		registration.addIngredientInfo(new FluidStack(ModInit.OIL_STILL.get(), 1000), VanillaTypes.FLUID, oilDescriptionKey);
	}

	// Oxygen Loading
	private List<ItemStack> generateOxygenLoadingItems() {
		return ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(OxygenUtil::canReceive).map(OxygenUtil::makeFull).collect(Collectors.toList());
	}

	// Oxygen Loader
	private List<OxygenLoaderRecipe> generateOxygenLoaderRecipes() {
		return BeyondEarthRecipeTypes.OXYGENLOADER.getRecipes(this.getLevel());
	}

	// Oxygen Bubble Distributor
	private List<OxygenBubbleDistributorRecipe> generateOxygenBubbleDistributorRecipes() {
		return BeyondEarthRecipeTypes.OXYGENBUBBLEDISTRIBUTOR.getRecipes(this.getLevel());
	}

	// Generator
	private List<GeneratingRecipe> generateGeneratorRecipes() {
		return BeyondEarthRecipeTypes.GENERATING.getRecipes(this.getLevel());
	}

	// Workbench
	private List<WorkbenchingRecipe> generateWorkbenchRecipes() {
		return BeyondEarthRecipeTypes.WORKBENCHING.getRecipes(this.getLevel());
	}

	// Compressor
	private List<CompressingRecipe> generateCompressingRecipes() {
		return BeyondEarthRecipeTypes.COMPRESSING.getRecipes(this.getLevel());
	}

	// Fuel Maker
	private List<ItemStack> generateFluidFullIngredients(Fluid fluid) {
		return ForgeRegistries.ITEMS.getValues().stream().map(i -> new ItemStack(i)).filter(is -> FluidUtil2.canFill(is, fluid)).map(is -> FluidUtil2.makeFull(is, fluid)).collect(Collectors.toList());
	}

	private List<FuelRefiningRecipe> generateFuelMakerRecipes() {
		return BeyondEarthRecipeTypes.FUELREFINING.getRecipes(this.getLevel());
	}

	// Fuel Loading
	private List<Fluid> generateFuelTagFluids() {
		return ForgeRegistries.FLUIDS.getValues().stream().filter(f -> f.isSource(f.defaultFluidState()) && Methods.tagCheck(f, ModInit.FLUID_VEHICLE_FUEL_TAG)).collect(Collectors.toList());
	}

	private List<FuelLoadingRecipe> generateFuelLoadingRecipes() {
		List<ItemStack> fuelTagBuckets = new ArrayList<>();

		for (Fluid fluid : this.fuelTagFluids) {
			fuelTagBuckets.add(new ItemStack(fluid.getBucket()));
		}

		FuelLoadingRecipe recipe = new FuelLoadingRecipe(fuelTagBuckets, this.fuelTagFluids);

		List<FuelLoadingRecipe> recipes = new ArrayList<>();
		recipes.add(recipe);
		return recipes;
	}

	// Space Station
	private List<SpaceStationRecipe> generateSpaceStationRecipes() {
		List<SpaceStationRecipe> recipes = new ArrayList<>();
		this.getLevel().getRecipeManager().byKey(SpaceStationRecipe.KEY).ifPresent(r -> recipes.add((SpaceStationRecipe) r));

		return recipes;
	}

	public static class FuelLoadingRecipe {
		private final List<ItemStack> fuelTagBuckets;
		private final List<Fluid> fluids;

		public FuelLoadingRecipe(List<ItemStack> fuelTagBuckets, List<Fluid> fluids) {
			this.fuelTagBuckets = Collections.unmodifiableList(fuelTagBuckets);
			this.fluids = Collections.unmodifiableList(fluids);
		}

		public List<ItemStack> getFuelTagBuckets() {
			return this.fuelTagBuckets;
		}

		public List<FluidStack> getFluidStacks(int amount) {
			return this.getFluid().stream().map(f -> new FluidStack(f, amount)).collect(Collectors.toList());
		}

		public List<Fluid> getFluid() {
			return this.fluids;
		}
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		// Oxygen Loader
		registration.addRecipeCatalyst(new ItemStack(ModInit.OXYGEN_LOADER_BLOCK.get()), OxygenLoaderJeiCategory.Uid);
		// Oxygen Bubble Distributor
		registration.addRecipeCatalyst(new ItemStack(ModInit.OXYGEN_BUBBLE_DISTRIBUTOR_BLOCK.get()), OxygenBubbleDistributorJeiCategory.Uid);
		// Coal Generator
		registration.addRecipeCatalyst(new ItemStack(ModInit.COAL_GENERATOR_BLOCK.get()), CoalGeneratorJeiCategory.Uid);
		// workbench
		registration.addRecipeCatalyst(new ItemStack(ModInit.NASA_WORKBENCH_ITEM.get()), NASAWorkbenchJeiCategory.Uid);
		// Compressor
		registration.addRecipeCatalyst(new ItemStack(ModInit.COMPRESSOR_BLOCK.get()), CompressorJeiCategory.Uid);
		// FuelMaker
		registration.addRecipeCatalyst(new ItemStack(ModInit.FUEL_REFINERY_BLOCK.get()), FuelRefineryJeiCategory.Uid);
		// Rocket Tier 1
		registration.addRecipeCatalyst(new ItemStack(ModInit.TIER_1_ROCKET_ITEM.get()), RocketTier1JeiCategory.Uid, SpaceStationJeiCategory.Uid);
		// Rocket Tier 2
		registration.addRecipeCatalyst(new ItemStack(ModInit.TIER_2_ROCKET_ITEM.get()), RocketTier2JeiCategory.Uid, SpaceStationJeiCategory.Uid);
		// Rocket Tier 3
		registration.addRecipeCatalyst(new ItemStack(ModInit.TIER_3_ROCKET_ITEM.get()), RocketTier3JeiCategory.Uid, SpaceStationJeiCategory.Uid);
		// Rocket Tier 4
		registration.addRecipeCatalyst(new ItemStack(ModInit.TIER_4_ROCKET_ITEM.get()), RocketTier4JeiCategory.Uid, SpaceStationJeiCategory.Uid);
		// Rover
		registration.addRecipeCatalyst(new ItemStack(ModInit.ROVER_ITEM.get()), RoverJeiCategory.Uid);
	}

	public static class OxygenLoaderJeiCategory implements IRecipeCategory<OxygenLoaderRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "oxygen_loader");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenLoaderJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslatableComponent("container." + BeyondEarthMod.MODID + ".oxygen_loader");
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/oxygen_loader_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, OxygenLoaderRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 25, 9);
			input.addItemStacks(this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));

			IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 39);
			output.addItemStacks(this.plugin.oxygenFullItemStacks);

			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, INPUT_TANK_LEFT, INPUT_TANK_TOP);
			tank.setFluidRenderer(1, false, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setOverlay(fluidOverlay, 0, 0);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getInput().toStacks());
		}

		@Override
		public void draw(OxygenLoaderRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			GuiHelper.drawOxygenTank(stack, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, 1.0D);
		}

		@Override
		public List<Component> getTooltipStrings(OxygenLoaderRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(FuelRefineryBlockEntity.ENERGY_PER_TICK)).build());
			} else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getValueText(GaugeValueHelper.getOxygen(recipe.getOxygen())).build());
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenLoaderRecipe> getRecipeClass() {
			return OxygenLoaderRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	public static class OxygenBubbleDistributorJeiCategory implements IRecipeCategory<OxygenBubbleDistributorRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "oxygen_bubble_distributor");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public OxygenBubbleDistributorJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslatableComponent("container." + BeyondEarthMod.MODID + ".oxygen_bubble_distributor");
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/oxygen_bubble_distributor_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, OxygenBubbleDistributorRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 25, 9);
			input.addItemStacks(this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));

			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, INPUT_TANK_LEFT, INPUT_TANK_TOP);
			tank.setFluidRenderer(1, false, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setOverlay(fluidOverlay, 0, 0);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getInput().toStacks());
		}

		@Override
		public void draw(OxygenBubbleDistributorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			GuiHelper.drawOxygenTank(stack, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP, 1.0D);
		}

		@Override
		public List<Component> getTooltipStrings(OxygenBubbleDistributorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(FuelRefineryBlockEntity.ENERGY_PER_TICK)).build());
			} else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getValueText(GaugeValueHelper.getOxygen(recipe.getOxygen())).build());
			}

			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends OxygenBubbleDistributorRecipe> getRecipeClass() {
			return OxygenBubbleDistributorRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	public static class CoalGeneratorJeiCategory implements IRecipeCategory<GeneratingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "coal_generator");
		public static final int FIRE_LEFT = 45;
		public static final int FIRE_TOP = 45;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;

		private final Component title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> fires;
		private final LoadingCache<Integer, IDrawableAnimated> energies;

		public CoalGeneratorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BeyondEarthMod.MODID + ".coal_generator");
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/generator_gui_jei.png"), 0, 0, 144, 84);
			this.fires = createFires(guiHelper);
			this.energies = createGeneratingEnergies(guiHelper);
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, GeneratingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 45, 26);
			input.addIngredients(recipe.getInput());
		}

		@Override
		public void draw(GeneratingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

			int burnTime = recipe.getBurnTime();
			this.fires.getUnchecked(burnTime).draw(stack, FIRE_LEFT, FIRE_TOP);
			this.energies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			drawTextTime(stack, this.getBackground(), burnTime);
		}

		@Override
		public List<Component> getTooltipStrings(GeneratingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getFireBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getValueText(GaugeValueHelper.getBurnTime(recipe.getBurnTime())).build());
			} else if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getGeneratingPerTickText(GaugeValueHelper.getEnergy(CoalGeneratorBlockEntity.ENERGY_PER_TICK)).build());
			}
			return Collections.emptyList();
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends GeneratingRecipe> getRecipeClass() {
			return GeneratingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		public Rectangle2d getFireBounds() {
			return GuiHelper.getFireBounds(FIRE_LEFT, FIRE_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}
	}

	public static class NASAWorkbenchJeiCategory implements IRecipeCategory<WorkbenchingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "nasa_workbench"); // muss klein sein !
		private final Component title;
		private final IDrawable background;

		public NASAWorkbenchJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BeyondEarthMod.MODID + ".nasa_workbench");
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/nasaworkbenchjei.png"), 0, 0, 176, 122);
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, WorkbenchingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			builder.moveRecipeTransferButton(background.getWidth() - 20, background.getHeight() - 20);

			GridPlacer placer = new GridPlacer();
			placeRocketParts(39, 8, 1, placer::placeBottom, ModInit.ROCKET_PART_NOSE.get(), builder, recipe);
			placeRocketParts(30, 26, 2, placer::placeBottom, ModInit.ROCKET_PART_BODY.get(), builder, recipe);
			placeRocketParts(30, 80, 1, placer::placeRight, ModInit.ROCKET_PART_TANK.get(), builder, recipe);
			placeRocketParts(12, 80, 1, placer::placeBottom, ModInit.ROCKET_PART_FIN_LEFT.get(), builder, recipe);
			placeRocketParts(66, 80, 1, placer::placeBottom, ModInit.ROCKET_PART_FIN_RIGHT.get(), builder, recipe);
			placeRocketParts(39, 98, 1, placer::placeBottom, ModInit.ROCKET_PART_ENGINE.get(), builder, recipe);

			IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 127, 73);
			output.addItemStack(recipe.getOutput());
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends WorkbenchingRecipe> getRecipeClass() {
			return WorkbenchingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}
	}

	public static void placeRocketParts(int left, int top, int mod, IPlacer placer, RocketPart part, IRecipeLayoutBuilder builder, WorkbenchingRecipe recipe) {
		List<Ingredient> ingredients = recipe.getParts().get(part);

		RocketPartGridPlacer.place(left, top, mod, placer, part, (i, bounds) -> {
			Ingredient ingredient = (ingredients != null && i < ingredients.size()) ? ingredients.get(i) : Ingredient.EMPTY;
			IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, bounds.getX(), bounds.getY());
			slot.addIngredients(ingredient);
		});
	}

	public static IDrawableStatic createFireStatic(IGuiHelper guiHelper) {
		return drawableBuilder(guiHelper, GuiHelper.FIRE_PATH, GuiHelper.FIRE_WIDTH, GuiHelper.FIRE_HEIGHT).build();
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper) {
		return createFireAnimated(guiHelper, 200);
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper, int ticks) {
		return createFireAnimated(guiHelper, createFireStatic(guiHelper), ticks);
	}

	public static IDrawableAnimated createFireAnimated(IGuiHelper guiHelper, IDrawableStatic fireStatic, int ticks) {
		return guiHelper.createAnimatedDrawable(fireStatic, ticks, IDrawableAnimated.StartDirection.TOP, true);
	}

	public static IDrawableBuilder drawableBuilder(IGuiHelper guiHelper, ResourceLocation path, int width, int height) {
		return guiHelper.drawableBuilder(path, 0, 0, width, height).setTextureSize(width, height);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createFires(IGuiHelper guiHelper) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.FIRE_PATH, GuiHelper.FIRE_WIDTH, GuiHelper.FIRE_HEIGHT).buildAnimated(time, IDrawableAnimated.StartDirection.TOP, true);
			}
		});
	}

	public static LoadingCache<Integer, IDrawableAnimated> createArrows(IGuiHelper guiHelper) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.ARROW_PATH, GuiHelper.ARROW_WIDTH, GuiHelper.ARROW_HEIGHT).buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}

	public static LoadingCache<Integer, IDrawableAnimated> createEnergies(IGuiHelper guiHelper, boolean inverted) {
		return CacheBuilder.newBuilder().build(new CacheLoader<Integer, IDrawableAnimated>() {
			@Override
			public IDrawableAnimated load(Integer time) {
				return drawableBuilder(guiHelper, GuiHelper.ENERGY_PATH, GuiHelper.ENERGY_WIDTH, GuiHelper.ENERGY_HEIGHT).buildAnimated(time, inverted ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.BOTTOM, inverted);
			}
		});

	}

	public static LoadingCache<Integer, IDrawableAnimated> createUsingEnergies(IGuiHelper guiHelper) {
		return createEnergies(guiHelper, true);
	}

	public static LoadingCache<Integer, IDrawableAnimated> createGeneratingEnergies(IGuiHelper guiHelper) {
		return createEnergies(guiHelper, false);
	}

	public static void drawText(PoseStack stack, IDrawable background, String text) {
		Minecraft mc = Minecraft.getInstance();
		Font font = mc.font;
		int stringWidth = font.width(text);
		font.draw(stack, text, background.getWidth() - 5 - stringWidth, background.getHeight() - font.lineHeight - 5, 0x808080);
	}

	public static void drawTextTime(PoseStack stack, IDrawable background, int ticks) {
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String text = numberInstance.format(ticks / 20.0F) + "s";

		drawText(stack, background, text);
	}

	public static class RocketTier1JeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "rocket_t1");

		private final Component title;
		private final IDrawable background;
		private final IDrawable icon;

		public RocketTier1JeiCategory(IGuiHelper guiHelper) {
			this.title = ModInit.TIER_1_ROCKET.get().getDescription();
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
			this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModInit.TIER_1_ROCKET_ITEM.get()));
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, FuelLoadingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 14, 19);
			input.addItemStacks(recipe.getFuelTagBuckets());

			int capacity = FluidUtil2.BUCKET_SIZE;
			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, 66, 12);
			tank.setFluidRenderer(capacity, true, 46, 46);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getFluidStacks(capacity));
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return this.icon;
		}
	}

	public static class RocketTier2JeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "rocket_t2");

		private final Component title;
		private final IDrawable background;
		private final IDrawable icon;

		public RocketTier2JeiCategory(IGuiHelper guiHelper) {
			this.title = ModInit.TIER_2_ROCKET.get().getDescription();
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
			this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModInit.TIER_2_ROCKET_ITEM.get()));
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, FuelLoadingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 14, 19);
			input.addItemStacks(recipe.getFuelTagBuckets());

			int capacity = FluidUtil2.BUCKET_SIZE * 3;
			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, 66, 12);
			tank.setFluidRenderer(capacity, true, 46, 46);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getFluidStacks(capacity));
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return this.icon;
		}
	}

	public static class RocketTier3JeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "rocket_t3");

		private final Component title;
		private final IDrawable background;
		private final IDrawable icon;

		public RocketTier3JeiCategory(IGuiHelper guiHelper) {
			this.title = ModInit.TIER_3_ROCKET.get().getDescription();
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
			this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModInit.TIER_3_ROCKET_ITEM.get()));
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, FuelLoadingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 14, 19);
			input.addItemStacks(recipe.getFuelTagBuckets());

			int capacity = FluidUtil2.BUCKET_SIZE * 3;
			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, 66, 12);
			tank.setFluidRenderer(capacity, true, 46, 46);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getFluidStacks(capacity));
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return this.icon;
		}
	}

	public static class RocketTier4JeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "rocket_t4");

		private final Component title;
		private final IDrawable background;
		private final IDrawable icon;

		public RocketTier4JeiCategory(IGuiHelper guiHelper) {
			this.title = ModInit.TIER_4_ROCKET.get().getDescription();
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/rocket_gui_jei.png"), 0, 0, 128, 71);
			this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModInit.TIER_4_ROCKET_ITEM.get()));
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, FuelLoadingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 14, 19);
			input.addItemStacks(recipe.getFuelTagBuckets());

			int capacity = FluidUtil2.BUCKET_SIZE * 3;
			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, 66, 12);
			tank.setFluidRenderer(capacity, true, 46, 46);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getFluidStacks(capacity));
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return this.icon;
		}
	}

	public static class CompressorJeiCategory implements IRecipeCategory<CompressingRecipe> {

		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "compressor");
		public static final int ARROW_LEFT = 36;
		public static final int ARROW_TOP = 29;
		public static final int ENERGY_LEFT = 103;
		public static final int ENERGY_TOP = 15;

		private final Component title;
		private final IDrawable background;
		private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public CompressorJeiCategory(IGuiHelper guiHelper) {
			this.title = new TranslatableComponent("container." + BeyondEarthMod.MODID + ".compressor");
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/compressor_gui_jei.png"), 0, 0, 144, 84);
			this.cachedArrows = createArrows(guiHelper);
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 15, 30);
			input.addIngredients(recipe.getInput());

			IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.INPUT, 70, 29);
			output.addItemStack(recipe.getOutput());
		}

		@Override
		public void draw(CompressingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

			int cookTime = recipe.getCookTime();
			this.cachedArrows.getUnchecked(cookTime).draw(stack, ARROW_LEFT, ARROW_TOP);
			this.cachedEnergies.getUnchecked(cookTime).draw(stack, ENERGY_LEFT, ENERGY_TOP);
			drawTextTime(stack, this.getBackground(), cookTime);
		}

		@Override
		public List<Component> getTooltipStrings(CompressingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList((GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(CompressorBlockEntity.ENERGY_PER_TICK)).build()));
			} else {
				return Collections.emptyList();
			}
		}

		private Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends CompressingRecipe> getRecipeClass() {
			return CompressingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}
	}

	public static class FuelRefineryJeiCategory implements IRecipeCategory<FuelRefiningRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "fuel_refinery");
		public static final int INPUT_TANK_LEFT = 8;
		public static final int INPUT_TANK_TOP = 8;
		public static final int OUTPUT_TANK_LEFT = 74;
		public static final int OUTPUT_TANK_TOP = 8;
		public static final int ENERGY_LEFT = 114;
		public static final int ENERGY_TOP = 8;

		private final JeiPlugin plugin;
		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;
		private final LoadingCache<Integer, IDrawableAnimated> cachedEnergies;

		public FuelRefineryJeiCategory(JeiPlugin plugin, IGuiHelper guiHelper) {
			this.plugin = plugin;
			this.title = new TranslatableComponent("container." + BeyondEarthMod.MODID + ".fuel_refinery");
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/fuel_refinery_jei.png"), 0, 0, 148, 64);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
			this.cachedEnergies = createUsingEnergies(guiHelper);
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, FuelRefiningRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder inputItem = builder.addSlot(RecipeIngredientRole.INPUT, 25, 9);
			inputItem.addItemStacks(this.plugin.getFluidFullItemStacks(recipe.getInput().getFluids()));

			IRecipeSlotBuilder outputItem = builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 39);
			outputItem.addItemStacks(this.plugin.getFluidFullItemStacks(recipe.getOutput().getFluids()));

			IRecipeSlotBuilder inputTank = builder.addSlot(RecipeIngredientRole.INPUT, INPUT_TANK_LEFT, INPUT_TANK_TOP);
			inputTank.setFluidRenderer(1, false, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setOverlay(fluidOverlay, 0, 0);
			inputTank.addIngredients(VanillaTypes.FLUID, recipe.getInput().toStacks());

			IRecipeSlotBuilder outputTank = builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
			outputTank.setFluidRenderer(1, false, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setOverlay(fluidOverlay, 0, 0);
			outputTank.addIngredients(VanillaTypes.FLUID, recipe.getOutput().toStacks());
		}

		@Override
		public void draw(FuelRefiningRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

			this.cachedEnergies.getUnchecked(200).draw(stack, ENERGY_LEFT, ENERGY_TOP);
		}

		@Override
		public List<Component> getTooltipStrings(FuelRefiningRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
			if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
				return Collections.singletonList(GaugeTextHelper.getUsingPerTickText(GaugeValueHelper.getEnergy(FuelRefineryBlockEntity.ENERGY_PER_TICK)).build());
			} else {
				return Collections.emptyList();
			}
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelRefiningRecipe> getRecipeClass() {
			return FuelRefiningRecipe.class;
		}

		@Override
		public Component getTitle() {
			return this.title;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}

		public Rectangle2d getInputTankBounds() {
			return GuiHelper.getFluidTankBounds(INPUT_TANK_LEFT, INPUT_TANK_TOP);
		}

		public Rectangle2d getOutputTankBounds() {
			return GuiHelper.getFluidTankBounds(OUTPUT_TANK_LEFT, OUTPUT_TANK_TOP);
		}

		public Rectangle2d getEnergyBounds() {
			return GuiHelper.getEnergyBounds(ENERGY_LEFT, ENERGY_TOP);
		}
	}

	public static class RoverJeiCategory implements IRecipeCategory<FuelLoadingRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "rover");

		private final Component title;
		private final IDrawable background;
		private final IDrawable fluidOverlay;

		public RoverJeiCategory(IGuiHelper guiHelper) {
			this.title = ModInit.ROVER.get().getDescription();
			this.background = guiHelper.createDrawable(new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/rover_jei.png"), 0, 0, 144, 84);
			this.fluidOverlay = guiHelper.drawableBuilder(GuiHelper.FLUID_TANK_PATH, 0, 0, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setTextureSize(GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).build();
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, FuelLoadingRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 8, 60);
			input.addItemStacks(recipe.getFuelTagBuckets());

			int capacity = FluidUtil2.BUCKET_SIZE * 3;
			IRecipeSlotBuilder tank = builder.addSlot(RecipeIngredientRole.INPUT, 9, 8);
			tank.setFluidRenderer(capacity, true, GuiHelper.FLUID_TANK_WIDTH, GuiHelper.FLUID_TANK_HEIGHT).setOverlay(fluidOverlay, 0, 0);
			tank.addIngredients(VanillaTypes.FLUID, recipe.getFluidStacks(capacity));
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends FuelLoadingRecipe> getRecipeClass() {
			return FuelLoadingRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public IDrawable getIcon() {
			return null;
		}
	}

	public static class SpaceStationJeiCategory implements IRecipeCategory<SpaceStationRecipe> {
		public static final ResourceLocation Uid = new ResourceLocation(BeyondEarthMod.MODID, "space_station");
		public static final ResourceLocation BACKGROUND = new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/space_station_background.png");
		public static final ResourceLocation ICON = new ResourceLocation(BeyondEarthMod.MODID, "textures/jei/space_station_icon.png");
		public static final int SLOTS_X_CENTER = 72;
		public static final int SLOTS_Y_TOP = 6;
		public static final int SLOTS_X_OFFSET = 18;
		public static final int SLOTS_Y_OFFSET = 18;

		private final Component title;
		private final Component[] tooltips;
		private final IDrawable background;
		private final IDrawable icon;
		private final IDrawable slot;

		public SpaceStationJeiCategory(IGuiHelper guiHelper) {
			String path = BeyondEarthMod.MODID + ".space_station";
			this.title = new TranslatableComponent("jei.category." + path);
			this.tooltips = Arrays.stream(new TranslatableComponent("jei.tooltip." + path).getString().split("\n")).map(TextComponent::new).toArray(Component[]::new);
			this.background = guiHelper.createDrawable(BACKGROUND, 0, 0, 144, 51);
			this.icon = guiHelper.drawableBuilder(ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
			this.slot = guiHelper.getSlotDrawable();
		}

		@Override
		public void setRecipe(IRecipeLayoutBuilder builder, SpaceStationRecipe recipe, IFocusGroup focuses) {
			IRecipeCategory.super.setRecipe(builder, recipe, focuses);

			NonNullList<IngredientStack> ingredientStacks = recipe.getIngredientStacks();
			int count = ingredientStacks.size();

			for (int i = 0; i < count; i++) {
				int[] pos = this.getSpaceStationItemPosition(i, count);

				IngredientStack ingredientStack = ingredientStacks.get(i);
				IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, pos[0] + 1, pos[1] + 1);
				slot.addItemStacks(Arrays.asList(ingredientStack.getItems()));
			}
		}

		public int[] getSpaceStationItemPosition(int index, int count) {
			int xIndex = index % count;
			int yIndex = index / count;
			int slots_width = count * SLOTS_X_OFFSET;
			int xPosition = SLOTS_X_CENTER + (xIndex * SLOTS_X_OFFSET) - (slots_width / 2);
			int yPosition = SLOTS_Y_TOP + (yIndex * SLOTS_Y_OFFSET);
			return new int[] { xPosition, yPosition };
		}

		@Override
		public void draw(SpaceStationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
			IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
			NonNullList<IngredientStack> ingredientStacks = recipe.getIngredientStacks();
			int count = ingredientStacks.size();

			for (int i = 0; i < count; i++) {
				int[] pos = this.getSpaceStationItemPosition(i, count);
				this.slot.draw(stack, pos[0], pos[1]);
			}

			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			int tooltipYOffset = this.getSpaceStationItemPosition(ingredientStacks.size() - 1, count)[1] + SLOTS_Y_OFFSET + 4;
			Component[] tooltips = this.getTooltip();

			for (int i = 0; i < tooltips.length; i++) {
				Component tooltip = tooltips[i];
				int tooltipWidth = font.width(tooltip);
				font.draw(stack, tooltip, SLOTS_X_CENTER - (tooltipWidth / 2), tooltipYOffset + font.lineHeight * i, 0xFF404040);
			}
		}

		@Override
		public ResourceLocation getUid() {
			return Uid;
		}

		@Override
		public Class<? extends SpaceStationRecipe> getRecipeClass() {
			return SpaceStationRecipe.class;
		}

		@Override
		public Component getTitle() {
			return title;
		}

		public Component[] getTooltip() {
			return this.tooltips;
		}

		@Override
		public IDrawable getBackground() {
			return this.background;
		}

		@Override
		public IDrawable getIcon() {
			return this.icon;
		}
	}
}