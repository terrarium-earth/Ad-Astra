package earth.terrarium.ad_astra.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

public class ChestItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final ChestBlockEntity chest;

    public ChestItemRenderer(Block block) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.chest = new ChestBlockEntity(BlockPos.ZERO, block.defaultBlockState());

    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.chest, poseStack, buffer, packedLight, packedOverlay);
    }
}
