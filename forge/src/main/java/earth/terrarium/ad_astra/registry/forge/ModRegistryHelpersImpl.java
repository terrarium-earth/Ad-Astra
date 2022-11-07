package earth.terrarium.ad_astra.registry.forge;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModRegistryHelpersImpl {

    public static final Map<Registry<?>, DeferredRegister<?>> REGISTRIES = new HashMap<>();

    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return new CreativeModeTab(loc.getNamespace() + "." + loc.getPath()) {
            @Override
            public @NotNull ItemStack makeIcon() {
                return icon.get();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> DeferredRegister<T> getOrCreateRegistry(Registry<T> registry) {
        if(REGISTRIES.containsKey(registry)) return (DeferredRegister<T>) REGISTRIES.get(registry);
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registry.key(), AdAstra.MOD_ID);
        REGISTRIES.put(registry, deferredRegister);
        return deferredRegister;
    }

    public static <V, T extends V> Pair<Supplier<T>, ResourceLocation> registerFull(Registry<V> registry, String id, Supplier<T> object) {
        var registered = getOrCreateRegistry(registry).register(id, object);
        return Pair.of(registered, registered.getId());
    }
}
