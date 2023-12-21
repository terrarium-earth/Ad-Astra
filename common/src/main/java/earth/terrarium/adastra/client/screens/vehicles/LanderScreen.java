package earth.terrarium.adastra.client.screens.vehicles;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.VehicleScreen;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.menus.vehicles.LanderMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LanderScreen extends VehicleScreen<LanderMenu, Lander> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/lander.png");

    public LanderScreen(LanderMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 174);
    }
}
