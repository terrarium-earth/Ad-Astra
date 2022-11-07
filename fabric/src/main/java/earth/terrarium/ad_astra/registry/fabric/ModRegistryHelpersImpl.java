package earth.terrarium.ad_astra.registry.fabric;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.ad_astra.AdAstra;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ModRegistryHelpersImpl {


    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(loc, icon);
    }

    public static <V, T extends V> Pair<Supplier<T>, ResourceLocation> registerFull(Registry<V> registry, String id, Supplier<T> object) {
        var register = Registry.register(registry, new ResourceLocation(AdAstra.MOD_ID, id), object.get());
        return Pair.of(() -> register, new ResourceLocation(AdAstra.MOD_ID, id));
    }
}
