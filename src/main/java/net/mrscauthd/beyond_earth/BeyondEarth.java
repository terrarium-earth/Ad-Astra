package net.mrscauthd.beyond_earth;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.recipes.GeneratingRecipe;
import net.mrscauthd.beyond_earth.recipes.HammerShapelessRecipe;
import net.mrscauthd.beyond_earth.registry.*;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeyondEarth implements ModInitializer {

    public static final String MOD_ID = "beyond_earth";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        // Registry.
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModFluids.register();
        ModArmour.register();
        ModScreenHandlers.register();
        ModRecipeTypes.register();

        // Recipe serializers.
        Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("hammer_recipe"), new HammerShapelessRecipe.Serializer());
        Registry.register(Registry.RECIPE_SERIALIZER,  GeneratingRecipe.GENERATING_ID, new GeneratingRecipe.Serializer());

        BeyondEarth.LOGGER.info("Beyond Earth Initialized!");
    }
}
