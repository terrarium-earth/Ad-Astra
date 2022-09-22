package com.github.alexnijjar.ad_astra.client.screens;

import java.awt.Rectangle;
import java.util.Arrays;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.ad_astra.client.screens.utils.ButtonType;
import com.github.alexnijjar.ad_astra.client.screens.utils.CustomButton;
import com.github.alexnijjar.ad_astra.client.screens.utils.PlanetSelectionScreen.TooltipType;
import com.github.alexnijjar.ad_astra.client.screens.utils.ScreenUtils;
import com.github.alexnijjar.ad_astra.data.ButtonColour;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.github.alexnijjar.ad_astra.screen.handler.OxygenDistributorScreenHandler;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.entity.OxygenUtils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OxygenDistributorScreen extends AbstractMachineScreen<OxygenDistributorScreenHandler> {

	private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/oxygen_distributor.png");
	private static final Identifier WARNING_SIGN = new ModIdentifier("textures/gui/overlay/warning_sign.png");

	Text SHOW_TEXT = ScreenUtils.createText("show");
	Text HIDE_TEXT = ScreenUtils.createText("hide");

	public static final int INPUT_TANK_LEFT = 50;
	public static final int INPUT_TANK_TOP = 80;

	public static final int OUTPUT_TANK_LEFT = 114;
	public static final int OUTPUT_TANK_TOP = 80;

	public static final int ENERGY_LEFT = 147;
	public static final int ENERGY_TOP = 82;

	private boolean displayConversionEnergyCost = false;

	CustomButton visibleButton;

	public OxygenDistributorScreen(OxygenDistributorScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.backgroundWidth = 177;
		this.backgroundHeight = 244;
		this.playerInventoryTitleY = this.backgroundHeight - 92;
		this.titleY = 67;

	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

		super.drawBackground(matrices, delta, mouseX, mouseY);

		FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

		GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergyStorage().getStoredEnergy(), this.blockEntity.getMaxGeneration());
		GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.tanks.getFluids().get(0).getFluidAmount(), entity.getInputSize(), entity.tanks.getFluids().get(0));
		GuiUtil.drawFluidTank(matrices, this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP, entity.tanks.getFluids().get(1).getFluidAmount(), entity.getOutputSize(), entity.tanks.getFluids().get(1));

		visibleButton.setMessage(((OxygenDistributorBlockEntity) this.blockEntity).shouldShowOxygen() ? HIDE_TEXT : SHOW_TEXT);

		// Render a warning sign if there is an oxygen leak detected.
		boolean oxygenLeak = OxygenUtils.getOxygenBlocksCount(this.blockEntity.getWorld(), this.blockEntity.getPos()) >= AdAstra.CONFIG.oxygenDistributor.maxBlockChecks;
		if (oxygenLeak) {
			ScreenUtils.addTexture(matrices, this.width / 2 - 85, this.height / 2 - 137, 14, 15, WARNING_SIGN);
		}
		if (OxygenUtils.getOxygenBlocksCount(this.blockEntity.getWorld(), this.blockEntity.getPos()) <= 0 && entity.hasEnergy() && entity.tanks.getFluids().get(1).getFluidAmount() > 0) {
			ScreenUtils.addTexture(matrices, this.width / 2 - 67, this.height / 2 - 137, 14, 15, WARNING_SIGN);
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

		if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
			GuiUtil.drawEnergyTooltip(this, matrices, entity, mouseX, mouseY);
		}

		if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
			GuiUtil.drawTankTooltip(this, matrices, entity.tanks.getFluids().get(0), mouseX, mouseY);
		}

		if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
			GuiUtil.drawTankTooltip(this, matrices, entity.tanks.getFluids().get(1), mouseX, mouseY);
		}

		int oxygenBlocksCount = OxygenUtils.getOxygenBlocksCount(this.blockEntity.getWorld(), this.blockEntity.getPos());
		boolean oxygenLeak = OxygenUtils.getOxygenBlocksCount(this.blockEntity.getWorld(), this.blockEntity.getPos()) >= AdAstra.CONFIG.oxygenDistributor.maxBlockChecks;
		if (oxygenLeak) {
			if (GuiUtil.isHovering(getOxygenLeakWarningSignBounds(), mouseX, mouseY)) {
				this.renderTooltip(matrices, Arrays.asList(Text.translatable("gauge_text.ad_astra.oxygen_leak_warning[0]"), Text.translatable("gauge_text.ad_astra.oxygen_leak_warning[1]"), Text.translatable("gauge_text.ad_astra.oxygen_leak_warning[2]"), Text.translatable("gauge_text.ad_astra.oxygen_leak_warning[3]"), Text.translatable("gauge_text.ad_astra.oxygen_leak_warning[4]")), mouseX, mouseY);
			}
		}

		if (oxygenBlocksCount <= 0 && entity.hasEnergy() && entity.tanks.getFluids().get(1).getFluidAmount() > 0) {
			if (GuiUtil.isHovering(getBlockedWarningSignBounds(), mouseX, mouseY)) {
				this.renderTooltip(matrices, Arrays.asList(Text.translatable("gauge_text.ad_astra.blocked_warning[0]"), Text.translatable("gauge_text.ad_astra.blocked_warning[1]"), Text.translatable("gauge_text.ad_astra.blocked_warning[2]")), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		super.drawForeground(matrices, mouseX, mouseY);
		long oxygenBlocksCount = OxygenUtils.getOxygenBlocksCount(this.blockEntity.getWorld(), this.blockEntity.getPos());
		matrices.push();
		matrices.scale(0.9f, 0.9f, 0.9f);
		Text oxygenBlockText = Text.translatable("gauge_text.ad_astra.oxygen_blocks");
		Text oxygenBlockAmount = Text.of(oxygenBlocksCount + " / " + AdAstra.CONFIG.oxygenDistributor.maxBlockChecks);

		int offset = 25;
		this.textRenderer.draw(matrices, oxygenBlockText, 11, offset + 11, 0x68d975);
		this.textRenderer.draw(matrices, oxygenBlockAmount, 11, offset + 24, 0x68d975);

		OxygenDistributorBlockEntity entity = (OxygenDistributorBlockEntity) this.blockEntity;

		long energyUsagePerTick = entity.getEnergyToConsume(oxygenBlocksCount, true);

		if (displayConversionEnergyCost) {
			energyUsagePerTick += entity.getEnergyPerTick();
			this.displayConversionEnergyCost = false;
		} else if (entity.tanks.getFluids().get(0).getFluidAmount() > 0 && entity.tanks.getFluids().get(1).getFluidAmount() < entity.getOutputSize()) {
			energyUsagePerTick += entity.getEnergyPerTick();
			displayConversionEnergyCost = true;
		}

		long oxygenUsagePerTick = entity.getFluidToExtract(oxygenBlocksCount, true);

		float oxygenUsageRounded = FluidUtils.dropletsToMillibucketsFloat(oxygenUsagePerTick);
		oxygenUsageRounded = (float) (Math.round(oxygenUsageRounded * 1000.0) / 1000.0);

		// Energy per tick text
		this.textRenderer.draw(matrices, Text.translatable("gauge_text.ad_astra.energy_per_tick", energyUsagePerTick), 11, offset + -17, 0x68d975);
		// Oxygen usage per tick text
		this.textRenderer.draw(matrices, Text.translatable("gauge_text.ad_astra.fluid_per_tick", oxygenUsageRounded), 11, offset + -5, 0x68d975);
		matrices.pop();
	}

	@Override
	protected void init() {
		super.init();
		OxygenDistributorBlockEntity oxygenDistributor = ((OxygenDistributorBlockEntity) this.blockEntity);
		visibleButton = new CustomButton(this.width / 2 + 10, this.height / 2 - 83, oxygenDistributor.shouldShowOxygen() ? HIDE_TEXT : SHOW_TEXT, ButtonType.STEEL, ButtonColour.WHITE, TooltipType.NONE, null, pressed -> {
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeIdentifier(this.blockEntity.getWorld().getRegistryKey().getValue());
			buf.writeBlockPos(this.blockEntity.getPos());
			ClientPlayNetworking.send(ModC2SPackets.TOGGLE_SHOW_DISTRIBUTOR, buf);
			((OxygenDistributorBlockEntity) this.blockEntity).setShowOxygen(!oxygenDistributor.shouldShowOxygen());
		});
		visibleButton.doMask = false;
		this.addDrawableChild(visibleButton);
	}

	public Rectangle getInputTankBounds() {
		return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
	}

	public Rectangle getOutputTankBounds() {
		return GuiUtil.getFluidTankBounds(this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP);
	}

	public Rectangle getOxygenLeakWarningSignBounds() {
		return new Rectangle(this.width / 2 - 85, this.height / 2 - 137, 14, 15);
	}

	public Rectangle getBlockedWarningSignBounds() {
		return new Rectangle(this.width / 2 - 67, this.height / 2 - 137, 14, 15);
	}

	public Rectangle getEnergyBounds() {
		return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
	}

	@Override
	public int getTextColour() {
		return 0x2C282E;
	}
}