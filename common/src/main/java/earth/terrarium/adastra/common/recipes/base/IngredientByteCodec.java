package earth.terrarium.adastra.common.recipes.base;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientByteCodec {

    public static final ByteCodec<Ingredient> CODEC = ByteCodec.passthrough(
        (buf, ingredient) -> ingredient.toNetwork(new FriendlyByteBuf(buf)),
        buf -> Ingredient.fromNetwork(new FriendlyByteBuf(buf))
    );

    public static final ByteCodec<FluidHolder> FLUID_HOLDER_CODEC = ByteCodec.passthrough(
        (buf, fluidHolder) -> fluidHolder.writeToBuffer(new FriendlyByteBuf(buf)),
        buf -> FluidHolder.readFromBuffer(new FriendlyByteBuf(buf))
    );
}
