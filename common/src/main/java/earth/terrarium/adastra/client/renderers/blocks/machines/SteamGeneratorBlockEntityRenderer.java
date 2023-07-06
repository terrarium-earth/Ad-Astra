package earth.terrarium.adastra.client.renderers.blocks.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.renderers.items.base.CustomGeoItemRenderer;
import earth.terrarium.adastra.common.blockentities.SteamGeneratorBlockEntity;
import earth.terrarium.adastra.common.blocks.SteamGeneratorBlock;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SteamGeneratorBlockEntityRenderer extends GeoBlockRenderer<SteamGeneratorBlockEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation(AdAstra.MOD_ID, "steam_generator");

    public SteamGeneratorBlockEntityRenderer() {
        super(new DefaultedBlockGeoModel<>(MODEL));
    }

    @Override
    public ResourceLocation getTextureLocation(SteamGeneratorBlockEntity animatable) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock());
        if (animatable.getBlockState().getValue(SteamGeneratorBlock.LIT)) {
            return new ResourceLocation(AdAstra.MOD_ID, "textures/block/steam_generator/%s_on.png".formatted(id.getPath()));
        } else {
            return new ResourceLocation(AdAstra.MOD_ID, "textures/block/steam_generator/%s.png".formatted(id.getPath()));
        }
    }

    public static class ItemRender extends CustomGeoItemRenderer {
        public ItemRender(GeoModel<CustomGeoBlockItem> model) {
            super(model);
        }

        @Override
        public ResourceLocation getTextureLocation(CustomGeoBlockItem animatable) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(animatable.getBlock());
            return new ResourceLocation(AdAstra.MOD_ID, "textures/block/steam_generator/%s_on.png".formatted(id.getPath()));
        }
    }
}
