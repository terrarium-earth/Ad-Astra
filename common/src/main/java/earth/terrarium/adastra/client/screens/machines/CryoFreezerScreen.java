package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.menus.machines.CryoFreezerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CryoFreezerScreen extends MachineScreen<CryoFreezerMenu, CryoFreezerBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/cryo_freezer.png");
    public static final ResourceLocation CRYO_SLOT = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/slots/cryo.png");

    public static final Rect2i CLICK_AREA = new Rect2i(108, 10, 26, 25);

    public CryoFreezerScreen(CryoFreezerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, CRYO_SLOT, 177, 181);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawHorizontalProgressBar(graphics, GuiUtils.SNOWFLAKE, mouseX, mouseY, 46, 71, 13, 13, entity.cookTime(), entity.cookTimeTotal(), false);
    }
}
