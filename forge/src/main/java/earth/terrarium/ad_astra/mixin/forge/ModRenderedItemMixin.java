package earth.terrarium.ad_astra.mixin.forge;

import earth.terrarium.ad_astra.client.forge.AdAstraClientForge;
import earth.terrarium.ad_astra.common.item.RenderedBlockItem;
import earth.terrarium.ad_astra.common.item.RenderedItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin({RenderedItem.class, RenderedBlockItem.class})
public abstract class ModRenderedItemMixin extends Item {

    public ModRenderedItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Item item = this;

        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;
            private boolean hasCheckedSinceInit;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (AdAstraClientForge.hasInitializedRenderers() && !hasCheckedSinceInit) {
                    renderer = AdAstraClientForge.getItemRenderer(item);
                    hasCheckedSinceInit = true;
                }
                return renderer == null ? IClientItemExtensions.super.getCustomRenderer() : renderer;
            }
        });
    }
}
