package earth.terrarium.adastra.client.renderers.blocks.base;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.HashMap;
import java.util.Map;

public class CustomGeoBlockRenderer<T extends BlockEntity & GeoAnimatable> extends GeoBlockRenderer<T> {
    private final Map<Block, ResourceLocation> textureMap = new HashMap<>(1);

    public CustomGeoBlockRenderer(RegistryEntry<Block> block) {
        super(new DefaultedBlockGeoModel<>(block.getId()));
    }

    public CustomGeoBlockRenderer(GeoModel<T> model) {
        super(model);
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return this.textureMap.computeIfAbsent(animatable.getBlockState().getBlock(), block -> {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            return new ResourceLocation(AdAstra.MOD_ID, "textures/block/%s.png".formatted(id.getPath()));
        });
    }
}
