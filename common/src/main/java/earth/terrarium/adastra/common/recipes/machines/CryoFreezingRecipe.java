package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.recipes.base.IngredientByteCodec;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CryoFreezingRecipe(
    int cookingTime, int energy,
    Ingredient input,
    FluidHolder result
) implements CodecRecipe<Container> {

    public static final Codec<CryoFreezingRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(CryoFreezingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CryoFreezingRecipe::energy),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CryoFreezingRecipe::input),
            FluidHolder.CODEC.fieldOf("result").forGetter(CryoFreezingRecipe::result)
        ).apply(instance, CryoFreezingRecipe::new));

    public static final ByteCodec<CryoFreezingRecipe> NETWORK_CODEC = ObjectByteCodec.create(
        ByteCodec.INT.fieldOf(CryoFreezingRecipe::cookingTime),
        ByteCodec.INT.fieldOf(CryoFreezingRecipe::energy),
        IngredientByteCodec.CODEC.fieldOf(CryoFreezingRecipe::input),
        IngredientByteCodec.FLUID_HOLDER_CODEC.fieldOf(CryoFreezingRecipe::result),
        CryoFreezingRecipe::new
    );

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (!input.test(container.getItem(1))) return false;
        if (!(container instanceof CryoFreezerBlockEntity entity)) return true;
        if (entity.getEnergyStorage().internalExtract(energy, true) < energy) return false;
        return entity.getFluidContainer().getFirstFluid().getFluidAmount() < entity.getFluidContainer().getTankCapacity(0);
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<Container>> serializer() {
        return ModRecipeSerializers.CRYO_FREEZING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CRYO_FREEZING.get();
    }
}
