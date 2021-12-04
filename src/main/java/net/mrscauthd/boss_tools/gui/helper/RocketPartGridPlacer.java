package net.mrscauthd.boss_tools.gui.helper;

import java.util.function.Function;

import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.util.ThreeConsumer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;

public class RocketPartGridPlacer {

	public static int place(int slot, int left, int top, int mod, IPlacer placer, RocketPart part, ThreeConsumer<Integer, Integer, Rectangle2d> consumer) {
		for (int i = 0; i < part.getSlots(); i++) {
			Rectangle2d bounds = placer.place(i, left, top, mod);
			consumer.accept(i, slot, bounds);
			slot++;
		}
		return slot;
	}

	public static int placeContainer(int left, int top, int mod, IPlacer placer, RocketPart part, RocketPartsItemHandler itemHandler, Function<Slot, Slot> addSlot) {
		return place(0, left, top, mod, placer, part, (i, s, bounds) -> {
			IItemHandlerModifiable parent = itemHandler.getParent();
			int slot = itemHandler.getParentSlotIndex(part, i);
			addSlot.apply(new SlotItemHandler(parent, slot, bounds.getX(), bounds.getY()) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return true;
				}
			});
		});
	}

	private RocketPartGridPlacer() {

	}

}
