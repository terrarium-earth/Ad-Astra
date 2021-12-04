package net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor;

import java.text.NumberFormat;

import net.minecraft.client.gui.widget.button.ImageButton;
import net.mrscauthd.boss_tools.gui.helper.ImageButtonPlacer;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class OxygenBubbleDistributorGuiWindow extends ContainerScreen<OxygenBubbleDistributorGui.GuiContainer> {

	public static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId,"textures/screens/oxygen_bubble_distributor_gui.png");

	public static final int INPUT_TANK_LEFT = 9;
	public static final int INPUT_TANK_TOP = 21;

	public static final int OUTPUT_TANK_LEFT = 75;
	public static final int OUTPUT_TANK_TOP = 21;

	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	public static final int ARROW_LEFT = 48;
	public static final int ARROW_TOP = 36;

	private CustomTileEntity tileEntity;

	private boolean cachedWorkingAreaVisible = true;

	//Buttons
	public ImageButtonPlacer workingAreaVisibleButton;
	public ImageButtonPlacer button_plus;
	public ImageButtonPlacer button_minus;

	private static ResourceLocation HideButton = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button.png");
	private static ResourceLocation Button1 = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_plus.png");
	private static ResourceLocation Button2 = new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_minus.png");

	public OxygenBubbleDistributorGuiWindow(OxygenBubbleDistributorGui.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
		this.xSize = 177;
		this.ySize = 172;
		this.playerInventoryTitleY = this.ySize - 92;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		this.updateWorkingAreaVisibleButton();
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		CustomTileEntity tileEntity = (CustomTileEntity) this.getTileEntity();

		if (GuiHelper.isHover(this.getInputTankBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(tileEntity.getInputTank())).build(), mouseX, mouseY);
		} else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getOxygen(tileEntity.getOutputTank())).build(), mouseX, mouseY);
		} else if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(tileEntity)).build(), mouseX, mouseY);
		}

		if (GuiHelper.isHover(this.getButtonBounds(-20, 4, 20, 21), mouseX, mouseY)) {
			button_plus.setTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_plus_2.png"));
		} else {
			button_plus.setTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_plus.png"));
		}

		if (GuiHelper.isHover(this.getButtonBounds(-20, 25, 20, 20), mouseX, mouseY)) {
			button_minus.setTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_minus_2.png"));
		} else {
			button_minus.setTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_minus.png"));
		}

		if (GuiHelper.isHover(this.getButtonBounds(-20, - 22, 34, 20), mouseX, mouseY)) {
			workingAreaVisibleButton.setTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button_2.png"));
		} else {
			workingAreaVisibleButton.setTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/technik_button.png"));
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		CustomTileEntity tileEntity = this.getTileEntity();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.minecraft.getTextureManager().bindTexture(texture);
		AbstractGui.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		GuiHelper.drawEnergy(ms, this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP, tileEntity.getPrimaryEnergyStorage());
		GuiHelper.drawFluidTank(ms, this.guiLeft + INPUT_TANK_LEFT, this.guiTop + INPUT_TANK_TOP, tileEntity.getInputTank());
		GuiHelper.drawOxygenTank(ms, this.guiLeft + OUTPUT_TANK_LEFT, this.guiTop + OUTPUT_TANK_TOP, tileEntity.getOutputTank());
	}

	@Override
	protected void init() {
		super.init();

		button_plus = this.addButton(new ImageButtonPlacer(this.guiLeft - 20, this.guiTop + 5, 20, 20, 0, 0, 0, Button1, 20, 20, (p_2130901) -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBubbleDistributorBlock.ChangeRangeMessage(pos, true));
		}));

		button_minus = this.addButton(new ImageButtonPlacer(this.guiLeft - 20, this.guiTop + 25, 20, 20, 0, 0, 0, Button2, 20, 20, (p_2130901) -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBubbleDistributorBlock.ChangeRangeMessage(pos, false));
		}));

		workingAreaVisibleButton = this.addButton(new ImageButtonPlacer(this.guiLeft - 20, this.guiTop - 22, 34, 20, 0, 0, 0, HideButton, 34, 20, e -> {
			BlockPos pos = this.getTileEntity().getPos();
			BossToolsMod.PACKET_HANDLER.sendToServer(new OxygenBubbleDistributorBlock.ChangeWorkingAreaVisibleMessage(pos, !this.cachedWorkingAreaVisible));
		}));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(ms, mouseX, mouseY);

		CustomTileEntity tileEntity = this.getTileEntity();
		double range = tileEntity.getRange();
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		String rangeToString = numberInstance.format((range * 2.0D) + 1.0D);
		TranslationTextComponent workingAreaText = new TranslationTextComponent("gui." + BossToolsMod.ModId + ".oxygen_bubble_distributor.workingarea.text", rangeToString, rangeToString, rangeToString);

		int sideWidth = 2;
		int sidePadding = 2;
		int workingAreaHeight = 25;
		int workingAreaLeft = this.workingAreaVisibleButton.x + this.workingAreaVisibleButton.getWidth() - this.guiLeft;
		int workingAreaTop = -workingAreaHeight;
		int workingAreaOffsetX = workingAreaLeft;

		int textwidth = 12;

		if ((range * 2) + 1 > 9) {
			this.minecraft.getTextureManager().bindTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/oxygen_range_layer.png"));
			AbstractGui.blit(ms, workingAreaOffsetX + 1, workingAreaTop, 0, 0, 150, 25, 150, 25);
			textwidth = 13;
		} else {
			this.minecraft.getTextureManager().bindTexture(new ResourceLocation(BossToolsMod.ModId,"textures/buttons/oxygen_range_small_layer.png"));
			AbstractGui.blit(ms, workingAreaOffsetX + 1, workingAreaTop, 0, 0, 140, 25, 140, 25);
			textwidth = 17;
		}

		this.font.func_243248_b(ms, workingAreaText, workingAreaLeft + sideWidth + sidePadding + textwidth, workingAreaTop + 9, 0x339900);

		GL11.glPushMatrix();
		double oyxgenScale = 0.8D;
		GL11.glScaled(oyxgenScale, oyxgenScale, oyxgenScale);
		ITextComponent oxygenText = GaugeTextHelper.getUsingText2(GaugeValueHelper.getOxygen(tileEntity.getOxygenUsing(range)), tileEntity.getMaxTimer()).build();
		int oxygenWidth = this.font.getStringPropertyWidth(oxygenText);
		this.font.func_243248_b(ms, oxygenText, (int) ((this.xSize - 5) / oyxgenScale) - oxygenWidth, (int) (this.playerInventoryTitleY / oyxgenScale), 0x333333);
		GL11.glPopMatrix();

		String prefix = "gui." + BossToolsMod.ModId + ".oxygen_bubble_distributor.workingarea.";
		String method = this.cachedWorkingAreaVisible ? "hide" : "show";
		this.font.func_243248_b(ms, new TranslationTextComponent(prefix + method), workingAreaLeft + sideWidth + sidePadding + (this.cachedWorkingAreaVisible ? -30 : -32), workingAreaTop + 9, 0x339900);
	}

	private void updateWorkingAreaVisibleButton() {
		boolean next = this.getTileEntity().isWorkingAreaVisible();

		if (this.cachedWorkingAreaVisible != next) {
			this.cachedWorkingAreaVisible = next;
		}
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

	public Rectangle2d getInputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.guiLeft + INPUT_TANK_LEFT, this.guiTop + INPUT_TANK_TOP);
	}

	public Rectangle2d getOutputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.guiLeft + OUTPUT_TANK_LEFT, this.guiTop + OUTPUT_TANK_TOP);
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.guiLeft + ENERGY_LEFT, this.guiTop + ENERGY_TOP);
	}

	public Rectangle2d getButtonBounds(int left, int top, int width, int height) {
		return GuiHelper.getBounds(this.guiLeft + left, this.guiTop + top, width, height);
	}
}
