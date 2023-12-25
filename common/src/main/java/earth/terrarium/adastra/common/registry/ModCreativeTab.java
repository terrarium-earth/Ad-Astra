package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "SameParameterValue"})
public class ModCreativeTab {

    public static final Supplier<CreativeModeTab> TAB = new ResourcefulCreativeTab(new ResourceLocation(AdAstra.MOD_ID, "main"))
        .setItemIcon(ModItems.TIER_1_ROCKET)
        .addContent(ModCreativeTab::getCustomNbtItems)
        .addRegistry(ModItems.ITEMS)
        .build();

    public static Stream<ItemStack> getCustomNbtItems() {
        List<ItemStack> list = new ArrayList<>();

        list.add(fluidFilledItem(ModItems.TIER_1_ROCKET, ModFluids.FUEL));
        list.add(fluidFilledItem(ModItems.TIER_2_ROCKET, ModFluids.FUEL));
        list.add(fluidFilledItem(ModItems.TIER_3_ROCKET, ModFluids.FUEL));
        list.add(fluidFilledItem(ModItems.TIER_4_ROCKET, ModFluids.FUEL));
        list.add(fluidFilledItem(ModItems.ROVER, ModFluids.FUEL));

        list.add(fluidFilledItem(ModItems.SPACE_SUIT, ModFluids.OXYGEN));
        list.add(fluidFilledItem(ModItems.NETHERITE_SPACE_SUIT, ModFluids.OXYGEN));
        list.add(energyFilledItem(fluidFilledItem(ModItems.JET_SUIT, ModFluids.OXYGEN)));
        list.add(fluidFilledItem(ModItems.ZIP_GUN, ModFluids.OXYGEN));
        list.add(energyFilledItem(ModItems.ETRIONIC_CAPACITOR));
        list.add(energyFilledItem(ModItems.ENERGIZER));
        list.add(fluidFilledItem(ModItems.GAS_TANK, ModFluids.OXYGEN));
        list.add(fluidFilledItem(ModItems.LARGE_GAS_TANK, ModFluids.OXYGEN));

        return list.stream();
    }

    private static ItemStack fluidFilledItem(RegistryEntry<Item> item, RegistryEntry<Fluid> fluid) {
        var stack = new ItemStackHolder(item.get().getDefaultInstance());
        var container = FluidApi.getItemFluidContainer(stack);
        var holder = FluidHooks.newFluidHolder(fluid.get(), container.getTankCapacity(0), null);
        container.insertFluid(holder, false);
        return stack.getStack();
    }

    private static ItemStack energyFilledItem(RegistryEntry<Item> item) {
        return energyFilledItem(item.get().getDefaultInstance());
    }

    private static ItemStack energyFilledItem(ItemStack stack) {
        var holder = new ItemStackHolder(stack);
        var container = EnergyApi.getItemEnergyContainer(holder);
        if (container != null) {
            container.setEnergy(container.getMaxCapacity());
            stack.getOrCreateTag()
                .getCompound(Botarium.BOTARIUM_DATA)
                .putLong("Energy", container.getMaxCapacity());
        }
        return holder.getStack();
    }

    public static void init() {} // NO-OP
}