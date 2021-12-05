package net.mrscauthd.boss_tools.gui.helper;

import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;
import org.jetbrains.annotations.NotNull;

public class RocketPartGridPlacer {

	public static int place(int slot, int left, int top, int mod, IPlacer placer, RocketPart part, Consumer<Rect2i> consumer) {
		for (int i = 0; i < part.getSlots(); i++) {
			Rect2i bounds = placer.place(i, left, top, mod);
			consumer.accept(bounds);
			slot++;
		}
		return slot;
	}

	public static int placeContainer(int left, int top, int mod, IPlacer placer, RocketPart part, RocketPartsItemHandler itemHandler, Function<Slot, Slot> addSlot) {
		return place(0, left, top, mod, placer, part, (bounds) -> {
			IItemHandlerModifiable parent = itemHandler.getParent();
			int slot = itemHandler.getParentSlotIndex(part);
			addSlot.apply(new SlotItemHandler(parent, slot, bounds.getX(), bounds.getY()) {
				@Override
				public boolean mayPlace(@NotNull ItemStack stack) {
					return true;
				}
			});
		});
	}

	private RocketPartGridPlacer() {

	}

}
