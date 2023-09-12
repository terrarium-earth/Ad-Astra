package earth.terrarium.adastra.client.renderers.blocks.machines;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.renderers.blocks.base.CustomGeoBlockRenderer;
import earth.terrarium.adastra.client.renderers.items.base.CustomGeoItemRenderer;
import earth.terrarium.adastra.common.blockentities.machines.SteamGeneratorBlockEntity;
import earth.terrarium.adastra.common.blocks.machines.SteamGeneratorBlock;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class SteamGeneratorBlockEntityRenderer extends CustomGeoBlockRenderer<SteamGeneratorBlockEntity> {

    public SteamGeneratorBlockEntityRenderer(RegistryEntry<Block> block) {
        super(block);
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
        public ItemRender(RegistryEntry<Block> block) {
            super(block);
        }

        @Override
        public ResourceLocation getTextureLocation(CustomGeoBlockItem animatable) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(animatable.getBlock());
            return new ResourceLocation(AdAstra.MOD_ID, "textures/block/steam_generator/%s_on.png".formatted(id.getPath()));
        }
    }
}
