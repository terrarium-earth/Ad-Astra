package earth.terrarium.ad_astra.common.recipe.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.ad_astra.common.block.machine.entity.GeothermalGeneratorBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record GeothermalRecipe(ResourceLocation id, FluidHolder ingredient, int cookingTime,
                               int energy) implements CodecRecipe<Container> {

    public static Codec<GeothermalRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                FluidHolder.CODEC.fieldOf("ingredient").forGetter(GeothermalRecipe::ingredient),
                Codec.INT.fieldOf("cookingtime").orElse(1).forGetter(GeothermalRecipe::cookingTime),
                Codec.INT.fieldOf("energy").orElse(10).forGetter(GeothermalRecipe::energy)
        ).apply(instance, GeothermalRecipe::new));
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    public boolean matches(GeothermalGeneratorBlockEntity block) {
        FluidHolder fluid = block.getFluidContainer().getFluids().get(0);
        return fluid != null && !fluid.isEmpty() && fluid.matches(ingredient);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.GEOTHERMAL_GENERATING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.GEOTHERMAL_GENERATING.get();
    }
}
