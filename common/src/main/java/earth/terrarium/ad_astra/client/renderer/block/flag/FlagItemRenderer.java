package earth.terrarium.ad_astra.client.renderer.block.flag;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class

FlagItemRenderer extends BlockEntityWithoutLevelRenderer {

    public FlagItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType mode, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ResourceLocation model = new ModResourceLocation("block/flag/" + Registry.ITEM.getKey(stack.getItem()).getPath());
        AdAstraClient.renderBlock(model, poseStack, buffer, packedLight, packedOverlay);
    }
}