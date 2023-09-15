package earth.terrarium.adastra.datagen.provider.server;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.datagen.builder.HydraulicPressingRecipeBuilder;
import earth.terrarium.adastra.datagen.builder.RefiningRecipeBuilder;
import earth.terrarium.adastra.datagen.builder.SeparatingRecipeBuilder;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        createSeparating(writer, 2, 100,
            FluidHooks.newFluidHolder(Fluids.WATER, FluidHooks.buckets(0.003), null),
            FluidHooks.newFluidHolder(ModFluids.HYDROGEN.get(), FluidHooks.buckets(0.002), null),
            FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), FluidHooks.buckets(0.001), null)
        );

        createRefining(writer, 2, 30,
            FluidHooks.newFluidHolder(ModFluids.OIL.get(), FluidHooks.buckets(0.002), null),
            FluidHooks.newFluidHolder(ModFluids.FUEL.get(), FluidHooks.buckets(0.001), null)
        );

        createHydraulicPressing(writer, 100, 20,
            Items.IRON_INGOT,
            ModItems.IRON_PLATE.get().getDefaultInstance()
        );

        createHydraulicPressing(writer, 800, 20,
            Items.IRON_BLOCK,
            new ItemStack(ModItems.IRON_PLATE.get(), 9)
        );

        createHydraulicPressing(writer, 100, 20,
            ModItemTags.STEEL_INGOTS,
            ModItems.STEEL_INGOT.get().getDefaultInstance()
        );

        createHydraulicPressing(writer, 100, 20,
            ModItemTags.ETRIUM_INGOTS,
            ModItems.ETRIUM_PLATE.get().getDefaultInstance()
        );

        createHydraulicPressing(writer, 800, 20,
            ModItemTags.STEEL_BLOCKS,
            new ItemStack(ModItems.STEEL_INGOT.get(), 9)
        );

        createHydraulicPressing(writer, 800, 20,
            ModItemTags.ETRIUM_BLOCKS,
            new ItemStack(ModItems.ETRIUM_INGOT.get(), 9)
        );
    }

    public static void createSeparating(Consumer<FinishedRecipe> writer, int cookingtime, int energy, FluidHolder ingredient, FluidHolder resultFluid1, FluidHolder resultFluid2) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(ingredient.getFluid()));
        ResourceLocation resultFluid1Id = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid1.getFluid()));
        ResourceLocation resultFluid2Id = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid2.getFluid()));

        var builder = new SeparatingRecipeBuilder(ingredient, resultFluid1, resultFluid2)
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "%s_and_%s_from_separating_%s".formatted(resultFluid1Id.getPath(), resultFluid2Id.getPath(), ingredientId.getPath())));
    }

    public static void createHydraulicPressing(Consumer<FinishedRecipe> writer, int cookingtime, int energy, TagKey<Item> ingredient, ItemStack result) {
        ResourceLocation ingredientId = ingredient.location();
        ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem()));

        var builder = new HydraulicPressingRecipeBuilder(Ingredient.of(ingredient), result)
            .unlockedBy("has_item", has(ingredient))
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "%s_from_hydraulic_pressing_%s".formatted(resultId.getPath(), ingredientId.getPath())));
    }

    public static void createHydraulicPressing(Consumer<FinishedRecipe> writer, int cookingtime, int energy, Item ingredient, ItemStack result) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ingredient));
        ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem()));

        var builder = new HydraulicPressingRecipeBuilder(Ingredient.of(ingredient), result)
            .unlockedBy("has_item", has(ingredient))
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "%s_from_hydraulic_pressing_%s".formatted(resultId.getPath(), ingredientId.getPath())));
    }

    public static void createRefining(Consumer<FinishedRecipe> writer, int cookingtime, int energy, FluidHolder ingredient, FluidHolder resultFluid) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(ingredient.getFluid()));
        ResourceLocation resultFluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid.getFluid()));

        var builder = new RefiningRecipeBuilder(ingredient, resultFluid)
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "%s_from_refining_%s".formatted(resultFluidId.getPath(), ingredientId.getPath())));
    }
}
