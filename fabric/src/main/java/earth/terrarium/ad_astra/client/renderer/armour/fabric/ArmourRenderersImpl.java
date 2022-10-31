package earth.terrarium.ad_astra.client.renderer.armour.fabric;

import earth.terrarium.ad_astra.client.renderer.armour.ArmourModelSupplier;
import earth.terrarium.ad_astra.items.armour.ModArmourItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ArmourRenderersImpl {
    private static final Map<String, ResourceLocation> ARMOUR_ITEM_MODELS = new HashMap<>();

    public static void registerArmour(ArmourModelSupplier modelSupplier, Item... items) {
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, model) -> ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, modelSupplier.getArmorModel(entity, stack, slot, model), getArmorResource(entity, stack, slot, null)), items);
    }

    public static ResourceLocation getArmorResource(LivingEntity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

        if (item instanceof ModArmourItem armour) {
            String customTexture = armour.getArmorTexture(stack, entity, slot, type);
            if (customTexture != null) {
                s1 = customTexture;
            }
        }

        ResourceLocation resourcelocation = ARMOUR_ITEM_MODELS.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOUR_ITEM_MODELS.put(s1, resourcelocation);
        }

        return resourcelocation;
    }
}
