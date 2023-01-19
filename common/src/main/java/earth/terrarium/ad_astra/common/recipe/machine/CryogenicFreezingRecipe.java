package earth.terrarium.ad_astra.common.recipe.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.ad_astra.common.block.machine.entity.CryogenicFreezerBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record CryogenicFreezingRecipe(ResourceLocation id, FluidHolder ingredient1, FluidHolder ingredient2,
                                      int cookingTime,
                                      int energy, FluidHolder resultFluid) implements CodecRecipe<Container> {

    public static Codec<CryogenicFreezingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                FluidHolder.CODEC.fieldOf("ingredient_1").forGetter(CryogenicFreezingRecipe::ingredient1),
                FluidHolder.CODEC.fieldOf("ingredient_2").forGetter(CryogenicFreezingRecipe::ingredient2),
                Codec.INT.fieldOf("cookingtime").orElse(2).forGetter(CryogenicFreezingRecipe::cookingTime),
                Codec.INT.fieldOf("energy").orElse(10).forGetter(CryogenicFreezingRecipe::energy),
                FluidHolder.CODEC.fieldOf("result_fluid").forGetter(CryogenicFreezingRecipe::resultFluid)
        ).apply(instance, CryogenicFreezingRecipe::new));
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    public boolean matches(CryogenicFreezerBlockEntity block) {
        FluidHolder fluid1 = block.getFluidContainer().getFluids().get(0);
        FluidHolder fluid2 = block.getFluidContainer().getFluids().get(1);
        return fluid1 != null && fluid2 != null && !fluid1.isEmpty() && !fluid2.isEmpty() && (fluid1.matches(ingredient1) && fluid2.matches(ingredient2) || fluid1.matches(ingredient2) && fluid2.matches(ingredient1));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRYOGENIC_FREEZING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CRYOGENIC_FREEZING.get();
    }
}
