package earth.terrarium.adastra.common.registry;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ModDataComponents {

    public static final ResourcefulRegistry<DataComponentType<?>> DATA_COMPONENT_TYPES = ResourcefulRegistries.create(BuiltInRegistries.DATA_COMPONENT_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<DataComponentType<Boolean>> ACTIVE = DATA_COMPONENT_TYPES.register("active", () -> buildPersistentComponent(Codec.BOOL));
    public static final RegistryEntry<DataComponentType<Byte>> MODE = DATA_COMPONENT_TYPES.register("mode", () -> buildPersistentComponent(Codec.BYTE));

    private static <T> DataComponentType<T> buildPersistentComponent(Codec<T> directCodec) {
        return DataComponentType.<T>builder().persistent(directCodec).cacheEncoding().build();
    }

    private static <T> DataComponentType<T> buildSyncPersistentComponentRegistryFriendly(Codec<T> directCodec, StreamCodec<RegistryFriendlyByteBuf, T> directStreamCodec) {
        return DataComponentType.<T>builder().persistent(directCodec).networkSynchronized(directStreamCodec).cacheEncoding().build();
    }

    private static <T> DataComponentType<T> buildSyncPersistentComponent(Codec<T> directCodec, StreamCodec<ByteBuf, T> directStreamCodec) {
        return DataComponentType.<T>builder().persistent(directCodec).networkSynchronized(directStreamCodec).cacheEncoding().build();
    }
}
