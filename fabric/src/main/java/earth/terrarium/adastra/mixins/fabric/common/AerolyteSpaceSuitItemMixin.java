package earth.terrarium.adastra.mixins.fabric.common;

import earth.terrarium.adastra.common.items.armor.AerolyteSpaceSuitItem;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AerolyteSpaceSuitItem.class)
public abstract class AerolyteSpaceSuitItemMixin implements FabricElytraItem {
    @Shadow(remap = false)
    private boolean isFullFlightEnabled(Player player) {
        return false;
    }

    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        return entity instanceof Player p && isFullFlightEnabled(p);
    }
}
