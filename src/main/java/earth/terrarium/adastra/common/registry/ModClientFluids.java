package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.client.fluid.data.ClientFluidProperties;
import com.teamresourceful.resourcefullib.client.registry.ResourcefulClientRegistries;
import com.teamresourceful.resourcefullib.client.registry.ResourcefulClientRegistryType;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;

public class ModClientFluids {
    public static final ResourcefulRegistry<ClientFluidProperties> CLIENT_FLUIDS = ResourcefulClientRegistries.create(ResourcefulClientRegistryType.FLUID, AdAstra.MOD_ID);

    public static final RegistryEntry<ClientFluidProperties> OXYGEN = CLIENT_FLUIDS.register("oxygen", () -> create(0xffdae6f0));
    public static final RegistryEntry<ClientFluidProperties> HYDROGEN = CLIENT_FLUIDS.register("hydrogen", () -> create(0xff89CFF0));
    public static final RegistryEntry<ClientFluidProperties> OIL = CLIENT_FLUIDS.register("oil", () -> create(0xff373A36));
    public static final RegistryEntry<ClientFluidProperties> FUEL = CLIENT_FLUIDS.register("fuel", () -> create(0xffE5292B));
    public static final RegistryEntry<ClientFluidProperties> CRYO_FUEL = CLIENT_FLUIDS.register("cryo_fuel", () -> create(0xff6cfffa));

    public static ClientFluidProperties create(int tintColor) {
        return new ClientFluidProperties() {
            @Override
            public ResourceLocation still(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState) {
                return ModFluidProperties.STILL;
            }

            @Override
            public ResourceLocation flowing(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState) {
                return ModFluidProperties.FLOWING;
            }

            @Override
            public ResourceLocation overlay(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState) {
                return ModFluidProperties.OVERLAY;
            }

            @Override
            public ResourceLocation screenOverlay() {
                return ModFluidProperties.SCREEN_OVERLAY;
            }

            @Override
            public int tintColor(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState) {
                return tintColor;
            }
        };
    }
}
