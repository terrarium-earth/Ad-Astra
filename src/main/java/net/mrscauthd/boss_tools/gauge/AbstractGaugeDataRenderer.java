package net.mrscauthd.boss_tools.gauge;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public abstract class AbstractGaugeDataRenderer {

	private final IGaugeValue value;

	public AbstractGaugeDataRenderer(IGaugeValue value) {
		this.value = value;
	}

	public void toBytes(FriendlyByteBuf buffer) {
		GaugeValueSerializer.Serializer.write(this.getValue(), buffer);
	}

	public void render(PoseStack matrixStack, int left, int top) {
		this.drawBorder(matrixStack, left, top);

		int width = this.getWidth();
		int height = this.getHeight();
		int padding = this.getBorderWidth();
		Rect2i innerBounds = new Rect2i(left + padding, top + padding, width - padding * 2, height - padding * 2);
		this.drawBackground(matrixStack, innerBounds);
		this.drawContents(matrixStack, innerBounds);
		this.drawGaugeText(matrixStack, innerBounds);
	}

	protected void drawContents(PoseStack matrixStack, Rect2i innerBounds) {

	}

	@Nullable
	public Component getGaugeText() {
		return GaugeTextHelper.getValueText(this.getValue()).build();
	}

	protected void drawGaugeText(PoseStack matrixStack, Rect2i innerBounds) {
		Component text = this.getGaugeText();

		if (text != null) {
			int color = this.getTextColor();
			int textPadding = 2;
			Rect2i textBounds = new Rect2i(innerBounds.getX() + textPadding, innerBounds.getY(), innerBounds.getWidth() - textPadding, innerBounds.getHeight());

			this.drawText(matrixStack, textBounds, text, color);
		}
	}

	protected void drawText(PoseStack matrixStack, Rect2i bounds, Component text, int color) {
		this.drawText(Minecraft.getInstance(), matrixStack, bounds, text, color);
	}

	protected void drawText(Minecraft minecraft, PoseStack matrixStack, Rect2i bounds, Component text, int color) {
		Font fontRenderer = minecraft.font;
		int textWidth = fontRenderer.width(text);

		float scale = Math.min(1.0F, (float) bounds.getWidth() / (float) textWidth);
		float offsetX = 0.0F;
		float offsetY = (bounds.getHeight() - ((fontRenderer.lineHeight - 1) * scale)) / 2.0F;
		float scaledX = (bounds.getX() + offsetX) / scale;
		float scaledY = (bounds.getY() + offsetY) / scale;

		matrixStack.pushPose();
		matrixStack.scale(scale, scale, scale);
		fontRenderer.draw(matrixStack, text, scaledX, scaledY, color);
		matrixStack.popPose();
	}

	protected void drawBackground(PoseStack matrixStack, Rect2i innerBounds) {
		IGaugeValue value = this.getValue();
		int tileColor = value.getColor();
		double displayRatio = value.getDisplayRatio();

		try {
			RenderSystem.enableBlend();
			GuiHelper.setGLColorFromInt(tileColor);

			TextureAtlasSprite tileTexture = this.getBackgroundTileTexture();

			if (tileTexture != null) {
				int tileWidth = this.getBackgroundTileWidth();
				int tileHeight = this.getBackgroundTileHeight();
				int ratioWidth = (int) Math.ceil(innerBounds.getWidth() * displayRatio);
				GuiHelper.drawTiledSprite(matrixStack, innerBounds.getX(), innerBounds.getY(), ratioWidth, innerBounds.getHeight(), tileTexture, tileWidth, tileHeight);
			}

			ResourceLocation texture = this.getBackgroundTexture();

			if (texture != null) {
				GuiHelper.drawHorizontal(matrixStack, innerBounds.getX(), innerBounds.getY(), innerBounds.getWidth(), innerBounds.getHeight(), texture, displayRatio);
			}

		} finally {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableBlend();
		}

	}

	protected void drawBorder(PoseStack matrixStack, int left, int top) {
		int width = this.getWidth();
		int height = this.getHeight();
		int borderColor = this.getBorderColor();
		int padding = this.getBorderWidth();

		GuiComponent.fill(matrixStack, left, top, left + width - padding, top + padding, borderColor);
		GuiComponent.fill(matrixStack, left, top, left + padding, top + height - padding, borderColor);
		GuiComponent.fill(matrixStack, left + width - padding, top, left + width, top + height - padding, borderColor);
		GuiComponent.fill(matrixStack, left, top + height - padding, left + width, top + height, borderColor);
	}

	public int getTextColor() {
		return 0xFFFFFFFF;
	}

	@Nullable
	public TextureAtlasSprite getBackgroundTileTexture() {
		return null;
	}

	@Nullable
	public ResourceLocation getBackgroundTexture() {
		return null;
	}

	public int getBackgroundTileWidth() {
		return 16;
	}

	public int getBackgroundTileHeight() {
		return 16;
	}

	public int getBorderWidth() {
		return 1;
	}

	public int getBorderColor() {
		return 0xFF000000;
	}

	public int getWidth() {
		return 100;
	}

	public int getHeight() {
		return 13;
	}

	public IGaugeValue getValue() {
		return this.value;
	}

}
