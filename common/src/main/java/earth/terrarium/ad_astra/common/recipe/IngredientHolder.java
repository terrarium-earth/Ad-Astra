package earth.terrarium.ad_astra.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import net.minecraft.world.item.crafting.Ingredient;

public record IngredientHolder(Ingredient ingredient, int count) {
    public static final Codec<IngredientHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IngredientCodec.CODEC.fieldOf("ingredient").forGetter(IngredientHolder::ingredient),
            Codec.INT.fieldOf("count").orElse(1).forGetter(IngredientHolder::count)
    ).apply(instance, IngredientHolder::new));

    public static IngredientHolder of(Ingredient ingredient) {
        return new IngredientHolder(ingredient, 1);
    }
}
