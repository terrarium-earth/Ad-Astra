package earth.terrarium.adastra.common.recipes.base;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidIngredient;
import earth.terrarium.botarium.common.fluid.utils.QuantifiedFluidIngredient;
import net.minecraft.network.FriendlyByteBuf;

public class BotariumByteCodecs {

    public static final ByteCodec<FluidHolder> FLUID_HOLDER_CODEC = ByteCodec.passthrough(
        (buf, fluidHolder) -> fluidHolder.writeToBuffer(new FriendlyByteBuf(buf)),
        buf -> FluidHolder.readFromBuffer(new FriendlyByteBuf(buf))
    );

    public static final ByteCodec<FluidIngredient> FLUID_INGREDIENT_CODEC = ModUtils.toByteCodec(FluidIngredient.CODEC);

    public static final ByteCodec<QuantifiedFluidIngredient> QUANTIFIED_FLUID_INGREDIENT_CODEC = ObjectByteCodec.create(
        FLUID_INGREDIENT_CODEC.fieldOf(QuantifiedFluidIngredient::getIngredient),
        ByteCodec.LONG.fieldOf(QuantifiedFluidIngredient::getFluidAmount),
        QuantifiedFluidIngredient::new
    );
}
