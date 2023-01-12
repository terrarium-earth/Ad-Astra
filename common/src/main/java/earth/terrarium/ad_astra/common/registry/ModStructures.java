package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructures {
    public static final ResourcefulRegistry<StructureType<?>> STRUCTURE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.STRUCTURE_TYPE, AdAstra.MOD_ID);
}