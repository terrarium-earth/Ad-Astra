package net.mrscauthd.boss_tools.armormaterial;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class SpaceSuitNetheriteArmorMaterial {
    public static IArmorMaterial ARMOR_MATERIAL = new IArmorMaterial() {
        @Override
        public int getDurability(EquipmentSlotType slot) {
            return new int[]{48, 55, 60, 40}[slot.getIndex()] * 10;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slot) {
            return new int[]{3, 6, 8, 3}[slot.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public net.minecraft.util.SoundEvent getSoundEvent() {
            return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
        }

        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.EMPTY;
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public String getName() {
            return "netherite_space_suit";
        }

        @Override
        public float getToughness() {
            return 0f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0f;
        }
    };
}
