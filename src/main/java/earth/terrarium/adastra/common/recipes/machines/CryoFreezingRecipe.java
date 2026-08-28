package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.base.ContainerRecipeWrapper;
import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CryoFreezingRecipe(
    int cookingTime, int energy,
    Ingredient input,
    ResourceStack<FluidResource> result
) implements CodecRecipe<RecipeInput> {

    public static final MapCodec<CryoFreezingRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(CryoFreezingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CryoFreezingRecipe::energy),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CryoFreezingRecipe::input),
            ResourceStack.FLUID_CODEC.fieldOf("result").forGetter(CryoFreezingRecipe::result)
        ).apply(instance, CryoFreezingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CryoFreezingRecipe> NETWORK_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        CryoFreezingRecipe::cookingTime,
        ByteBufCodecs.INT,
        CryoFreezingRecipe::energy,
        Ingredient.CONTENTS_STREAM_CODEC,
        CryoFreezingRecipe::input,
        ResourceStack.FLUID_STREAM_CODEC,
        CryoFreezingRecipe::result,
        CryoFreezingRecipe::new
    );

    @Override
    public boolean matches(@NotNull RecipeInput recipeInput, @NotNull Level level) {
        if (!input.test(recipeInput.getItem(1))) return false;
        if (!(recipeInput instanceof ContainerRecipeWrapper wrapper)) return false;
        if (!(wrapper.container() instanceof CryoFreezerBlockEntity entity)) return false;
        if (entity.getEnergyStorage().extract(energy, true) < energy) return false;
        return entity.getFluidContainer().getAmount(0) < entity.getFluidContainer().getLimit(0, FluidResource.BLANK);
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<RecipeInput>> serializer() {
        return ModRecipeSerializers.CRYO_FREEZING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CRYO_FREEZING.get();
    }
}
