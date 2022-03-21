package net.mrscauthd.beyond_earth;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.items.HammerShapelessRecipe;
import net.mrscauthd.beyond_earth.registry.ModArmour;
import net.mrscauthd.beyond_earth.registry.ModBlocks;
import net.mrscauthd.beyond_earth.registry.ModFluids;
import net.mrscauthd.beyond_earth.registry.ModItems;
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
        ModFluids.register();
        ModArmour.register();

        // Recipe serializers.
        Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("hammer_recipe_serializer"), new HammerShapelessRecipe.Serializer());

        BeyondEarth.LOGGER.info("Beyond Earth Initialized!");
    }
}
