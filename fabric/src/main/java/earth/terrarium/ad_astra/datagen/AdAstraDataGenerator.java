package earth.terrarium.ad_astra.datagen;

import earth.terrarium.ad_astra.datagen.provider.server.ModBlockTagGenerator;
import earth.terrarium.ad_astra.datagen.provider.server.ModItemTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AdAstraDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModItemTagGenerator::new);
        pack.addProvider(ModBlockTagGenerator::new);
    }
}
