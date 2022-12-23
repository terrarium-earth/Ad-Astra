package earth.terrarium.ad_astra.common.registry.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModRegistryHelpersImpl {
    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return new CreativeModeTab(loc.getNamespace() + "." + loc.getPath()) {
            @Override
            public @NotNull ItemStack makeIcon() {
                return icon.get();
            }
        };
    }
}
