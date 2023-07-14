package earth.terrarium.ad_astra.client.forge;

import earth.terrarium.ad_astra.client.ClientPlatformUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ClientPlatformUtilsImpl {
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        return dispatcher.getModel(id);
    }

    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(MenuType<? extends M> type, ClientPlatformUtils.ScreenConstructor<M, U> factory) {
        MenuScreens.register(type, factory::create);
    }

    public static Component getFluidTranslation(Fluid fluid) {
        if (fluid == Fluids.EMPTY) {
            return Component.translatable("item.ad_astra.empty_tank").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA));
        }
        return fluid.getFluidType().getDescription();
    }
}
