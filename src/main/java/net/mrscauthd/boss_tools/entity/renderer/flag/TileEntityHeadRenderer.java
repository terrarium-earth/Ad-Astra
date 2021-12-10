package net.mrscauthd.boss_tools.entity.renderer.flag;

import java.util.Map;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.flag.FlagBlock;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

@OnlyIn(Dist.CLIENT)
public class TileEntityHeadRenderer implements BlockEntityRenderer<FlagTileEntity> {

	private Map<SkullBlock.Type, SkullModelBase> skullModels;

	public TileEntityHeadRenderer(BlockEntityRendererProvider.Context context) {
		this.skullModels = SkullBlockRenderer.createSkullRenderers(context.getModelSet());
	}

	public void render(FlagTileEntity flag, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		BlockState blockState = flag.getBlockState();

		if (blockState.getValue(FlagBlock.HALF) == DoubleBlockHalf.UPPER) {
			float renderScale = 1.0F;
			float renderOffsetX = 0.050F;
			float renderOffsetY = 0.18F;
			float skullLength = 0.01F;

			Direction direction = blockState.getValue(FlagBlock.FACING);
			Vec3i normal = direction.getNormal();
			float angle = direction.get2DDataValue() * 90.0F;
			int x = normal.getX();
			int z = normal.getZ();
			boolean isXDirection = x != 0;
			boolean isZDirection = z != 0;
			float skullModelOffset = 0.25F;
			float skullDirectionalX = isXDirection ? skullModelOffset : 0.00F;
			float skullDirectionalZ = isZDirection ? skullModelOffset : 0.00F;
			float skullScaleX = isXDirection ? skullLength : renderScale;
			float skullScaleY = renderScale;
			float skullScaleZ = isZDirection ? skullLength : renderScale;
			float skullOffsetX = (0.50F - (skullScaleX / 2.0F)) - (renderOffsetX * z);
			float skullOffsetY = (0.75F - (skullScaleY / 2.0F)) + renderOffsetY;
			float skullOffsetZ = (0.50F - (skullScaleZ / 2.0F)) + (renderOffsetX * x);

			Types type = SkullBlock.Types.PLAYER;
			GameProfile playerProfile = flag.getPlayerProfile();

			RenderType rendertype = SkullBlockRenderer.getRenderType(type, playerProfile);
			SkullModelBase skullModelBase = this.skullModels.get(type);

			stack.pushPose();
			stack.translate(skullOffsetX, skullOffsetY, skullOffsetZ);
			stack.scale(skullScaleX, skullScaleY, skullScaleZ);

			// Front
			stack.pushPose();
			stack.translate(-skullDirectionalX * x, 0, -skullDirectionalZ * z);
			SkullBlockRenderer.renderSkull(null, angle - 000.0F, 0, stack, buffer, combinedLight, skullModelBase, rendertype);
			stack.popPose();

			// Back
			stack.pushPose();
			stack.translate(+skullDirectionalX * x, 0, +skullDirectionalZ * z);
			SkullBlockRenderer.renderSkull(null, angle - 180.0F, 0, stack, buffer, combinedLight, skullModelBase, rendertype);
			stack.popPose();

			stack.popPose();
		}

	}

}
