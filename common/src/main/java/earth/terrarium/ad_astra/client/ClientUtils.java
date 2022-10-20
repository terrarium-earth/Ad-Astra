package earth.terrarium.ad_astra.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.NotImplementedException;

@Environment(EnvType.CLIENT)
public class ClientUtils {

    @ExpectPlatform
    public static BakedModel getModel(BakedModelManager dispatcher, Identifier id) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void registerScreen(ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> factory) {
        throw new AssertionError();
    }
}
