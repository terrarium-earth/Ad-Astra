package net.mrscauthd.beyond_earth.gui.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

public class GuiHelper {

	public static final ResourceLocation FIRE_PATH = new ResourceLocation(BeyondEarthMod.MODID, "textures/fire_on.png");
	public static final int FIRE_WIDTH = 14;
	public static final int FIRE_HEIGHT = 14;
	public static final ResourceLocation ARROW_PATH = new ResourceLocation(BeyondEarthMod.MODID, "textures/animated_arrow_full.png");
	public static final int ARROW_WIDTH = 24;
	public static final int ARROW_HEIGHT = 17;
	public static final ResourceLocation OXYGEN_CONTENT_PATH = new ResourceLocation(BeyondEarthMod.MODID, "textures/oxygen.png");
	public static final ResourceLocation OXYGEN_TANK_PATH = new ResourceLocation(BeyondEarthMod.MODID, "textures/fluid_tank_fore.png");
	public static final int OXYGEN_TANK_WIDTH = 14;
	public static final int OXYGEN_TANK_HEIGHT = 48;
	public static final ResourceLocation ENERGY_PATH = new ResourceLocation(BeyondEarthMod.MODID, "textures/energy_full.png");
	public static final int ENERGY_WIDTH = 24;
	public static final int ENERGY_HEIGHT = 48;
	public static final int FUEL_WIDTH = 48;
	public static final int FUEL_HEIGHT = 48;
	public static final ResourceLocation FLUID_TANK_PATH = new ResourceLocation(BeyondEarthMod.MODID, "textures/fluid_tank_fore.png");
	public static final int FLUID_TANK_WIDTH = 14;
	public static final int FLUID_TANK_HEIGHT = 48;

	public static boolean isHover(Rectangle2d bounds, double x, double y) {
		int left = bounds.getX();
		int right = left + bounds.getWidth();
		int top = bounds.getY();
		int bottom = top + bounds.getHeight();
		return left <= x && x < right && top <= y && y < bottom;
	}

	public static void drawArrow(PoseStack matrixStack, int left, int top, double ratio) {
		GuiHelper.drawHorizontal(matrixStack, left, top, ARROW_WIDTH, ARROW_HEIGHT, ARROW_PATH, ratio);
	}

	public static Rectangle2d getArrowBounds(int left, int top) {
		return new Rectangle2d(left, top, ARROW_WIDTH, ARROW_HEIGHT);
	}

	public static void drawFire(PoseStack matrixStack, int left, int top, double ratio) {
		drawVertical(matrixStack, left, top, FIRE_WIDTH, FIRE_HEIGHT, FIRE_PATH, ratio);
	}

	public static Rectangle2d getFireBounds(int left, int top) {
		return new Rectangle2d(left, top, FIRE_WIDTH, FIRE_HEIGHT);
	}

	public static void drawOxygenTank(PoseStack matrixStack, int left, int top, IOxygenStorage oxygenStorage) {
		drawOxygenTank(matrixStack, left, top, oxygenStorage.getOxygenStoredRatio());
	}

	public static void drawOxygenTank(PoseStack matrixStack, int left, int top, double ratio) {
		int maxHeight = FLUID_TANK_HEIGHT;
		int scaledHeight = (int) Math.ceil(maxHeight * ratio);
		int offset = maxHeight - scaledHeight;

		RenderSystem.setShaderTexture(0, OXYGEN_CONTENT_PATH);
		drawTiledSprite(matrixStack, left, top + offset, OXYGEN_TANK_WIDTH, scaledHeight, 16, 16, 0.0F, 1.0F, 0.0F, 1.0F);
		drawFluidTankOverlay(matrixStack, left, top);
	}

	public static Rectangle2d getOxygenTankBounds(int left, int top) {
		return new Rectangle2d(left, top, OXYGEN_TANK_WIDTH, OXYGEN_TANK_HEIGHT);
	}

	public static void drawEnergy(PoseStack matrixStack, int left, int top, IEnergyStorage energyStorage) {
		drawEnergy(matrixStack, left, top, (double) energyStorage.getEnergyStored() / (double) energyStorage.getMaxEnergyStored());
	}

	public static void drawEnergy(PoseStack matrixStack, int left, int top, double ratio) {
		drawVertical(matrixStack, left, top, ENERGY_WIDTH, ENERGY_HEIGHT, ENERGY_PATH, ratio);
	}

	public static void drawFuel(PoseStack matrixStack, int left, int top, double ratio) {
		ResourceLocation full = new ResourceLocation(BeyondEarthMod.MODID, "textures/rocket_fuel_bar_full.png");
		drawVertical(matrixStack, left, top, FUEL_WIDTH, FUEL_HEIGHT, full, ratio);
	}

	public static Rectangle2d getEnergyBounds(int left, int top) {
		return new Rectangle2d(left, top, ENERGY_WIDTH, ENERGY_HEIGHT);
	}

	public static Rectangle2d getBounds(int left, int top, int width, int height) {
		return new Rectangle2d(left, top, width, height);
	}

	public static void drawFluidTank(PoseStack matrixStack, int left, int top, IFluidTank tank) {
		drawFluidTank(matrixStack, left, top, tank.getFluid(), tank.getCapacity());
	}

	public static void drawFluidTank(PoseStack matrixStack, int left, int top, FluidStack stack, int capacity) {
		if (stack != null && !stack.isEmpty() && capacity > 0) {
			int maxHeight = FLUID_TANK_HEIGHT;
			int scaledHeight = (int) Math.ceil(maxHeight * ((double) stack.getAmount() / (double) capacity));
			int offset = maxHeight - scaledHeight;
			drawFluid(matrixStack, left, top + offset, FLUID_TANK_WIDTH, scaledHeight, stack);
		}

		drawFluidTankOverlay(matrixStack, left, top);
	}

	public static void drawFluidTankOverlay(PoseStack matrixStack, int left, int top) {
		drawVertical(matrixStack, left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT, FLUID_TANK_PATH, 1.0D);
	}

	public static void drawFluid(PoseStack matrixStack, int left, int top, int width, int height, FluidStack stack) {
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == Fluids.EMPTY) {
			return;
		}

		TextureAtlasSprite fluidStillSprite = getStillFluidSprite(stack);
		FluidAttributes attributes = fluid.getAttributes();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		setGLColorFromInt(attributes.getColor(stack));
		drawTiledSprite(matrixStack, left, top, width, height, fluidStillSprite, 16, 16);
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void drawRocketFluidTank(PoseStack matrixStack, int left, int top, FluidStack stack, int capacity) {
		if (stack != null && !stack.isEmpty() && capacity > 0) {
			int maxHeight = 46;
			int scaledHeight = (int) Math.ceil(maxHeight * ((double) stack.getAmount() / (double) capacity));
			int offset = maxHeight - scaledHeight;
			GuiHelper.drawFluid(matrixStack, left, top + offset, 46, scaledHeight, stack);
		}

	}

	public static void drawFluidHorizontal(PoseStack matrixStack, int left, int top, int width, int height, FluidStack stack, int capacity) {
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == Fluids.EMPTY) {
			return;
		}

		double ratio = (double) stack.getAmount() / (double) capacity;
		drawFluid(matrixStack, left, top, (int) Math.ceil(width * ratio), height, stack);
	}

	public static void drawFluidVertical(PoseStack matrixStack, int left, int top, int width, int height, FluidStack stack, int capacity) {
		Fluid fluid = FluidUtil2.getFluid(stack);

		if (fluid == Fluids.EMPTY) {
			return;
		}

		double ratio = (double) stack.getAmount() / (double) capacity;
		int scaledHeight = (int) Math.ceil(height * ratio);
		int offset = height - scaledHeight;
		drawFluid(matrixStack, left, top + offset, scaledHeight, height, stack);
	}

	public static void drawTiledSprite(PoseStack matrixStack, int left, int top, int width, int height, TextureAtlasSprite sprite, int tileWidth, int tileHeight) {
		float uMin = sprite.getU0();
		float uMax = sprite.getU1();
		float vMin = sprite.getV0();
		float vMax = sprite.getV1();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
		drawTiledSprite(matrixStack, left, top, width, height, tileWidth, tileHeight, uMin, uMax, vMin, vMax);
	}

	public static void drawTiledSprite(PoseStack matrixStack, int left, int top, int width, int height, int tileWidth, int tileHeight, float uMin, float uMax, float vMin, float vMax) {
		Matrix4f matrix = matrixStack.last().pose();

		int xTileCount = width / tileWidth;
		int xRemainder = width - (xTileCount * tileWidth);
		int yTileCount = height / tileWidth;
		int yRemainder = height - (yTileCount * tileWidth);

		int yStart = top + height;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int tiledWidth = (xTile == xTileCount) ? xRemainder : tileWidth;
				int tiledHeight = (yTile == yTileCount) ? yRemainder : tileWidth;
				int x = left + (xTile * tileWidth);
				int y = yStart - ((yTile + 1) * tileHeight);

				if (tiledWidth > 0 && tiledHeight > 0) {
					int maskRight = tileWidth - tiledWidth;
					int maskTop = tileHeight - tiledHeight;

					drawTextureWithMasking(matrix, x, y, tileWidth, tileHeight, maskTop, maskRight, 0, uMin, uMax, vMin, vMax);
				}
			}
		}
	}

	public static void drawTextureWithMasking(Matrix4f matrix, float left, float top, float tileWidth, float tileHeight, int maskTop, int maskRight, float zLevel, float uMin, float uMax, float vMin, float vMax) {
		uMax = uMax - (maskRight / tileWidth * (uMax - uMin));
		vMax = vMax - (maskTop / tileHeight * (vMax - vMin));

		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(matrix, left, top + tileHeight, zLevel).uv(uMin, vMax).endVertex();
		bufferBuilder.vertex(matrix, left + tileWidth - maskRight, top + tileHeight, zLevel).uv(uMax, vMax).endVertex();
		bufferBuilder.vertex(matrix, left + tileWidth - maskRight, top + maskTop, zLevel).uv(uMax, vMin).endVertex();
		bufferBuilder.vertex(matrix, left, top + maskTop, zLevel).uv(uMin, vMin).endVertex();
		tessellator.end();
	}

	public static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;

		RenderSystem.setShaderColor(red, green, blue, alpha);

	}

	public static TextureAtlasSprite getStillFluidSprite(FluidStack stack) {
		Fluid fluid = stack.getFluid();
		FluidAttributes attributes = fluid.getAttributes();
		ResourceLocation fluidStill = attributes.getStillTexture(stack);
		return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
	}

	public static Rectangle2d getFluidTankBounds(int left, int top) {
		return new Rectangle2d(left, top, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
	}

	public static Rectangle2d getRocketFluidTankBounds(int left, int top) {
		return new Rectangle2d(left, top, 48, 48);
	}

	public static void drawVertical(PoseStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		RenderSystem.setShaderTexture(0, resource);
		GuiComponent.blit(matrixStack, left, top + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
	}

	public static void drawVerticalReverse(PoseStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioHeight = (int) Math.ceil(height * ratio);
		int remainHeight = height - ratioHeight;
		RenderSystem.setShaderTexture(0, resource);
		GuiComponent.blit(matrixStack, left, top, 0, 0, width, remainHeight, width, height);
	}

	public static void drawHorizontal(PoseStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioWidth = (int) Math.ceil(width * ratio);

		RenderSystem.setShaderTexture(0, resource);
		GuiComponent.blit(matrixStack, left, top, 0, 0, ratioWidth, height, width, height);
	}

	public static void drawHorizontalReverse(PoseStack matrixStack, int left, int top, int width, int height, ResourceLocation resource, double ratio) {
		int ratioWidth = (int) Math.ceil(width * ratio);
		int remainWidth = width - ratioWidth;
		RenderSystem.setShaderTexture(0, resource);
		GuiComponent.blit(matrixStack, left + ratioWidth, top, ratioWidth, 0, remainWidth, height, width, height);
	}

	public static void innerBlit(Matrix4f matrix, float x1, float x2, float y1, float y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix, (float) x1, (float) y2, (float) blitOffset).uv(minU, maxV).endVertex();
		bufferbuilder.vertex(matrix, (float) x2, (float) y2, (float) blitOffset).uv(maxU, maxV).endVertex();
		bufferbuilder.vertex(matrix, (float) x2, (float) y1, (float) blitOffset).uv(maxU, minV).endVertex();
		bufferbuilder.vertex(matrix, (float) x1, (float) y1, (float) blitOffset).uv(minU, minV).endVertex();
		bufferbuilder.end();
		BufferUploader.end(bufferbuilder);
	}

	public static void blit(PoseStack matrixStack, float x, float y, float width, float height, float uOffset, float vOffset, float uWidth, float vHeight, int textureWidth, int textureHeight) {
		innerBlit(matrixStack, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
	}

	private static void innerBlit(PoseStack matrixStack, float x1, float x2, float y1, float y2, int blitOffset, float uWidth, float vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
		innerBlit(matrixStack.last().pose(), x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float) textureWidth, (uOffset + (float) uWidth) / (float) textureWidth, (vOffset + 0.0F) / (float) textureHeight, (vOffset + (float) vHeight) / (float) textureHeight);
	}

	public static void blit(PoseStack matrixStack, float x, float y, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
		blit(matrixStack, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight);
	}

	private GuiHelper() {

	}

}
