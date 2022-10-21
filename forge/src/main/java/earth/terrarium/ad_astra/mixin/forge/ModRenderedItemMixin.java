package earth.terrarium.ad_astra.mixin.forge;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;

import earth.terrarium.ad_astra.client.forge.AdAstraClientForge;
import earth.terrarium.ad_astra.items.ModRenderedBlockItem;
import earth.terrarium.ad_astra.items.ModRenderedItem;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

@Mixin({ModRenderedItem.class, ModRenderedBlockItem.class})
public abstract class ModRenderedItemMixin extends Item {

    public ModRenderedItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Item item = this;

        consumer.accept(new IClientItemExtensions() {
            private BuiltinModelItemRenderer renderer = null;
            private boolean hasCheckedSinceInit = false;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (AdAstraClientForge.hasInitializedRenderers() && !hasCheckedSinceInit) {
                    renderer = AdAstraClientForge.getItemRenderer(item);
                    hasCheckedSinceInit = true;
                }
                return renderer == null ? IClientItemExtensions.super.getCustomRenderer() : renderer;
            }
        });
    }
}
