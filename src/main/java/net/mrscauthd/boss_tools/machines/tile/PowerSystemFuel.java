package net.mrscauthd.boss_tools.machines.tile;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.inventory.StackCacher;

public abstract class PowerSystemFuel extends PowerSystem {

	private final int slot;

	private int fuel;
	private int maxFuel;

	private StackCacher itemStackCacher;
	private int cachedFuel;

	public PowerSystemFuel(AbstractMachineTileEntity tileEntity, int slot) {
		super(tileEntity);

		this.slot = slot;

		this.fuel = 0;
		this.maxFuel = 0;

		this.itemStackCacher = new StackCacher();
		this.cachedFuel = 0;
	}

	@Override
	public int getUsingSlots() {
		return this.getSlot() == -1 ? 0 : 1;
	}

	@Override
	public int receive(int amount, boolean simulate) {
		int received = Math.min(this.getCapacity() - this.getStored(), Math.max(amount, 0));

		if (!simulate) {
			this.fuel += received;
			this.getTileEntity().markDirty();
		}

		return received;
	}

	@Override
	public int extract(int amount, boolean simulate) {
		int extracted = Math.min(this.getStored(), Math.max(amount, 0));

		if (!simulate) {
			this.fuel -= extracted;
			this.getTileEntity().markDirty();
		}

		return extracted;
	}

	@Override
	public int getStored() {
		return this.fuel;
	}

	@Override
	public int getCapacity() {
		return this.maxFuel;
	}

	public boolean canFeed(boolean spareForNextTick, ItemStack fuel) {
		return this.getTileEntity().hasSpaceInOutput();
	}

	@Override
	public void deserializeNBT(CompoundNBT compound) {
		super.deserializeNBT(compound);

		this.fuel = compound.getInt("fuel");
		this.maxFuel = compound.getInt("maxFuel");
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = super.serializeNBT();

		compound.putInt("fuel", this.fuel);
		compound.putInt("maxFuel", this.maxFuel);

		return compound;
	}

	protected abstract int getFuelInternal(ItemStack fuel);

	public final int getFuel(ItemStack fuel) {
		if (fuel == null || fuel.isEmpty()) {
			this.itemStackCacher.set(fuel);
			this.cachedFuel = -1;
		} else if (!this.itemStackCacher.test(fuel)) {
			this.itemStackCacher.set(fuel);
			this.cachedFuel = this.getFuelInternal(fuel);
		}
		return this.cachedFuel;
	}

	@Override
	public boolean feed(boolean spareForNextTick) {
		if (spareForNextTick == false) {
			return false;
		}

		int slot = this.getSlot();

		if (slot == -1) {
			return false;
		}

		IItemHandlerModifiable itemHandler = this.getItemHandler();
		ItemStack fuelItemStack = itemHandler.getStackInSlot(slot);

		if (!fuelItemStack.isEmpty() && this.canFeed(spareForNextTick, fuelItemStack)) {
			int fuel = this.getFuel(fuelItemStack);

			if (fuel > 0) {
				itemHandler.extractItem(slot, 1, false);
				this.addFuel(fuel);
				return true;
			}
		}

		return false;
	}

	public void setFuel(int fuel) {
		fuel = Math.max(fuel, 0);
		this.maxFuel = fuel;
		this.receive(fuel, false);
	}

	public void addFuel(int fuel) {
		fuel = Math.max(fuel, 0);
		this.maxFuel = this.getStored() + fuel;
		this.receive(fuel, false);
	}

	public boolean matchDirection(Direction direction) {
		return direction == null || (direction != Direction.UP && direction != Direction.DOWN);
	}

	@Override
	public void getSlotsForFace(Direction direction, List<Integer> slots) {
		if (this.matchDirection(direction)) {
			slots.add(this.getSlot());
		}
	}

	@Override
	public boolean canInsertItem(@Nullable Direction direction, int index, ItemStack stack) {
		return this.matchDirection(direction) && index == this.getSlot() && this.getFuel(stack) > 0;
	}

	public IItemHandlerModifiable getItemHandler() {
		return this.getTileEntity().getItemHandler();
	}

	public int getSlot() {
		return this.slot;
	}

	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(BossToolsMod.ModId, "fuel");
	}
}
