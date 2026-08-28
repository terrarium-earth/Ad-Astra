package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.base.ContainerRecipeWrapper;
import earth.terrarium.adastra.common.blockentities.machines.FuelRefineryBlockEntity;
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

public record RefiningRecipe(
    int cookingTime, int energy,
    SizedFluidIngredient input,
    ResourceStack<FluidResource> result
) implements CodecRecipe<RecipeInput> {

    public static final MapCodec<RefiningRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(RefiningRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(RefiningRecipe::energy),
            SizedFluidIngredient.FLAT_CODEC.fieldOf("input").forGetter(RefiningRecipe::input),
            ResourceStack.FLUID_CODEC.fieldOf("result").forGetter(RefiningRecipe::result)
        ).apply(instance, RefiningRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RefiningRecipe> NETWORK_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        RefiningRecipe::cookingTime,
        ByteBufCodecs.INT,
        RefiningRecipe::energy,
        SizedFluidIngredient.STREAM_CODEC,
        RefiningRecipe::input,
        ResourceStack.FLUID_STREAM_CODEC,
        RefiningRecipe::result,
        RefiningRecipe::new
    );

    @Override
    public boolean matches(@NotNull RecipeInput recipeInput, @NotNull Level level) {
        if (!(recipeInput instanceof ContainerRecipeWrapper wrapper)) return false;
        if (!(wrapper.container() instanceof FuelRefineryBlockEntity entity)) return false;
        if (!input.test(entity.getFluidContainer().getContents(0))) return false;
        if (entity.getEnergyStorage().extract(energy, true) < energy) return false;
        if (entity.getFluidContainer().getAmount(1) >= entity.getFluidContainer().getLimit(1, FluidResource.BLANK)) {
            return false;
        }
        return entity.getFluidContainer().extract(entity.getFluidContainer().getResource(0), input.getAmount(), true) >= input.getAmount();
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<RecipeInput>> serializer() {
        return ModRecipeSerializers.REFINING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.REFINING.get();
    }
}
