package net.mrscauthd.astrocraft.compat.mekanism;

public class MekanismHelper {
	/*
	public static Capability<IGasHandler> getGasHandlerCapability() {
		return Capabilities.GAS_HANDLER_CAPABILITY;
	}

	public static IGasHandler getItemStackGasHandler(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return null;
		}

		return itemStack.getCapability(getGasHandlerCapability()).orElse(null);
	}

	public static IOxygenStorage getItemStackOxygenAdapter(ItemStack itemStack) {
		IGasHandler gasHandler = getItemStackGasHandler(itemStack);

		if (gasHandler != null) {
			return new GasHandlerOxygenAdapter(gasHandler, true, true);
		} else {
			return null;
		}
	}

	public static List<TOPChemicalElement> createGasGaugeDataElement(IGasHandler gasHandler) {
		List<TOPChemicalElement> list = new ArrayList<>();

		if (gasHandler != null) {
			int tanks = gasHandler.getTanks();

			for (int i = 0; i < tanks; i++) {
				list.add(new TOPChemicalElement.GasElement(gasHandler.getChemicalInTank(i), gasHandler.getTankCapacity(i)));
			}
		}

		return list;
	}

	 */
}