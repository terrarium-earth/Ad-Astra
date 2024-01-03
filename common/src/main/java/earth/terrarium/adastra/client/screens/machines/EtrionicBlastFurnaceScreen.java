package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.machines.OptionBarOptions;
import earth.terrarium.adastra.client.components.machines.OptionsBarWidget;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
import earth.terrarium.adastra.common.menus.machines.EtrionicBlastFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EtrionicBlastFurnaceScreen extends MachineScreen<EtrionicBlastFurnaceMenu, EtrionicBlastFurnaceBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/etrionic_blast_furnace.png");
    public static final ResourceLocation FURNACE_OVERLAY = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/etrionic_blast_furnace_overlay.png");
    public static final Rect2i CLICK_AREA = new Rect2i(23, 79, 45, 19);

    public EtrionicBlastFurnaceScreen(EtrionicBlastFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, STEEL_SLOT, 184, 201);
        this.titleLabelY += 3;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawHorizontalProgressBar(graphics, GuiUtils.ARROW, mouseX, mouseY, 75, 50, 20, 12, entity.cookTime(), entity.cookTimeTotal(), false);
        if (entity.cookTimeTotal() > 0) {
            graphics.blit(FURNACE_OVERLAY, leftPos + 30, topPos + 51, 0, 0, 32, 43, 32, 43);
        }
    }

    @Override
    public OptionsBarWidget.Builder createOptionsBar() {
        return super.createOptionsBar()
            .addElement(0, OptionBarOptions.createBlastFurnaceMode(entity));
    }
}
