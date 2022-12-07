package earth.terrarium.ad_astra.mixin.forge;

import earth.terrarium.ad_astra.client.forge.AdAstraClientForge;
import earth.terrarium.ad_astra.item.ModRenderedBlockItem;
import earth.terrarium.ad_astra.item.ModRenderedItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin({ModRenderedItem.class, ModRenderedBlockItem.class})
public abstract class ModRenderedItemMixin extends Item {

    public ModRenderedItemMixin(Properties settings) {
        super(settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Item item = this;

        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer = null;
            private boolean hasCheckedSinceInit = false;

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
