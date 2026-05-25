package earth.terrarium.adastra.common.items.armor.base;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.DyedItemColor;

public class CustomDyeableArmorItem extends ArmorItem {

    public CustomDyeableArmorItem(Holder<ArmorMaterial> armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties
            .durability(type.getDurability(armorMaterial.value().getDefense(type)))
            .component(DataComponents.DYED_COLOR, new DyedItemColor(0xFFFFFFFF,false)));
    }

    // Makes the default color white instead of brown
//    @Override
//    public int getColor(ItemStack stack) {
//        int color = super.getColor(stack);
//        return color == 0xa06540 ? 0xFFFFFF : color;
//    }

}
