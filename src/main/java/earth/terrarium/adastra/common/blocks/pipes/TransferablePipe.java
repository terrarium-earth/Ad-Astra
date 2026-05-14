package earth.terrarium.adastra.common.blocks.pipes;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public interface TransferablePipe {

    long transferRate();

    Type type();

    enum Type implements StringRepresentable {
        ENERGY,
        FLUID;

        public static Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
