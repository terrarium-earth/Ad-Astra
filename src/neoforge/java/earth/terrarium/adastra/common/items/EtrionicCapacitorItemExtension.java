package earth.terrarium.adastra.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.msrandom.classextensions.ClassExtension;
import net.msrandom.classextensions.ExtensionInject;

@ClassExtension(EtrionicCapacitorItem.class)
public class EtrionicCapacitorItemExtension extends Item {

    public EtrionicCapacitorItemExtension(Properties properties) {
        super(properties);
    }

    @ExtensionInject
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
