package earth.terrarium.ad_astra.common.compat.rei;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import earth.terrarium.ad_astra.common.recipe.ModRecipe;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;

public record RecipeCodecDisplaySerializer<DISPLAY extends Display, RECIPE extends ModRecipe> (Function<DISPLAY, RECIPE> display2RecipeFunc, Function<RECIPE, DISPLAY> recipe2DisplayFunc, Function<ResourceLocation, Codec<RECIPE>> codecFunc) implements DisplaySerializer<DISPLAY> {

    @Override
    public CompoundTag save(CompoundTag tag, DISPLAY display) {
        RECIPE recipe = this.display2RecipeFunc().apply(display);
        ResourceLocation id = recipe.getId();
        Codec<RECIPE> codec = this.codecFunc().apply(id);

        tag.putString("id", id.toString());
        tag.put("recipe", codec.encode(recipe, NbtOps.INSTANCE, null).result().get());
        return tag;
    }

    @Override
    public DISPLAY read(CompoundTag tag) {
        ResourceLocation id = new ResourceLocation(tag.getString("id"));
        Codec<RECIPE> codec = this.codecFunc().apply(id);
        return codec.parse(NbtOps.INSTANCE, tag.get("recipe")).result().map(this.recipe2DisplayFunc()).get();
    }

}
