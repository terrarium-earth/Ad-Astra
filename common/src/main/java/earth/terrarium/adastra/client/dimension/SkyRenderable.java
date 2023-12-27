package earth.terrarium.adastra.client.dimension;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record SkyRenderable(
    ResourceLocation texture,
    float scale,
    Vec3 globalRotation,
    Vec3 localRotation,
    MovementType movementType,
    boolean blend,
    int backLightColor,
    float backLightScale) {

    public static SkyRenderable create(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, int backLightColor) {
        return create(texture, scale, globalRotation, localRotation, movementType, false, backLightColor, scale * 3);
    }

    public static SkyRenderable create(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, boolean blend, int backLightColor) {
        return create(texture, scale, globalRotation, localRotation, movementType, blend, backLightColor, scale * 3);
    }

    public static SkyRenderable create(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, boolean blend, int backLightColor, float backLightScale) {
        return new SkyRenderable(texture, scale, globalRotation, localRotation, movementType, blend, backLightColor, backLightScale);
    }
}
