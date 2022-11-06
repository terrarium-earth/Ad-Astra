package earth.terrarium.ad_astra.mixin.fabric;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StairBlock.class)
public interface StairBlockAccessor {

    @Accessor
    Block getBase();
}
