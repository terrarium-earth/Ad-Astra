package earth.terrarium.adastra.common.items.armor.materials;

import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class JetSuitMaterial implements ArmorMaterial {
    public static final ArmorMaterial MATERIAL = new JetSuitMaterial();

    private static final int[] BASE_DURABILITY = new int[]{15, 17, 18, 13};
    private static final int[] PROTECTION_VALUES = new int[]{4, 7, 9, 4};

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * 37;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return PROTECTION_VALUES[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItemTags.CALORITE_INGOTS);
    }

    @Override
    public String getName() {
        return "netherite";
    }

    @Override
    public float getToughness() {
        return 5;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1f;
    }
}