package earth.terrarium.ad_astra.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.apache.commons.lang3.NotImplementedException;

@Environment(EnvType.CLIENT)
public class ClientUtils {

    @ExpectPlatform
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(MenuType<? extends M> type, MenuScreens.ScreenConstructor<M, U> factory) {
        throw new AssertionError();
    }
}
