package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.world.carver.CraterWorldCarver;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class ModWorldCarvers {
    public static final ResourcefulRegistry<WorldCarver<?>> WORLD_CARVERS = ResourcefulRegistries.create(BuiltInRegistries.CARVER, AdAstra.MOD_ID);

    public static final RegistryEntry<CraterWorldCarver> CRATER = WORLD_CARVERS.register("crater", () -> new CraterWorldCarver(CarverConfiguration.CODEC.codec()));
}
