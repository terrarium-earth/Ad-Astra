package earth.terrarium.ad_astra.client.renderer.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ArmourModelSupplier {
    @NotNull
    HumanoidModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<LivingEntity> original);
}
