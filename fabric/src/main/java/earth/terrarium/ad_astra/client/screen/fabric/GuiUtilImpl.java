package earth.terrarium.ad_astra.client.screen.fabric;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
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
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
    }
}
