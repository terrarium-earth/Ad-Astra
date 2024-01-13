package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.FuelRefineryBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.QuantifiedFluidIngredient;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record RefiningRecipe(
    int cookingTime, int energy,
    QuantifiedFluidIngredient input,
    FluidHolder result
) implements CodecRecipe<Container> {

    public static final Codec<RefiningRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(RefiningRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(RefiningRecipe::energy),
            QuantifiedFluidIngredient.CODEC.fieldOf("input").forGetter(RefiningRecipe::input),
            FluidHolder.CODEC.fieldOf("result").forGetter(RefiningRecipe::result)
        ).apply(instance, RefiningRecipe::new));

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (!(container instanceof FuelRefineryBlockEntity entity)) return false;
        if (!input.test(entity.getFluidContainer().getFirstFluid())) return false;
        if (entity.getEnergyStorage().internalExtract(energy, true) < energy) return false;
        if (entity.getFluidContainer().getFluids().get(1).getFluidAmount() >= entity.getFluidContainer().getTankCapacity(1)) {
            return false;
        }
        return entity.getFluidContainer().internalExtract(entity.getFluidContainer().getFirstFluid()
                .copyWithAmount(input.getFluidAmount()), true)
            .getFluidAmount() >= input.getFluidAmount();
    }

    @Override
    public <T extends CodecRecipe<Container>> CodecRecipeSerializer<T> serializer() {
        return ModRecipeSerializers.REFINING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.REFINING.get();
    }
}
