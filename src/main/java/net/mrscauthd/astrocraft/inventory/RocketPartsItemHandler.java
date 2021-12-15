package net.mrscauthd.astrocraft.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.mrscauthd.astrocraft.crafting.RocketPart;

public class RocketPartsItemHandler implements IItemHandlerModifiable {

	private final IItemHandlerModifiable parent;
	private final int slotStartIndex;

	private final int slotCount;
	private final List<RocketPart> list;
	private final Map<RocketPart, IItemHandlerModifiable> map;

	public RocketPartsItemHandler(IItemHandlerModifiable parent, int slotStartIndex, Collection<RocketPart> parts) {
		this.parent = parent;
		this.slotStartIndex = slotStartIndex;

		List<RocketPart> list = new ArrayList<>();
		Map<RocketPart, IItemHandlerModifiable> map = new HashMap<>();
		int slotCount = 0;

		for (RocketPart part : parts) {
			int count = part.getSlots();
			int slotNumber = this.getParentSlotIndex(slotCount);

			list.add(part);
			map.put(part, new RangedWrapper(parent, slotNumber, slotNumber + count));
			slotCount += count;
		}

		this.slotCount = slotCount;
		this.list = Collections.unmodifiableList(list);
		this.map = Collections.unmodifiableMap(map);
	}

	public IItemHandlerModifiable getParent() {
		return this.parent;
	}

	public int getSlotStartIndex() {
		return this.slotStartIndex;
	}

	public final List<RocketPart> getPartOrders() {
		return this.list;
	}

	public final Map<RocketPart, IItemHandlerModifiable> getSubHandlers() {
		return this.map;
	}

	public int getParentSlotIndex(int slot) {
		return this.getSlotStartIndex() + slot;
	}

	public int getParentSlotIndex(RocketPart getPart) {
		int index = this.getSlotStartIndex();

		for (RocketPart part : this.getPartOrders()) {
			if (part == getPart) {
				return index;
			}

			index += this.getSubHandlers().get(part).getSlots();
		}

		return -1;
	}

	public int getParentSlotIndex(RocketPart getPart, int innerSlot) {
		int partSlot = this.getParentSlotIndex(getPart);
		return partSlot != -1 ? (partSlot + innerSlot) : -1;
	}

	@Nullable
	public Pair<IItemHandlerModifiable, Integer> findInventory(int slot) {
		for (RocketPart part : this.getPartOrders()) {
			IItemHandlerModifiable subHandler = this.getSubHandlers().get(part);
			int inventorySize = subHandler.getSlots();

			if (slot < inventorySize) {
				return Pair.of(subHandler, slot);
			} else {
				slot -= inventorySize;
			}
		}

		return null;
	}

	public void consumeInventory(int slot, BiConsumer<IItemHandlerModifiable, Integer> consumer) {
		Pair<IItemHandlerModifiable, Integer> pair = this.findInventory(slot);

		if (pair != null) {
			consumer.accept(pair.getKey(), pair.getValue());
		}

	}

	public <T> T queryInventory(int slot, BiFunction<IItemHandlerModifiable, Integer, T> function) {
		return this.queryInventory(slot, function, null);
	}

	public <T> T queryInventory(int slot, BiFunction<IItemHandlerModifiable, Integer, T> function, T fallback) {
		Pair<IItemHandlerModifiable, Integer> pair = this.findInventory(slot);

		if (pair != null) {
			return function.apply(pair.getKey(), pair.getValue());
		}

		return fallback;
	}

	@Override
	public int getSlots() {
		return this.slotCount;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.queryInventory(slot, IItemHandler::getStackInSlot, ItemStack.EMPTY);
	}

	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return this.queryInventory(slot, (h, s) -> h.insertItem(s, stack, simulate), ItemStack.EMPTY);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return this.queryInventory(slot, (h, s) -> h.extractItem(s, amount, simulate), ItemStack.EMPTY);
	}

	@Override
	public int getSlotLimit(int slot) {
		return this.queryInventory(slot, IItemHandler::getSlotLimit, 0);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return this.queryInventory(slot, (h, s) -> h.isItemValid(s, stack), false);
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		this.consumeInventory(slot, (h, s) -> h.setStackInSlot(s, stack));
	}

}
