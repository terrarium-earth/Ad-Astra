package com.github.alexnijjar.ad_astra.mixin.forge;

import com.github.alexnijjar.ad_astra.client.renderer.armour.ArmourModelSupplier;
import com.github.alexnijjar.ad_astra.client.renderer.armour.forge.ArmourRenderersImpl;
import com.github.alexnijjar.ad_astra.items.armour.ModArmourItem;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(ModArmourItem.class)
public abstract class ModArmourItemMixin {

    // @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Item item = (Item)(Object)this;

        consumer.accept(new IClientItemExtensions() {

            private final ArmourModelSupplier renderer = ArmourRenderersImpl.getArmourRenderer(item);

            @Override
            @SuppressWarnings("unchecked")
            public @NotNull BipedEntityModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<?> original) {
                if (renderer != null) {
                    return renderer.getArmorModel(livingEntity, itemStack, equipmentSlot, (BipedEntityModel<LivingEntity>) original);
                }
                return original;
//                EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
//                ModelPart layer = modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION);
//                return new SpaceSuitModel(layer, (BipedEntityModel<LivingEntity>) original, livingEntity, equipmentSlot, itemStack, SpaceSuitRenderer.SPACE_SUIT_TEXTURE);
            }
        });
    }
}
