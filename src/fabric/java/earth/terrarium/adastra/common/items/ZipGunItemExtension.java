package earth.terrarium.adastra.common.items;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.msrandom.classextensions.ClassExtension;
import net.msrandom.classextensions.ExtensionInject;

@ClassExtension(ZipGunItem.class)
public class ZipGunItemExtension extends Item implements FabricItem {

    public ZipGunItemExtension(Item.Properties properties) {
        super(properties);
    }

    @ExtensionInject
    @Override
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
}
