package earth.terrarium.ad_astra.mixin;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public interface WoodTypeInvoker {

    @Invoker("<init>")
    static WoodType ad_astra$init(String name) {
        throw new UnsupportedOperationException();
    }

    @Invoker("register")
    static WoodType ad_astra$invokeRegister(WoodType value) {
        throw new UnsupportedOperationException();
    }
}
