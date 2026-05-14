package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.OxygenLoaderBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.ingredient.SizedFluidIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record OxygenLoadingRecipe(
    int cookingTime, int energy,
    SizedFluidIngredient input,
    ResourceStack<FluidResource> result
) implements CodecRecipe<RecipeInput> {

    public static final MapCodec<OxygenLoadingRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(OxygenLoadingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(OxygenLoadingRecipe::energy),
            SizedFluidIngredient.FLAT_CODEC.fieldOf("input").forGetter(OxygenLoadingRecipe::input),
            ResourceStack.FLUID_CODEC.fieldOf("result").forGetter(OxygenLoadingRecipe::result)
        ).apply(instance, OxygenLoadingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, OxygenLoadingRecipe> NETWORK_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        OxygenLoadingRecipe::cookingTime,
        ByteBufCodecs.INT,
        OxygenLoadingRecipe::energy,
        SizedFluidIngredient.STREAM_CODEC,
        OxygenLoadingRecipe::input,
        ResourceStack.FLUID_STREAM_CODEC,
        OxygenLoadingRecipe::result,
        OxygenLoadingRecipe::new
    );

    @Override
    public boolean matches(@NotNull RecipeInput container, @NotNull Level level) {
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
    public CodecRecipeSerializer<? extends CodecRecipe<RecipeInput>> serializer() {
        return ModRecipeSerializers.OXYGEN_LOADING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.OXYGEN_LOADING.get();
    }
}
