package net.mrscauthd.beyond_earth.capability.air;

import net.minecraft.world.entity.player.Player;

public class AirUtil {

	public static IAirStorage getEntityAirStorage(Player player) {
		IAirStorage air = player.getCapability(CapabilityAir.AIR).orElse(null);


		if (air != null) {
			return air;
		}

		return null;
	}

	public static boolean canReceive(Player player) {
		if (player == null) {
			return false;
		}

		IAirStorage storageInItemStack = getEntityAirStorage(player);
		return storageInItemStack != null && storageInItemStack.receiveAir(1, true) > 0;
	}

	public static boolean canExtract(Player player) {
		if (player == null) {
			return false;
		}

		IAirStorage storageInItemStack = getEntityAirStorage(player);
		return storageInItemStack != null && storageInItemStack.extractAir(1, true) > 0;
	}

	public static Player makeFull(Player player) {
		if (player == null) {
			return player;
		}

		IAirStorage storageInItemStack = getEntityAirStorage(player);

		if (storageInItemStack != null) {
			storageInItemStack.receiveAir(storageInItemStack.getMaxAirStored(), false);
		}

		return player;
	}

	private AirUtil() {

	}
}
