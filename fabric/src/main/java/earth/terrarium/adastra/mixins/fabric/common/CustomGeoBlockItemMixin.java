package earth.terrarium.adastra.mixins.fabric.common;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(CustomGeoBlockItem.class)
public abstract class CustomGeoBlockItemMixin extends Item implements GeoItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public CustomGeoBlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = AdAstraClient.getItemRenderer(CustomGeoBlockItemMixin.this);
                }

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
}