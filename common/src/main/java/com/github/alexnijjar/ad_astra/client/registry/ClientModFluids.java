package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import dev.architectury.hooks.fluid.FluidStackHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.screen.PlayerScreenHandler;

@Environment(EnvType.CLIENT)
public class ClientModFluids {

    public static void register() {
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL,
				new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_fuel_still"), new ModIdentifier("block/fluid_fuel_flow"), new ModIdentifier("block/fuel_overlay")));
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.CRYO_FUEL_STILL, ModFluids.FLOWING_CRYO_FUEL,
				new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_cryo_fuel_still"), new ModIdentifier("block/fluid_cryo_fuel_flow"), new ModIdentifier("block/cryo_fuel_overlay")));
		FluidStackHooks.INSTANCE.register(ModFluids.OIL_STILL, ModFluids.FLOWING_OIL, new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_oil_still"), new ModIdentifier("block/fluid_oil_flow"), new ModIdentifier("block/oil_overlay")));
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OXYGEN_STILL, new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_oxygen_still"), new ModIdentifier("block/fluid_oxygen_still"), new ModIdentifier("block/fluid_oxygen_still")));

		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(new ModIdentifier("block/fluid_fuel_still"));
			registry.register(new ModIdentifier("block/fluid_fuel_flow"));
			registry.register(new ModIdentifier("block/fuel_overlay"));

			registry.register(new ModIdentifier("block/fluid_cryo_fuel_still"));
			registry.register(new ModIdentifier("block/fluid_cryo_fuel_flow"));
			registry.register(new ModIdentifier("block/cryo_fuel_overlay"));

			registry.register(new ModIdentifier("block/fluid_oil_still"));
			registry.register(new ModIdentifier("block/fluid_oil_flow"));
			registry.register(new ModIdentifier("block/oil_overlay"));

			registry.register(new ModIdentifier("block/fluid_oxygen_still"));
		});

		ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(new ModIdentifier("entity/chest/aeronos_chest"));
			registry.register(new ModIdentifier("entity/chest/aeronos_chest_right"));
			registry.register(new ModIdentifier("entity/chest/aeronos_chest_left"));
			registry.register(new ModIdentifier("entity/chest/strophar_chest"));
			registry.register(new ModIdentifier("entity/chest/strophar_chest_right"));
			registry.register(new ModIdentifier("entity/chest/strophar_chest_left"));
		});

		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.CRYO_FUEL_STILL, ModFluids.FLOWING_CRYO_FUEL);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OIL_STILL, ModFluids.FLOWING_OIL);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OXYGEN_STILL);
    }
}
