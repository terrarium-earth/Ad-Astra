package earth.terrarium.adastra.client.renderers.world;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.common.blockentities.machines.OxygenDistributorBlockEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OxygenDistributorOverlayRenderer {
    private static final Map<BlockPos, Set<BlockPos>> POSITIONS = new HashMap<>();
    private static final int COLOR = 0x4099ccff;

    public static void addPositions(BlockPos pos, Set<BlockPos> positions) {
        POSITIONS.put(pos, positions);
    }

    public static void removePositions(BlockPos pos) {
        POSITIONS.remove(pos);
    }

    public static void clearPositions() {
        POSITIONS.clear();
    }

    public static boolean canAdd(BlockPos pos) {
        var player = Minecraft.getInstance().player;
        if (player == null) return false;
        return player.blockPosition().closerThan(pos, 128);
    }

    public static void render(PoseStack poseStack, Camera camera) {
        if (!AdAstraConfigClient.showOxygenDistributorArea) return;

        var level = Minecraft.getInstance().level;
        if (level == null) return;
        if (level.getGameTime() % 40 == 0) {
            POSITIONS.keySet().removeIf(pos -> !level.isLoaded(pos)
                || !canAdd(pos)
                || !(level.getBlockEntity(pos) instanceof OxygenDistributorBlockEntity)
            );
        }

        poseStack.pushPose();
        var bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        var consumer = bufferSource.getBuffer(RenderType.debugSectionQuads());

        RenderSystem.polygonOffset(-3, -3);
        RenderSystem.enablePolygonOffset();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        poseStack.translate(-camera.getPosition().x(), -camera.getPosition().y(), -camera.getPosition().z());
        POSITIONS.values().forEach(positions -> positions.forEach(pos ->
            renderCube(poseStack, consumer, pos, positions)));

        bufferSource.endBatch();
        RenderSystem.enableCull();
        RenderSystem.polygonOffset(0, 0);
        RenderSystem.disablePolygonOffset();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    private static void renderCube(PoseStack poseStack, VertexConsumer consumer, BlockPos pos, Set<BlockPos> others) {
        Matrix4f matrix = poseStack.last().pose();

        int minX = pos.getX();
        int minY = pos.getY();
        int minZ = pos.getZ();
        int maxX = minX + 1;
        int maxY = minY + 1;
        int maxZ = minZ + 1;

        // Bottom Face
        if (!others.contains(pos.below())) {
            consumer.vertex(matrix, minX, minY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, minY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, minY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, minY, maxZ).color(COLOR).endVertex();
        }

        // Top Face
        if (!others.contains(pos.above())) {
            consumer.vertex(matrix, minX, maxY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, maxY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, maxY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, maxY, minZ).color(COLOR).endVertex();
        }

        // North Face
        if (!others.contains(pos.north())) {
            consumer.vertex(matrix, minX, minY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, maxY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, maxY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, minY, minZ).color(COLOR).endVertex();
        }

        // South Face
        if (!others.contains(pos.south())) {
            consumer.vertex(matrix, maxX, minY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, maxY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, maxY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, minY, maxZ).color(COLOR).endVertex();
        }

        // East Face
        if (!others.contains(pos.east())) {
            consumer.vertex(matrix, maxX, minY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, maxY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, maxY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, maxX, minY, maxZ).color(COLOR).endVertex();
        }

        // West Face
        if (!others.contains(pos.west())) {
            consumer.vertex(matrix, minX, minY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, maxY, maxZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, maxY, minZ).color(COLOR).endVertex();
            consumer.vertex(matrix, minX, minY, minZ).color(COLOR).endVertex();
        }
    }
}
