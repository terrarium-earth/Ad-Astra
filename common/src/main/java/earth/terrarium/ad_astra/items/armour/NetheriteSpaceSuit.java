package earth.terrarium.ad_astra.items.armour;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.config.SpaceSuitConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import java.util.stream.StreamSupport;

public class NetheriteSpaceSuit extends SpaceSuit {

    public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
        super(material, slot, settings);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.getItem() instanceof NetheriteSpaceSuit);
    }

    @Override
    public long getTankSize() {
        return SpaceSuitConfig.netheriteSpaceSuitTankSize;
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