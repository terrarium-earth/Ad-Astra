package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.machines.CoalGeneratorBlockEntity;
import earth.terrarium.adastra.common.menus.machines.CoalGeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CoalGeneratorScreen extends MachineScreen<CoalGeneratorMenu, CoalGeneratorBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/coal_generator.png");

    public CoalGeneratorScreen(CoalGeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, IRON_SLOT, 176, 189);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawVerticalProgressBar(graphics, GuiUtils.FIRE, mouseX, mouseY, 78, 54, 15, 15, entity.cookTime(), entity.cookTimeTotal(), true);
    }
}
