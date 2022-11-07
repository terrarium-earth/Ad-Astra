package earth.terrarium.ad_astra.mixin.forge;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {

    @Accessor("STRIPPABLES")
    static Map<Block, Block> adastra_getStrippables() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor("STRIPPABLES")
    static void adastra_setStrippables(Map<Block, Block> value) {
        throw new UnsupportedOperationException();
    }
}