package earth.terrarium.ad_astra.mixin;

import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PaintingEntity.class)
public interface PaintingEntityInvoker {
    @Invoker("setVariant")
    void adastra_invokeSetVariant(Holder<PaintingVariant> variant);
}
