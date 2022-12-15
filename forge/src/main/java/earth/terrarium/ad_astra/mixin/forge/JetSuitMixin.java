package earth.terrarium.ad_astra.mixin.forge;

import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(JetSuit.class)
public abstract class JetSuitMixin extends Item {
    @Shadow(remap = false)
    private boolean isFallFlying;

    public JetSuitMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level.isClientSide) {
            if (EquipmentSlot.CHEST.equals(stack.getItem().getEquipmentSlot(stack))) {
                int nextFlightTick = flightTicks + 1;
                if (nextFlightTick % 10 == 0) {
                    if (nextFlightTick % 20 == 0) {
                        stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
                    }
                    entity.gameEvent(GameEvent.ELYTRA_GLIDE);
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
