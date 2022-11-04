package earth.terrarium.ad_astra.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {

    @Accessor
    VertexBuffer getSkyBuffer();

    @Accessor
    boolean getGenerateClouds();

    @Accessor
    void setGenerateClouds(boolean value);

    @Accessor
    VertexBuffer getCloudBuffer();

    @Accessor
    void setCloudBuffer(VertexBuffer value);

    @Accessor
    CloudStatus getPrevCloudsType();

    @Accessor
    void setPrevCloudsType(CloudStatus value);

    @Accessor
    Vec3 getPrevCloudColor();

    @Accessor
    void setPrevCloudColor(Vec3 value);

    @Accessor
    int getTicks();

    @Accessor
    int getPrevCloudX();

    @Accessor
    void setPrevCloudX(int value);

    @Accessor
    int getPrevCloudY();

    @Accessor
    void setPrevCloudY(int value);

    @Accessor
    int getPrevCloudZ();

    @Accessor
    void setPrevCloudZ(int value);

    @Accessor
    float[] getRainSizeX();

    @Accessor
    float[] getRainSizeZ();

    @Invoker
    BufferBuilder.RenderedBuffer invokeBuildClouds(BufferBuilder builder, double x, double y, double z, Vec3 color);
}