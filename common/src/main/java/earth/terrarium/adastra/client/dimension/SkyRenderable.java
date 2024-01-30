package earth.terrarium.adastra.client.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.common.utils.ModUtils;
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

    public static final Codec<SkyRenderable> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyRenderable::texture),
        Codec.FLOAT.fieldOf("scale").forGetter(SkyRenderable::scale),
        Vec3.CODEC.fieldOf("global_rotation").forGetter(SkyRenderable::globalRotation),
        Vec3.CODEC.fieldOf("local_rotation").forGetter(SkyRenderable::localRotation),
        ModUtils.createEnumCodec(MovementType.class).fieldOf("movement_type").forGetter(SkyRenderable::movementType),
        Codec.BOOL.fieldOf("blend").forGetter(SkyRenderable::blend),
        Codec.INT.fieldOf("back_light_color").forGetter(SkyRenderable::backLightColor),
        Codec.FLOAT.fieldOf("back_light_scale").forGetter(SkyRenderable::backLightScale)
    ).apply(inst, SkyRenderable::new));

    public SkyRenderable(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, int backLightColor) {
        this(texture, scale, globalRotation, localRotation, movementType, false, backLightColor, scale * 3);
    }

    public SkyRenderable(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, boolean blend, int backLightColor) {
        this(texture, scale, globalRotation, localRotation, movementType, blend, backLightColor, scale * 3);
    }
}
