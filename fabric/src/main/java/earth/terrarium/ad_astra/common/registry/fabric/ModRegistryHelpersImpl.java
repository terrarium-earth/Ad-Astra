package earth.terrarium.ad_astra.common.registry.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ModRegistryHelpersImpl {
    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(loc, icon);
    }
}
