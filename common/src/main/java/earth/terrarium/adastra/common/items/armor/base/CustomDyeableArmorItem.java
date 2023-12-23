package earth.terrarium.adastra.common.items.armor.base;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

public class CustomDyeableArmorItem extends DyeableArmorItem {
    public CustomDyeableArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    // Makes the default color white instead of brown
    @Override
    public int getColor(ItemStack stack) {
        int color = super.getColor(stack);
        return color == 0xa06540 ? 0xFFFFFF : color;
    }
}
