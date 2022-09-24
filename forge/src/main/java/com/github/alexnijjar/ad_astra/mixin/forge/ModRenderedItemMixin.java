package com.github.alexnijjar.ad_astra.mixin.forge;

import com.github.alexnijjar.ad_astra.client.forge.AdAstraClientForge;
import com.github.alexnijjar.ad_astra.items.ModRenderedBlockItem;
import com.github.alexnijjar.ad_astra.items.ModRenderedItem;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin({ModRenderedItem.class, ModRenderedBlockItem.class})
public abstract class ModRenderedItemMixin extends Item {

    public ModRenderedItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Item item = this;

        consumer.accept(new IClientItemExtensions() {
            private final BuiltinModelItemRenderer renderer = AdAstraClientForge.getItemRenderer(item);

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return renderer == null ? IClientItemExtensions.super.getCustomRenderer() : renderer;
            }
        });
    }
}
