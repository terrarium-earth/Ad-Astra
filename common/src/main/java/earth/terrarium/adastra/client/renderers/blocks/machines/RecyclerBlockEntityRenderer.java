package earth.terrarium.adastra.client.renderers.blocks.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.client.renderers.blocks.base.CustomGeoBlockRenderer;
import earth.terrarium.adastra.common.blockentities.machines.RecyclerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import java.util.Objects;

public class RecyclerBlockEntityRenderer extends CustomGeoBlockRenderer<RecyclerBlockEntity> {

    public RecyclerBlockEntityRenderer(RegistryEntry<Block> block) {
        super(block);
    }

    @Override
    public void postRender(PoseStack poseStack, RecyclerBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        try (var ignored = new CloseablePoseStack(poseStack)) {
            ItemStack stack = animatable.getItem(1);
            poseStack.translate(0, 1.2f, -0.2f);
            poseStack.scale(0.55f, 0.55f, 0.55f);
            if (animatable.cookTimeTotal() == 0 || !animatable.canFunction()) return;
            ClientLevel level = Objects.requireNonNull(Minecraft.getInstance().level);
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack),
                animatable.getBlockPos().getX() + 0.5,
                animatable.getBlockPos().getY() + 1.2,
                animatable.getBlockPos().getZ() + 0.5,
                (level.random.nextFloat() - 0.5) * 0.4,
                (level.random.nextFloat() - 0.5) * 0.4,
                (level.random.nextFloat() - 0.5) * 0.4);
        }
    }
}
