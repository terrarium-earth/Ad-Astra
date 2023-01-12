package earth.terrarium.ad_astra.fabric;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModCommands;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.network.chat.Component;

public class AdAstraFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AdAstra.init();
        ModEntityTypes.registerAttributes((type, builder) -> FabricDefaultAttributeRegistry.register(type.get(), builder.get()));
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> ModCommands.registerCommands(command -> command.accept(dispatcher)));
        ModItems.onRegisterCreativeTabs((loc, item, items) -> FabricItemGroup.builder(loc)
                .title(Component.translatable("itemGroup." + loc.getNamespace() + "." + loc.getPath()))
                .icon(() -> item.get().getDefaultInstance())
                .displayItems((featureFlagSet, output, bl) -> items.forEach(output::accept))
                .build());
    }
}
