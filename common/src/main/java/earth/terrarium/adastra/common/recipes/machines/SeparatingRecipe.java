package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record SeparatingRecipe(ResourceLocation id, int cookingTime, int energy,
                               FluidHolder ingredient, FluidHolder resultFluid1,
                               FluidHolder resultFluid2) implements CodecRecipe<Container> {

    public static Codec<SeparatingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").orElse(2).forGetter(SeparatingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").orElse(10).forGetter(SeparatingRecipe::energy),
            FluidHolder.CODEC.fieldOf("ingredient").forGetter(SeparatingRecipe::ingredient),
            FluidHolder.CODEC.fieldOf("result_fluid_1").forGetter(SeparatingRecipe::resultFluid1),
            FluidHolder.CODEC.fieldOf("result_fluid_2").forGetter(SeparatingRecipe::resultFluid2)
        ).apply(instance, SeparatingRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SEPARATING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.SEPARATING.get();
    }
}
