package earth.terrarium.adastra.client.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.common.blockentities.machines.EnergizerBlockEntity;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Objects;


public class EnergizerBlockEntityRenderer implements BlockEntityRenderer<EnergizerBlockEntity> {

    @Override
    public void render(EnergizerBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        var stack = entity.getItem(0);
        if (stack.isEmpty()) return;
        if (!entity.getBlockState().getValue(MachineBlock.LIT)) return;

        try (var pose = new CloseablePoseStack(poseStack)) {
            double offset = Math.sin((Objects.requireNonNull(entity.getLevel()).getGameTime() + partialTick) / 8.0) / 8.0;
            pose.translate(0.5, 1.6 + offset, 0.5);
            pose.mulPose(Axis.YP.rotationDegrees((entity.getLevel().getGameTime() + partialTick) * 4));
            int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above());
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, lightAbove, packedOverlay, poseStack, buffer, entity.getLevel(), 0);
        }
    }
}
