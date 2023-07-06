package earth.terrarium.adastra.client.renderers.blocks.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.HydraulicPressBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class HydraulicPressBlockEntityRenderer extends GeoBlockRenderer<HydraulicPressBlockEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation(AdAstra.MOD_ID, "hydraulic_press");

    public HydraulicPressBlockEntityRenderer() {
        super(new DefaultedBlockGeoModel<>(MODEL));
    }

    @Override
    public ResourceLocation getTextureLocation(HydraulicPressBlockEntity animatable) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock());
        return new ResourceLocation(AdAstra.MOD_ID, "textures/block/%s.png".formatted(id.getPath()));
    }
}
