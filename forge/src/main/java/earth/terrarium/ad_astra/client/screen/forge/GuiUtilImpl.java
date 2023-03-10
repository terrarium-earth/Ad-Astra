package earth.terrarium.ad_astra.client.screen.forge;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class GuiUtilImpl {
    public static Component getFluidTranslation(Fluid fluid) {
        if (fluid == Fluids.EMPTY) {
            return Component.translatable("item.ad_astra.empty_tank").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA));
        }
        return fluid.getFluidType().getDescription();
    }
}
