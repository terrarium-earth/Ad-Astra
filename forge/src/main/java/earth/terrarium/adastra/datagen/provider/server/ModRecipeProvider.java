package earth.terrarium.adastra.datagen.provider.server;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.datagen.builder.SeparatingRecipeBuilder;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
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
        createSeparating(writer, 2, 200,
            FluidHooks.newFluidHolder(Fluids.WATER, FluidHooks.buckets(0.003), null),
            FluidHooks.newFluidHolder(ModFluids.HYDROGEN.get(), FluidHooks.buckets(0.002), null),
            FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), FluidHooks.buckets(0.001), null)
        );
    }

    public static void createSeparating(Consumer<FinishedRecipe> writer, int cookingtime, int energy, FluidHolder ingredient, FluidHolder resultFluid1, FluidHolder resultFluid2) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(ingredient.getFluid()));
        ResourceLocation resultFluid1Id = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid1.getFluid()));
        ResourceLocation resultFluid2Id = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid2.getFluid()));

        SeparatingRecipeBuilder builder = new SeparatingRecipeBuilder(ingredient, resultFluid1, resultFluid2).cookingTime(cookingtime).energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "%s_and_%s_from_%s".formatted(resultFluid1Id.getPath(), resultFluid2Id.getPath(), ingredientId.getPath())));
    }
}
