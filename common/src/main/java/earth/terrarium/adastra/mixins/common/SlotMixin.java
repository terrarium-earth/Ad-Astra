package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.common.utils.MoveableSlot;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slot.class)
public class SlotMixin implements MoveableSlot {

    @Mutable
    @Shadow
    @Final
    public int x;

    @Mutable
    @Shadow
    @Final
    public int y;

    @Override
    public void adastra$moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
