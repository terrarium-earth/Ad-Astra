package earth.terrarium.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.util.SignType;

@Mixin(SignType.class)
public interface SignTypeInvoker {

    @Invoker("<init>")
    static SignType adastra_init(String name) {
        throw new UnsupportedOperationException();
    }

    @Invoker("register")
    static SignType adastra_invokeRegister(SignType value) {
        throw new UnsupportedOperationException();
    }
}
