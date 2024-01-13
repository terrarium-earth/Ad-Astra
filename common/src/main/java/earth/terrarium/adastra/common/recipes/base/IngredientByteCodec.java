package earth.terrarium.adastra.common.recipes.base;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientByteCodec {

    public static final ByteCodec<Ingredient> CODEC = ByteCodec.passthrough(
        (buf, ingredient) -> ingredient.toNetwork(new FriendlyByteBuf(buf)),
        buf -> Ingredient.fromNetwork(new FriendlyByteBuf(buf))
    );
}
