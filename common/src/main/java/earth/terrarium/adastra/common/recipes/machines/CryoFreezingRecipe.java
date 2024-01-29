package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CryoFreezingRecipe(
    ResourceLocation id,
    int cookingTime, int energy,
    Ingredient input,
    FluidHolder result
) implements CodecRecipe<Container> {

    public static Codec<CryoFreezingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(CryoFreezingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CryoFreezingRecipe::energy),
            IngredientCodec.CODEC.fieldOf("ingredient").forGetter(CryoFreezingRecipe::input),
            FluidHolder.NEW_CODEC.fieldOf("result").forGetter(CryoFreezingRecipe::result)
        ).apply(instance, CryoFreezingRecipe::new));
    }

    public static Codec<CryoFreezingRecipe> netCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(CryoFreezingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CryoFreezingRecipe::energy),
            IngredientCodec.NETWORK_CODEC.fieldOf("ingredient").forGetter(CryoFreezingRecipe::input),
            FluidHolder.NEW_CODEC.fieldOf("result").forGetter(CryoFreezingRecipe::result)
        ).apply(instance, CryoFreezingRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (!input.test(container.getItem(1))) return false;
        if (!(container instanceof CryoFreezerBlockEntity entity)) return true;
        if (entity.getEnergyStorage().internalExtract(energy, true) < energy) return false;
        return entity.getFluidContainer().getFirstFluid().getFluidAmount() < entity.getFluidContainer().getTankCapacity(0);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRYO_FREEZING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CRYO_FREEZING.get();
    }
}
