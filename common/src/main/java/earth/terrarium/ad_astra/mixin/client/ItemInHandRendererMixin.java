package earth.terrarium.ad_astra.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.item.vehicle.VehicleItem;
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
public class ItemInHandRendererMixin {
    @Inject(method = "renderItem*", at = @At("HEAD"), cancellable = true)
    public void ad_astra$renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, CallbackInfo ci) {
        if (itemStack != null && !itemStack.isEmpty()) {
            // Disable item rendering while in a vehicle
            if (livingEntity.getVehicle() instanceof Vehicle vehicle) {
                if (vehicle.fullyConcealsRider()) {
                    ci.cancel();
                }
            }

            if (itemStack.getItem() instanceof VehicleItem) {
                // disable item rendering while holding a vehicle item and swimming
                if (livingEntity.getPose().equals(Pose.SWIMMING)) {
                    ci.cancel();
                }

                // Only render the main hand stack if the player is holding a vehicle in both the main hand and offhand
                Item mainStack = livingEntity.getMainHandItem().getItem();
                ItemStack offhandStack = livingEntity.getOffhandItem();
                if (!offhandStack.isEmpty() && leftHand && itemStack.getItem().equals(offhandStack.getItem()) && mainStack instanceof VehicleItem) {
                    ci.cancel();
                }
            }
        }
    }
}