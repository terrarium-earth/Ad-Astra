package earth.terrarium.ad_astra.common.item.armor;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.SpaceSuitConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.Range;

import java.util.stream.StreamSupport;

public class NetheriteSpaceSuit extends SpaceSuit {

    public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
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
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armour/netherite_space_suit.png").toString();
    }
}