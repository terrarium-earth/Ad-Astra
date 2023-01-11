package earth.terrarium.ad_astra.client.renderer.block.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlock;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class GlobeBlockEntityRenderer implements BlockEntityRenderer<GlobeBlockEntity> {

    public GlobeBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(GlobeBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        GlobeModel model = GlobeModel.getModel();

        model.setYaw(Mth.lerp(tickDelta, entity.getCachedYaw(), entity.getYaw()));

        BlockState state = entity.getBlockState();
        GlobeRenderer.render(BuiltInRegistries.BLOCK.getKey(state.getBlock()), model, state.getValue(GlobeBlock.FACING), poseStack, buffer, packedLight, packedOverlay);
    }
}