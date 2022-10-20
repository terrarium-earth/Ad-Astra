package earth.terrarium.ad_astra.client.forge;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ClientUtilsImpl {
    public static BakedModel getModel(BakedModelManager dispatcher, Identifier id) {
        return dispatcher.getModel(id);
    }

    public static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void registerScreen(ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> factory) {
        HandledScreens.register(type, factory);
    }
}
