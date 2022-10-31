package earth.terrarium.ad_astra.client.renderer.armour.forge;

import earth.terrarium.ad_astra.client.renderer.armour.ArmourModelSupplier;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class ArmourRenderersImpl {
    private static final Map<Item, ArmourModelSupplier> ARMOUR_ITEM_MODELS = new HashMap<>();

    public static void registerArmour(ArmourModelSupplier modelSupplier, Item... items) {
        for (Item item : items) {
            registerArmourRenderer(item, modelSupplier);
        }
    }

    public static ArmourModelSupplier getArmourRenderer(ItemLike item) {
        return ARMOUR_ITEM_MODELS.get(item.asItem());
    }

    private static void registerArmourRenderer(ItemLike item, ArmourModelSupplier renderer) {
        ARMOUR_ITEM_MODELS.put(item.asItem(), renderer);
    }
}
