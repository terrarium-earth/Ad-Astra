package net.mrscauthd.beyond_earth.capability.air;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerAirCapabilityProvider implements ICapabilityProvider, IAirStorageHolder, INBTSerializable<CompoundTag> {

	public static final String KEY_AIR = "air";

	private Player player;
	private IAirStorage airStorage;

	public PlayerAirCapabilityProvider(Player player, int capacity) {
		this.player = player;
		this.airStorage = new AirStorage(this, capacity);

		this.readAir();
	}

	private void readAir() {
		CompoundTag compound = this.getPlayer().getPersistentData();
		this.getAirStorage().setAirStored(compound.getInt(KEY_AIR));
	}

	public void writeAir() {
		CompoundTag compound = this.serializeNBT();
		compound.putInt(KEY_AIR, this.getAirStorage().getAirStored());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
		if (capability == CapabilityAir.AIR) {
			this.readAir();
			return LazyOptional.of(this::getAirStorage).cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public void onAirChanged(IAirStorage airStorage, int airDelta) {
		this.writeAir();
	}

	public Player getPlayer() {
		return this.player;
	}

	public IAirStorage getAirStorage() {
		return this.airStorage;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.airStorage.setAirStored(nbt.getInt("air"));
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();

		compound.putInt("air", this.getAirStorage().getAirStored());

		return compound;
	}
}
