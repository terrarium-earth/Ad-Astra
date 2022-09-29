package com.github.alexnijjar.ad_astra.client.renderer.armour.forge;

import com.github.alexnijjar.ad_astra.client.renderer.armour.ArmourModelSupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

import java.util.HashMap;
import java.util.Map;

public class ArmourRenderersImpl {
    private static final Map<Item, ArmourModelSupplier> ARMOUR_ITEM_MODELS = new HashMap<>();

    public static void registerArmour(ArmourModelSupplier modelSupplier, Item... items) {
        for (Item item : items) {
            registerArmourRenderer(item, modelSupplier);
        }
    }

    public static ArmourModelSupplier getArmourRenderer(ItemConvertible item) {
        return ARMOUR_ITEM_MODELS.get(item.asItem());
    }

    private static void registerArmourRenderer(ItemConvertible item, ArmourModelSupplier renderer) {
        ARMOUR_ITEM_MODELS.put(item.asItem(), renderer);
    }
}
