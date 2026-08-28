package earth.terrarium.adastra.common.recipes.base;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;

public record IngredientHolder(Ingredient ingredient, int count) {

    public static final Codec<IngredientHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(IngredientHolder::ingredient),
        Codec.INT.fieldOf("count").orElse(1).forGetter(IngredientHolder::count)
    ).apply(instance, IngredientHolder::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, IngredientHolder> NETWORK_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        IngredientHolder::ingredient,
        ByteBufCodecs.INT,
        IngredientHolder::count,
        IngredientHolder::new
    );

    public static IngredientHolder of(Ingredient ingredient) {
        return new IngredientHolder(ingredient, 1);
    }

    public static IngredientHolder of(Ingredient ingredient, int count) {
        return new IngredientHolder(ingredient, count);
    }
}
