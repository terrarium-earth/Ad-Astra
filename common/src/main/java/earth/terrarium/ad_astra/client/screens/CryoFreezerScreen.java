package earth.terrarium.ad_astra.client.screens;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.blocks.machines.entity.CryoFreezerBlockEntity;
import earth.terrarium.ad_astra.screen.handler.CryoFreezerScreenHandler;
import earth.terrarium.ad_astra.util.ModIdentifier;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CryoFreezerScreen extends AbstractMachineScreen<CryoFreezerScreenHandler> {

	private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/cryo_freezer.png");

	public static final int SNOWFLAKE_LEFT = 54;
	public static final int SNOWFLAKE_TOP = 71;

	public static final int INPUT_TANK_LEFT = 85;
	public static final int INPUT_TANK_TOP = 37;

	public static final int ENERGY_LEFT = 149;
	public static final int ENERGY_TOP = 27;

	public CryoFreezerScreen(CryoFreezerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.backgroundWidth = 177;
		this.backgroundHeight = 181;
		this.playerInventoryTitleY = this.backgroundHeight - 93;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

		super.drawBackground(matrices, delta, mouseX, mouseY);

		CryoFreezerBlockEntity entity = (CryoFreezerBlockEntity) this.entity;

		GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.handler.getEnergyAmount(), this.entity.getCapacity());
		GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, this.handler.getFluids().get(0).getFluidAmount(), entity.getFluidContainer().getTankCapacity(0), this.handler.getFluids().get(0));
		GuiUtil.drawSnowflake(matrices, this.x + SNOWFLAKE_LEFT, this.y + SNOWFLAKE_TOP, entity.getCookTime(), entity.getCookTimeTotal());
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		CryoFreezerBlockEntity entity = (CryoFreezerBlockEntity) this.entity;

		if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
			GuiUtil.drawEnergyTooltip(this, matrices, this.handler.getEnergyAmount(), entity.getCapacity(), mouseX, mouseY);
		}

		if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
			GuiUtil.drawTankTooltip(this, matrices, this.handler.getFluids().get(0), entity.getFluidContainer().getTankCapacity(0), mouseX, mouseY);
		}
	}

	public Rectangle getOutputTankBounds() {
		return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
	}

	public Rectangle getEnergyBounds() {
		return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
	}

	@Override
	public int getTextColour() {
		return 0xccffff;
	}
}