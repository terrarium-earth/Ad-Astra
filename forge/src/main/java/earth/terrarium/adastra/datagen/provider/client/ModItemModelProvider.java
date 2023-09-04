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
        basicItem(ModItems.ETRIUM_INGOT.get());
        basicItem(ModItems.ETRIUM_NUGGET.get());
        basicItem(ModItems.ETRIUM_PLATE.get());
        basicItem(ModItems.ETRIUM_ROD.get());

        basicItem(ModItems.WRENCH.get());
        basicItem(ModItems.ETRIONIC_CORE.get());
        basicItem(ModItems.PHOTOVOLTAIC_ETRIUM_CELL.get());
        basicItem(ModItems.PHOTOVOLTAIC_VESNIUM_CELL.get());

        basicItem(ModItems.GAS_TANK.get());
        basicItem(ModItems.LARGE_GAS_TANK.get());

        basicItem(ModItems.AEROLYTE_SPACE_HELMET.get());
        basicItem(ModItems.AEROLYTE_SPACE_SUIT.get());
        basicItem(ModItems.AEROLYTE_SPACE_PANTS.get());
        basicItem(ModItems.AEROLYTE_SPACE_BOOTS.get());

        basicItem(ModItems.OXYGEN_BUCKET.get());
        basicItem(ModItems.HYDROGEN_BUCKET.get());
        basicItem(ModItems.OIL_BUCKET.get());
    }
}
