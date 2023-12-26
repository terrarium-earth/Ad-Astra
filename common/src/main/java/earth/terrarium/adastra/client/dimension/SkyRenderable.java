package earth.terrarium.adastra.client.dimension;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record SkyRenderable(
    ResourceLocation texture,
    float scale,
    Vec3 globalRotation,
    Vec3 localRotation,
    MovementType movementType,
    int backLightColor,
    float backLightScale) {

    public static SkyRenderable create(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType) {
        return new SkyRenderable(texture, scale, globalRotation, localRotation, movementType, 0, 0);
    }

    public static SkyRenderable createWithBackLight(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, int backLightColor) {
        return createWithBackLight(texture, scale, globalRotation, localRotation, movementType, backLightColor, scale * 3);
    }

    public static SkyRenderable createWithBackLight(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, int backLightColor, float backLightScale) {
        return new SkyRenderable(texture, scale, globalRotation, localRotation, movementType, backLightColor, backLightScale);
    }
}
