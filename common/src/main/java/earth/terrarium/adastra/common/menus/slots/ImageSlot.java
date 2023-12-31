package earth.terrarium.adastra.common.menus.slots;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public class ImageSlot extends Slot {
    private final ResourceLocation icon;

    public ImageSlot(Container container, int slot, int xPosition, int yPosition, ResourceLocation icon) {
        super(container, slot, xPosition, yPosition);
        this.icon = icon;
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, icon);
    }

    @Nullable
    public ResourceLocation getSlotTexture() {
        return null;
    }
}