package earth.terrarium.ad_astra.mixin.forge;

import earth.terrarium.ad_astra.items.armour.JetSuit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(JetSuit.class)
public class JetSuitMixin extends Item {
    @Shadow
    private boolean isFallFlying;

    public JetSuitMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.world.isClient) {
            if (EquipmentSlot.CHEST.equals(stack.getItem().getEquipmentSlot(stack))) {
                int nextFlightTick = flightTicks + 1;
                if (nextFlightTick % 10 == 0) {
                    if (nextFlightTick % 20 == 0) {
                        stack.damage(1, entity, e -> e.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
                    }
                    entity.emitGameEvent(GameEvent.ELYTRA_GLIDE);
                }
            }
        }
        return true;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return this.isFallFlying;
    }
}
