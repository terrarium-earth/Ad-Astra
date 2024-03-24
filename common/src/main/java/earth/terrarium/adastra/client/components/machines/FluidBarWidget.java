package earth.terrarium.adastra.client.components.machines;

import com.mojang.blaze3d.systems.RenderSystem;
import com.teamresourceful.resourcefullib.client.components.CursorWidget;
import com.teamresourceful.resourcefullib.client.screens.CursorScreen;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.adastra.client.components.base.TickableWidget;
import earth.terrarium.adastra.client.screens.base.ConfigurationScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.packets.ServerboundClearFluidTankPacket;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.util.FastColor;

public class FluidBarWidget extends ConfigurationWidget implements CursorWidget, TickableWidget {

    protected final BlockPos tankPos;
    protected final int tank;
    protected final FluidContainer container;
    protected long lastFluidAmount;
    protected long difference;

    public FluidBarWidget(BlockPos tankPos, FluidConfiguration configuration) {
        super(configuration, GuiUtils.FLUID_BAR_WIDTH, GuiUtils.FLUID_BAR_HEIGHT);
        this.tankPos = tankPos;
        this.tank = configuration.tank();
        this.container = configuration.container();
    }

    @Override
    public void tick() {
        FluidHolder holder = this.container.getFluids().get(this.tank);
        this.difference = holder.getFluidAmount() - this.lastFluidAmount;
        this.lastFluidAmount = holder.getFluidAmount();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(graphics, mouseX, mouseY, partialTick);
        FluidHolder holder = this.container.getFluids().get(this.tank);
        long capacity = this.container.getTankCapacity(this.tank);
        long amount = holder.getFluidAmount();
        float ratio = amount / (float) capacity;
        int x = this.getX();
        int y = this.getY();

        if (!holder.isEmpty()) {
            TextureAtlasSprite sprite = ClientFluidHooks.getFluidSprite(holder);
            int color = ClientFluidHooks.getFluidColor(holder);
            float r = FastColor.ARGB32.red(color) / 255f;
            float g = FastColor.ARGB32.green(color) / 255f;
            float b = FastColor.ARGB32.blue(color) / 255f;

            try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), x, y + GuiUtils.FLUID_BAR_HEIGHT - (int) (GuiUtils.FLUID_BAR_HEIGHT * ratio), GuiUtils.FLUID_BAR_WIDTH, GuiUtils.FLUID_BAR_HEIGHT)) {
                for (int i = 1; i < 5; i++) {
                    graphics.blit(x, y + GuiUtils.FLUID_BAR_HEIGHT - i * 16, 0, 16, 16, sprite, r, g, b, 1);
                }
            }
        }

        RenderSystem.enableBlend();
        graphics.blitSprite(GuiUtils.FLUID_BAR, x, y, GuiUtils.FLUID_BAR_WIDTH, GuiUtils.FLUID_BAR_HEIGHT);
        RenderSystem.disableBlend();

        if (this.isHoveredOrFocused()) {
            if (holder.isEmpty()) {
                setTooltip(Tooltip.create(CommonComponents.joinLines(
                    TooltipUtils.getFluidComponent(holder, capacity),
                    TooltipUtils.getFluidDifferenceComponent(difference)
                )));
            } else {
                setTooltip(Tooltip.create(CommonComponents.joinLines(
                    TooltipUtils.getFluidComponent(holder, capacity),
                    TooltipUtils.getFluidDifferenceComponent(difference),
                    ConstantComponents.CLEAR_FLUID_TANK
                )));
            }
            setTooltipDelay(-1);
        }
    }

    @Override
    protected boolean isValidClickButton(int button) {
        return super.isValidClickButton(button) || (button == 1 && Screen.hasShiftDown());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (ConfigurationScreen.isConfigurable()) {
            super.onClick(mouseX, mouseY);
        } else {
            NetworkHandler.CHANNEL.sendToServer(new ServerboundClearFluidTankPacket(this.tankPos, this.tank));
        }
    }

    @Override
    public CursorScreen.Cursor getCursor() {
        return Screen.hasShiftDown() ? CursorScreen.Cursor.POINTER : CursorScreen.Cursor.DEFAULT;
    }
}
