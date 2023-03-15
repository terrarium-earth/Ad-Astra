package earth.terrarium.ad_astra.common.item.armor;

import earth.terrarium.ad_astra.common.config.SpaceSuitConfig;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class JetSuitMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[]{15, 17, 18, 13};
    private static final int[] PROTECTION_VALUES = new int[]{4, 7, 9, 4};

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * 37;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return PROTECTION_VALUES[type.ordinal()] * SpaceSuitConfig.jetSuitProtectionMultiplier;
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
        return Ingredient.of(ModTags.CALORITE_INGOTS);
    }

    @Override
    public String getName() {
        return "jet_suit";
    }

    @Override
    public float getToughness() {
        return SpaceSuitConfig.jetSuitArmorToughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.125f;
    }
}