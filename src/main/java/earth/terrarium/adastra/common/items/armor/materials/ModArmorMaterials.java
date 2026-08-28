package earth.terrarium.adastra.common.items.armor.materials;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> SPACE_SUIT = register(
        "space_suit",
        Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 2);
            map.put(ArmorItem.Type.LEGGINGS, 5);
            map.put(ArmorItem.Type.CHESTPLATE, 6);
            map.put(ArmorItem.Type.HELMET, 2);
            map.put(ArmorItem.Type.BODY, 5);
        }),
        14,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        0.0F,
        0.0F,
        () -> Ingredient.of(ModItemTags.STEEL_INGOTS)
    );
    public static final Holder<ArmorMaterial> NETHERITE_SPACE_SUIT = register(
        "netherite_space_suit",
        Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 3);
            map.put(ArmorItem.Type.LEGGINGS, 7);
            map.put(ArmorItem.Type.CHESTPLATE, 8);
            map.put(ArmorItem.Type.HELMET, 3);
            map.put(ArmorItem.Type.BODY, 11);
        }),
        15,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        3,
        0.1F,
        () -> Ingredient.of(Items.NETHERITE_INGOT)
    );
    public static final Holder<ArmorMaterial> JET_SUIT = register(
        "jet_suit",
        Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 4);
            map.put(ArmorItem.Type.LEGGINGS, 7);
            map.put(ArmorItem.Type.CHESTPLATE, 9);
            map.put(ArmorItem.Type.HELMET, 4);
            map.put(ArmorItem.Type.BODY, 11);
        }),
        15,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        5,
        0.1F,
        () -> Ingredient.of(ModItemTags.CALORITE_INGOTS)
    );

    private static Holder<ArmorMaterial> register(String string, EnumMap<ArmorItem.Type, Integer> enumMap, int i, Holder<SoundEvent> arg, float f, float g, Supplier<Ingredient> supplier) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, string)));
        return register(string, enumMap, i, arg, f, g, supplier, list);
    }

    private static Holder<ArmorMaterial> register(String string, EnumMap<ArmorItem.Type, Integer> enumMap, int i, Holder<SoundEvent> arg, float f, float g, Supplier<Ingredient> supplier, List<ArmorMaterial.Layer> list) {
        EnumMap<ArmorItem.Type, Integer> enumMap2 = new EnumMap<>(ArmorItem.Type.class);
        ArmorItem.Type[] var9 = ArmorItem.Type.values();
        int var10 = var9.length;

        for (int var11 = 0; var11 < var10; ++var11) {
            ArmorItem.Type type = var9[var11];
            enumMap2.put(type, (Integer) enumMap.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, string), new ArmorMaterial(enumMap2, i, arg, supplier, list, f, g));
    }
}
