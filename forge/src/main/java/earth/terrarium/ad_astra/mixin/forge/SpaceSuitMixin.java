package earth.terrarium.ad_astra.mixin.forge;

import earth.terrarium.ad_astra.client.forge.ModArmourExtension;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(SpaceSuit.class)
public abstract class SpaceSuitMixin {

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ModArmourExtension());
    }
}
