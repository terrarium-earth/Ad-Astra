package earth.terrarium.ad_astra.client.fabric;

import earth.terrarium.ad_astra.client.ClientPlatformUtils;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ClientPlatformUtilsImpl {
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        return BakedModelManagerHelper.getModel(dispatcher, id);
    }

    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(MenuType<? extends M> type, ClientPlatformUtils.ScreenConstructor<M, U> factory) {
        MenuScreens.register(type, factory::create);
    }
}
