package net.mrscauthd.beyond_earth.armormaterial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class SpaceSuitMaterial {
    public static ArmorMaterial ArmorMaterial = new ArmorMaterial() {

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return new int[]{195, 225, 240, 165}[slot.getIndex()];
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return new int[]{2, 5, 6, 2}[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Override
        public SoundEvent getEquipSound() {
            return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(Items.IRON_INGOT));
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public String getName() {
            return "space_suit";
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
