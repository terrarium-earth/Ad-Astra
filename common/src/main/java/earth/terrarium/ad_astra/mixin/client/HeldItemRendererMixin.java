package earth.terrarium.ad_astra.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.items.vehicles.VehicleItem;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderItem*", at = @At("HEAD"), cancellable = true)
    public void adastra_renderItem(LivingEntity entity, ItemStack stack, ItemTransforms.TransformType renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        if (stack != null && !stack.isEmpty()) {
            // Disable item rendering while in a vehicle
            if (entity.getVehicle() instanceof VehicleEntity vehicle) {
                if (vehicle.fullyConcealsRider()) {
                    ci.cancel();
                }
            }

            // disable item rendering while holding a vehicle item and swimming
            if (stack.getItem() instanceof VehicleItem) {
                if (entity.getPose().equals(Pose.SWIMMING)) {
                    ci.cancel();
                }

                // Only render the main hand stack if the player is holding a vehicle in both the main hand and off hand
                Item mainStack = entity.getMainHandItem().getItem();
                if (leftHanded && stack.getItem().equals(entity.getOffhandItem().getItem()) && mainStack instanceof VehicleItem) {
                    ci.cancel();
                }
            }
        }
    }
}
