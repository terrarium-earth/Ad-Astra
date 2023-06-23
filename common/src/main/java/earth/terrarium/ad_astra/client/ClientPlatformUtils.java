package earth.terrarium.ad_astra.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.NotImplementedException;

public class ClientPlatformUtils {

    @ExpectPlatform
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(MenuType<? extends M> type, ScreenConstructor<M, U> factory) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static Component getFluidTranslation(Fluid fluid) {
        throw new NotImplementedException();
    }

    public interface ScreenConstructor<T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> {
        U create(T abstractContainerMenu, Inventory inventory, Component component);
    }
}
