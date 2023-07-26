package earth.terrarium.adastra.client.renderers.entities.base;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CustomGeoEntityRenderer<T extends Entity & GeoAnimatable> extends GeoEntityRenderer<T> {
    private final ResourceLocation texture;
    private final Vec3 offset;

    public CustomGeoEntityRenderer(EntityRendererProvider.Context context, RegistryEntry<EntityType<T>> entity) {
        this(context, entity, 0.0f);
    }

    public CustomGeoEntityRenderer(EntityRendererProvider.Context context, RegistryEntry<EntityType<T>> entity, float offset) {
        super(context, new DefaultedEntityGeoModel<>(entity.getId()));
        this.texture = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/%s.png".formatted(entity.getId().getPath()));
        this.offset = new Vec3(0.0f, offset, 0.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return this.texture;
    }

    @Override
    public Vec3 getRenderOffset(T entity, float partialTicks) {
        return this.offset;
    }
}
