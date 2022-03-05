package net.mrscauthd.beyond_earth.gui.helper;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.beyond_earth.crafting.RocketPart;
import net.mrscauthd.beyond_earth.inventory.RocketPartsItemHandler;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

public class RocketPartGridPlacer {

	public static void place(int left, int top, int mod, IPlacer placer, RocketPart part, BiConsumer<Integer, Rectangle2d> consumer) {
		for (int i = 0; i < part.getSlots(); i++) {
			Rectangle2d bounds = placer.place(i, left, top, mod);
			consumer.accept(i, bounds);
		}
	}

	public static void placeContainer(int left, int top, int mod, IPlacer placer, RocketPart part, RocketPartsItemHandler itemHandler, Function<Slot, Slot> addSlot) {
		place(left, top, mod, placer, part, (i, bounds) -> {
			IItemHandlerModifiable parent = itemHandler.getParent();
			int slot = itemHandler.getParentSlotIndex(part, i);
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
