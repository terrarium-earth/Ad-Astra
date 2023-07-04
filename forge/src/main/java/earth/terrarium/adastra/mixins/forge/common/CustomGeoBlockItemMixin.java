package earth.terrarium.adastra.mixins.forge.common;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(CustomGeoBlockItem.class)
public abstract class CustomGeoBlockItemMixin extends Item {

    public CustomGeoBlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
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
}
