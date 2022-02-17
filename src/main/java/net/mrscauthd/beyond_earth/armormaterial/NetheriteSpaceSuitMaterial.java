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

public class NetheriteSpaceSuitMaterial {
    public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial() {

        @Override
        public int getDurabilityForSlot(EquipmentSlot slot) {
            return new int[]{481, 555, 592, 407}[slot.getIndex()];
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot slot) {
            return new int[]{3, 6, 8, 3}[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(Items.NETHERITE_INGOT));
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public String getName() {
            return "netherite_space_suit";
        }

        @Override
        public float getToughness() {
            return 3.0f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.1f;
        }
    };
}
