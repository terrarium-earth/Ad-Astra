package earth.terrarium.adastra.common.recipes.base;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import net.minecraft.world.item.crafting.Ingredient;

public record IngredientHolder(Ingredient ingredient, int count) {
    public static final Codec<IngredientHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(IngredientHolder::ingredient),
        Codec.INT.fieldOf("count").orElse(1).forGetter(IngredientHolder::count)
    ).apply(instance, IngredientHolder::new));

    public static final ByteCodec<IngredientHolder> NETWORK_CODEC = ObjectByteCodec.create(
        ExtraByteCodecs.INGREDIENT.fieldOf(IngredientHolder::ingredient),
        ByteCodec.INT.fieldOf(IngredientHolder::count),
        IngredientHolder::new
    );

    public static IngredientHolder of(Ingredient ingredient) {
        return new IngredientHolder(ingredient, 1);
    }

    public static IngredientHolder of(Ingredient ingredient, int count) {
        return new IngredientHolder(ingredient, count);
    }
}
