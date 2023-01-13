package earth.terrarium.ad_astra.common.recipe.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.ad_astra.common.block.machine.entity.ElectrolyzerBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

@MethodsReturnNonnullByDefault
public record ElectrolysisRecipe(ResourceLocation id, FluidHolder ingredient, int cookingTime,
                                 int energy, FluidHolder resultFluid1,
                                 FluidHolder resultFluid2) implements CodecRecipe<Container> {

    public static Codec<ElectrolysisRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                FluidHolder.CODEC.fieldOf("ingredient").forGetter(ElectrolysisRecipe::ingredient),
                Codec.INT.fieldOf("cookingtime").orElse(2).forGetter(ElectrolysisRecipe::cookingTime),
                Codec.INT.fieldOf("energy").orElse(10).forGetter(ElectrolysisRecipe::energy),
                FluidHolder.CODEC.fieldOf("result_fluid_1").forGetter(ElectrolysisRecipe::resultFluid1),
                FluidHolder.CODEC.fieldOf("result_fluid_2").forGetter(ElectrolysisRecipe::resultFluid2)
        ).apply(instance, ElectrolysisRecipe::new));
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    public boolean matches(ElectrolyzerBlockEntity block) {
        FluidHolder fluid = block.getFluidContainer().getFluids().get(0);
        return fluid != null && !fluid.isEmpty() && fluid.matches(ingredient);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ELECTROLYSIS.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.ELECTROLYSIS.get();
    }
}
