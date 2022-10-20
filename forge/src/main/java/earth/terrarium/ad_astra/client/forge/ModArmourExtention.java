package earth.terrarium.ad_astra.client.forge;

import earth.terrarium.ad_astra.client.renderer.armour.ArmourModelSupplier;
import earth.terrarium.ad_astra.client.renderer.armour.forge.ArmourRenderersImpl;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ModArmourExtention implements IClientItemExtensions {

    private ArmourModelSupplier renderer;


    @Override
    @SuppressWarnings("unchecked")
    public @NotNull BipedEntityModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<?> original) {
        if (renderer == null) {
            renderer = ArmourRenderersImpl.getArmourRenderer(itemStack.getItem());
        }
        if (renderer != null) {
            return renderer.getArmorModel(livingEntity, itemStack, equipmentSlot, (BipedEntityModel<LivingEntity>) original);
        }
        return original;
    }
}
