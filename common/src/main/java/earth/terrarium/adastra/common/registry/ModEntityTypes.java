package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.AirVortex;
import earth.terrarium.adastra.common.entities.SpiderBot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<EntityType<AirVortex>> AIR_VORTEX = ENTITY_TYPES.register("air_vortex", () ->
        EntityType.Builder.<AirVortex>of(AirVortex::new, MobCategory.MISC)
            .fireImmune()
            .noSummon()
            .noSave()
            .build(""));

    public static final RegistryEntry<EntityType<SpiderBot>> SPIDER_BOT = ENTITY_TYPES.register("spider_bot", () ->
        EntityType.Builder.of(SpiderBot::new, MobCategory.MISC)
            .sized(1.5f, 1.5f)
            .build(""));

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
        attributes.accept(SPIDER_BOT, SpiderBot::createMobAttributes);
    }
}
