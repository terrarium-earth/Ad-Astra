package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record RefiningRecipe(
    ResourceLocation id,
    int cookingTime, int energy,
    FluidHolder ingredient,
    FluidHolder resultFluid
) implements CodecRecipe<Container> {

    // Custom Fluid holder codec that uses a double instead of a float to fix a rounding bug in Botarium. TODO: Remove this when Botarium is fixed.
    public static final Codec<FluidHolder> FLUID_HOLDER_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(FluidHolder::getFluid),
        Codec.DOUBLE.fieldOf("buckets").orElse(1.0).forGetter(fluidHolder -> (double) fluidHolder.getFluidAmount() / FluidHooks.getBucketAmount()),
        CompoundTag.CODEC.optionalFieldOf("tag").forGetter(fluidHolder -> Optional.ofNullable(fluidHolder.getCompound()))
    ).apply(instance, (fluid, buckets, compoundTag) -> FluidHooks.newFluidHolder(fluid, FluidHooks.buckets(buckets), compoundTag.orElse(null))));

    public static Codec<RefiningRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(RefiningRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(RefiningRecipe::energy),
            FLUID_HOLDER_CODEC.fieldOf("ingredient").forGetter(RefiningRecipe::ingredient),
            FLUID_HOLDER_CODEC.fieldOf("result_fluid").forGetter(RefiningRecipe::resultFluid)
        ).apply(instance, RefiningRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.REFINING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.REFINING.get();
    }
}
