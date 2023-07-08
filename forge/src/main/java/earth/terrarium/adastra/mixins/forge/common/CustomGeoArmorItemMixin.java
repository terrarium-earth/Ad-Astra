package earth.terrarium.adastra.mixins.forge.common;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.common.items.base.CustomGeoArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

@Mixin(CustomGeoArmorItem.class)
public abstract class CustomGeoArmorItemMixin extends Item {

    public CustomGeoArmorItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = AdAstraClient.getArmorRenderer(CustomGeoArmorItemMixin.this);
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }
}
