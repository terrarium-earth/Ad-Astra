package earth.terrarium.adastra.datagen.provider.client;


import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems.BASIC_ITEMS.getEntries().stream().map(RegistryEntry::get).forEach(this::basicItem);
        ModItems.SLIDING_DOORS.getEntries().stream().map(RegistryEntry::get).forEach(this::basicItem);
        ModItems.PIPES.getEntries().stream().map(RegistryEntry::get).forEach(this::basicItem);
        ModItems.SPAWN_EGGS.getEntries().stream().map(RegistryEntry::get).forEach(this::spawnEggItem);
    }

    public void spawnEggItem(Item item) {
        getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString())
            .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}
