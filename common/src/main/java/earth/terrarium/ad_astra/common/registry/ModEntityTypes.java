package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, AdAstra.MOD_ID);

    public static void registerSpawnPlacements() {
    }

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
    }
}