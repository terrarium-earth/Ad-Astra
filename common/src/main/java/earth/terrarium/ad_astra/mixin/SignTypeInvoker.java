package earth.terrarium.ad_astra.mixin;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public interface SignTypeInvoker {

    @Invoker("<init>")
    static WoodType adastra_init(String name) {
        throw new UnsupportedOperationException();
    }

    @Invoker("register")
    static WoodType adastra_invokeRegister(WoodType value) {
        throw new UnsupportedOperationException();
    }
}
