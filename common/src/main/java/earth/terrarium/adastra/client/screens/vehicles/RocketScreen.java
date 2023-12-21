package earth.terrarium.adastra.client.screens.vehicles;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.VehicleScreen;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.menus.vehicles.RocketMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class RocketScreen extends VehicleScreen<RocketMenu, Rocket> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/rocket.png");

    public RocketScreen(RocketMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 174);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawFluidBar(graphics, mouseX, mouseY, 37, 55, entity.fluid(), entity.fluidContainer().getTankCapacity(0));
    }
}
