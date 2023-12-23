package earth.terrarium.adastra.mixins.fabric.common;

import earth.terrarium.adastra.common.items.armor.JetSuitItem;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(JetSuitItem.class)
public abstract class JetSuitItemMixin implements FabricElytraItem {
    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        //noinspection DataFlowIssue
        return ((JetSuitItem) (Object) this).canElytraFly(chestStack, entity);
    }
}
