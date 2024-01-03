package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.menus.machines.CompressorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CompressorScreen extends MachineScreen<CompressorMenu, CompressorBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/compressor.png");
    public static final Rect2i CLICK_AREA = new Rect2i(44, 25, 26, 25);

    public CompressorScreen(CompressorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, STEEL_SLOT, 184, 201);
        this.titleLabelY += 3;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawHorizontalProgressBar(graphics, GuiUtils.HAMMER, mouseX, mouseY, 72, 59, 15, 16, entity.cookTime(), entity.cookTimeTotal(), false);
    }
}
