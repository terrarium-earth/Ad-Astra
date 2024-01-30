package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.OxygenLoaderBlockEntity;
import earth.terrarium.adastra.common.recipes.base.BotariumByteCodecs;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.QuantifiedFluidIngredient;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record OxygenLoadingRecipe(
    int cookingTime, int energy,
    QuantifiedFluidIngredient input,
    FluidHolder result
) implements CodecRecipe<Container> {

    public static final Codec<OxygenLoadingRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(OxygenLoadingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(OxygenLoadingRecipe::energy),
            QuantifiedFluidIngredient.CODEC.fieldOf("input").forGetter(OxygenLoadingRecipe::input),
            FluidHolder.CODEC.fieldOf("result").forGetter(OxygenLoadingRecipe::result)
        ).apply(instance, OxygenLoadingRecipe::new));

    public static final ByteCodec<OxygenLoadingRecipe> NETWORK_CODEC = ObjectByteCodec.create(
        ByteCodec.INT.fieldOf(OxygenLoadingRecipe::cookingTime),
        ByteCodec.INT.fieldOf(OxygenLoadingRecipe::energy),
        BotariumByteCodecs.QUANTIFIED_FLUID_INGREDIENT_CODEC.fieldOf(OxygenLoadingRecipe::input),
        BotariumByteCodecs.FLUID_HOLDER_CODEC.fieldOf(OxygenLoadingRecipe::result),
        OxygenLoadingRecipe::new
    );

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (!(container instanceof OxygenLoaderBlockEntity entity)) return false;
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
    public CodecRecipeSerializer<? extends CodecRecipe<Container>> serializer() {
        return ModRecipeSerializers.OXYGEN_LOADING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.OXYGEN_LOADING.get();
    }
}
