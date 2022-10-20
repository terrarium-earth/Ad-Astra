package earth.terrarium.ad_astra.datagen;

import earth.terrarium.ad_astra.AdAstra;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AdAstraDataGenEntrypoint implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		AdAstra.LOGGER.info("Ad Astra Datagen started...");

		// Client
		fabricDataGenerator.addProvider(ModModelProvider::new);

		// Server
		fabricDataGenerator.addProvider(ModBlockLootTableProvider::new);
		fabricDataGenerator.addProvider(ModRecipeProvider::new);
		AdAstra.LOGGER.info("Ad Astra Datagen finished 🚀");
	}
}
