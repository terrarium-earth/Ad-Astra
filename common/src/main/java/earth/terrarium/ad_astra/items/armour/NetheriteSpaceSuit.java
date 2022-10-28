package earth.terrarium.ad_astra.items.armour;

import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import java.util.stream.StreamSupport;

public class NetheriteSpaceSuit extends SpaceSuit {

    public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.getItem() instanceof NetheriteSpaceSuit);
    }

    @Override
    public long getTankSize() {
        return AdAstra.CONFIG.spaceSuit.netheriteSpaceSuitTankSize;
    }

    @Override
    public Range<Integer> getTemperatureThreshold() {
        return Range.between(-300, 500);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return AdAstra.MOD_ID + (slot != EquipmentSlot.HEAD || "overlay".equals(type) ? ":textures/entity/armour/netherite_space_suit_overlay.png" : ":textures/entity/armour/netherite_space_suit.png");
    }
}