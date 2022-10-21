package earth.terrarium.ad_astra.client.forge;

import earth.terrarium.ad_astra.client.renderer.armour.ArmourModelSupplier;
import earth.terrarium.ad_astra.client.renderer.armour.forge.ArmourRenderersImpl;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ModArmourExtension implements IClientItemExtensions {

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

    @Override
    public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<?> original) {
        BipedEntityModel<?> replacement = this.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
        if (replacement != original) {
            uncheckedCopyTo(original, replacement);
            return replacement;
        } else {
            return original;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends LivingEntity> void uncheckedCopyTo(BipedEntityModel<T> from, BipedEntityModel<?> to) {
        from.copyStateTo((BipedEntityModel<T>)to);
    }
}
