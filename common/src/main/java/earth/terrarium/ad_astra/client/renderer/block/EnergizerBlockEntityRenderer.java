package earth.terrarium.ad_astra.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.blocks.machines.entity.EnergizerBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class EnergizerBlockEntityRenderer implements BlockEntityRenderer<EnergizerBlockEntity> {

    public EnergizerBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(EnergizerBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ItemStack stack = blockEntity.getItem(0);

        if (stack.isEmpty()) {
            return;
        }

        if (!blockEntity.getBlockState().getValue(AbstractMachineBlock.LIT)) {
            return;
        }

        matrices.pushPose();

        // Calculate the current offset in the y value
        double offset = Math.sin((blockEntity.getLevel().getGameTime() + tickDelta) / 8.0) / 8.0;
        // Move the item
        matrices.translate(0.5, 1.6 + offset, 0.5);

        // Rotate the item
        matrices.mulPose(Vector3f.YP.rotationDegrees((blockEntity.getLevel().getGameTime() + tickDelta) * 4));

        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
        matrices.popPose();
    }
}
