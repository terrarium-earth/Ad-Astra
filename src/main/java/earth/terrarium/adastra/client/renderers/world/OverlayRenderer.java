package earth.terrarium.adastra.client.renderers.world;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class OverlayRenderer {

    private final Map<BlockPos, Set<BlockPos>> positions = new HashMap<>();
    private final int color;
    private final BooleanSupplier config;
    private final Supplier<Block> block;

    public OverlayRenderer(int color, BooleanSupplier config, Supplier<Block> block) {
        this.color = color;
        this.config = config;
        this.block = block;
    }

    public void addPositions(BlockPos pos, Set<BlockPos> positions) {
        this.positions.put(pos, positions);
    }

    public void removePositions(BlockPos pos) {
        positions.remove(pos);
    }

    public void clearPositions() {
        positions.clear();
    }

    public boolean canAdd(BlockPos pos) {
        var player = Minecraft.getInstance().player;
        if (player == null) return false;
        return player.blockPosition().closerThan(pos, 128);
    }

    public void render(PoseStack poseStack, Camera camera) {
        if (!config.getAsBoolean()) return;

        var level = Minecraft.getInstance().level;
        if (level == null) return;
        if (level.getGameTime() % 40 == 0) {
            positions.keySet().removeIf(pos -> !level.isLoaded(pos)
                || !canAdd(pos)
                || !(level.getBlockState(pos).is(block.get()))
            );
        }

        poseStack.pushPose();
        var bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        var consumer = bufferSource.getBuffer(RenderType.debugSectionQuads());

        RenderSystem.polygonOffset(-3, -3);
        RenderSystem.enablePolygonOffset();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        poseStack.translate(-camera.getPosition().x(), -camera.getPosition().y(), -camera.getPosition().z());
        positions.values().forEach(positions -> positions.forEach(pos ->
            renderCube(poseStack, consumer, pos, positions)));

        bufferSource.endBatch();
        RenderSystem.enableCull();
        RenderSystem.polygonOffset(0, 0);
        RenderSystem.disablePolygonOffset();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    private void renderCube(PoseStack poseStack, VertexConsumer consumer, BlockPos pos, Set<BlockPos> others) {
        Matrix4f matrix = poseStack.last().pose();

        int minX = pos.getX();
        int minY = pos.getY();
        int minZ = pos.getZ();
        int maxX = minX + 1;
        int maxY = minY + 1;
        int maxZ = minZ + 1;

        // Bottom Face
        if (!others.contains(pos.below())) {
            consumer.addVertex(matrix, minX, minY, minZ).setColor(color);
            consumer.addVertex(matrix, maxX, minY, minZ).setColor(color);
            consumer.addVertex(matrix, maxX, minY, maxZ).setColor(color);
            consumer.addVertex(matrix, minX, minY, maxZ).setColor(color);
        }

        // Top Face
        if (!others.contains(pos.above())) {
            consumer.addVertex(matrix, minX, maxY, maxZ).setColor(color);
            consumer.addVertex(matrix, maxX, maxY, maxZ).setColor(color);
            consumer.addVertex(matrix, maxX, maxY, minZ).setColor(color);
            consumer.addVertex(matrix, minX, maxY, minZ).setColor(color);
        }

        // North Face
        if (!others.contains(pos.north())) {
            consumer.addVertex(matrix, minX, minY, minZ).setColor(color);
            consumer.addVertex(matrix, minX, maxY, minZ).setColor(color);
            consumer.addVertex(matrix, maxX, maxY, minZ).setColor(color);
            consumer.addVertex(matrix, maxX, minY, minZ).setColor(color);
        }

        // South Face
        if (!others.contains(pos.south())) {
            consumer.addVertex(matrix, maxX, minY, maxZ).setColor(color);
            consumer.addVertex(matrix, maxX, maxY, maxZ).setColor(color);
            consumer.addVertex(matrix, minX, maxY, maxZ).setColor(color);
            consumer.addVertex(matrix, minX, minY, maxZ).setColor(color);
        }

        // East Face
        if (!others.contains(pos.east())) {
            consumer.addVertex(matrix, maxX, minY, minZ).setColor(color);
            consumer.addVertex(matrix, maxX, maxY, minZ).setColor(color);
            consumer.addVertex(matrix, maxX, maxY, maxZ).setColor(color);
            consumer.addVertex(matrix, maxX, minY, maxZ).setColor(color);
        }

        // West Face
        if (!others.contains(pos.west())) {
            consumer.addVertex(matrix, minX, minY, maxZ).setColor(color);
            consumer.addVertex(matrix, minX, maxY, maxZ).setColor(color);
            consumer.addVertex(matrix, minX, maxY, minZ).setColor(color);
            consumer.addVertex(matrix, minX, minY, minZ).setColor(color);
        }
    }
}
