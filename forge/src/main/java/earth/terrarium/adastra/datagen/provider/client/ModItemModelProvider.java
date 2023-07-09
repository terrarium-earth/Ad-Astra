package earth.terrarium.adastra.datagen.provider.client;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public static final ResourceLocation RENDERED_ITEM = new ResourceLocation(AdAstra.MOD_ID, "item/rendered_item");
    public static final ResourceLocation SMALL_RENDERED_ITEM = new ResourceLocation(AdAstra.MOD_ID, "item/small_rendered_item");
    public static final ResourceLocation SOLAR_PANEL = new ResourceLocation(AdAstra.MOD_ID, "item/solar_panel");

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.AEROLYTE_SPACE_HELMET.get());
        basicItem(ModItems.AEROLYTE_SPACE_SUIT.get());
        basicItem(ModItems.AEROLYTE_SPACE_PANTS.get());
        basicItem(ModItems.AEROLYTE_SPACE_BOOTS.get());

        basicItem(ModItems.OXYGEN_BUCKET.get());
        basicItem(ModItems.HYDROGEN_BUCKET.get());
        basicItem(ModItems.OIL_BUCKET.get());
    }
}
