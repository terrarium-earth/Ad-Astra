package com.github.alexnijjar.ad_astra.mixin.forge;

import com.github.alexnijjar.ad_astra.client.forge.ModArmourExtention;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(SpaceSuit.class)
public abstract class SpaceSuitMixin {

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ModArmourExtention());
    }
}
