package earth.terrarium.adastra.mixins.forge.common;

import earth.terrarium.adastra.client.forge.AdAstraClientForge;
import earth.terrarium.adastra.common.items.rendered.RenderedBlockItem;
import earth.terrarium.adastra.common.items.rendered.RenderedItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin({RenderedItem.class, RenderedBlockItem.class})
public abstract class RenderedItemMixin extends Item {

    public RenderedItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return AdAstraClientForge.ITEM_RENDERERS.get(RenderedItemMixin.this);
            }
        });
    }
}
