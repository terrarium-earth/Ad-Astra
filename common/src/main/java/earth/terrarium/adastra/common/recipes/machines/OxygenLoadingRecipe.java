package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.blockentities.machines.OxygenLoaderBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record OxygenLoadingRecipe(
    ResourceLocation id,
    int cookingTime, int energy,
    FluidHolder ingredient,
    FluidHolder resultFluid
) implements CodecRecipe<Container> {

    public static Codec<OxygenLoadingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(OxygenLoadingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(OxygenLoadingRecipe::energy),
            FluidHolder.CODEC.fieldOf("ingredient").forGetter(OxygenLoadingRecipe::ingredient),
            FluidHolder.CODEC.fieldOf("result_fluid").forGetter(OxygenLoadingRecipe::resultFluid)
        ).apply(instance, OxygenLoadingRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (!(container instanceof OxygenLoaderBlockEntity entity)) return false;
        if (!ingredient.matches(entity.getFluidContainer().getFluids().get(0))) return false;
        if (entity.getEnergyStorage().internalExtract(energy, true) < energy) return false;
        if (entity.getFluidContainer().getFluids().get(1).getFluidAmount() >= entity.getFluidContainer().getTankCapacity(1)) {
            return false;
        }
        return entity.getFluidContainer().internalExtract(ingredient, true).getFluidAmount() >= ingredient.getFluidAmount();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.OXYGEN_LOADING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.OXYGEN_LOADING.get();
    }
}
