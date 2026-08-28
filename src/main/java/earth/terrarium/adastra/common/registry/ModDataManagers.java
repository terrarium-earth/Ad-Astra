package earth.terrarium.adastra.common.registry;

import com.mojang.serialization.Codec;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.common_storage_lib.data.DataManager;
import earth.terrarium.common_storage_lib.data.DataManagerRegistry;
import earth.terrarium.common_storage_lib.fluid.util.FluidStorageData;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModDataManagers {
    public static final DataManagerRegistry REGISTRY = new DataManagerRegistry(AdAstra.MOD_ID);

    public static final DataManager<FluidStorageData> FLUID_CONTENTS = REGISTRY.builder(FluidStorageData.DEFAULT).serialize(FluidStorageData.CODEC).networkSerializer(FluidStorageData.NETWORK_CODEC).withDataComponent().copyOnDeath().buildAndRegister("fluids");
    public static final DataManager<Long> VALUE_CONTENT = REGISTRY.builder(() -> 0L).serialize(Codec.LONG).networkSerializer(ByteBufCodecs.VAR_LONG).withDataComponent().copyOnDeath().buildAndRegister("energy");
}
