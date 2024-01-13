package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.adastra.common.utils.FluidUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "SameParameterValue"})
public class ModCreativeTab {

    public static final CreativeModeTab TAB = new ResourcefulCreativeModeTab(new ResourceLocation(AdAstra.MOD_ID, "main"))
        .setItemIcon(ModItems.TIER_1_ROCKET)
        .addContent(ModCreativeTab::getCustomNbtItems)
        .addRegistry(ModItems.ITEMS)
        .build();

    public static Stream<ItemStack> getCustomNbtItems() {
        List<ItemStack> list = new ArrayList<>();

        list.add(FluidUtils.fluidFilledItem(ModItems.TIER_1_ROCKET, ModFluids.FUEL));
        list.add(FluidUtils.fluidFilledItem(ModItems.TIER_2_ROCKET, ModFluids.FUEL));
        list.add(FluidUtils.fluidFilledItem(ModItems.TIER_3_ROCKET, ModFluids.FUEL));
        list.add(FluidUtils.fluidFilledItem(ModItems.TIER_4_ROCKET, ModFluids.FUEL));
        list.add(FluidUtils.fluidFilledItem(ModItems.ROVER, ModFluids.FUEL));

        list.add(FluidUtils.fluidFilledItem(ModItems.SPACE_SUIT, ModFluids.OXYGEN));
        list.add(FluidUtils.fluidFilledItem(ModItems.NETHERITE_SPACE_SUIT, ModFluids.OXYGEN));
        list.add(EnergyUtils.energyFilledItem(FluidUtils.fluidFilledItem(ModItems.JET_SUIT, ModFluids.OXYGEN)));
        list.add(FluidUtils.fluidFilledItem(ModItems.ZIP_GUN, ModFluids.OXYGEN));
        list.add(EnergyUtils.energyFilledItem(ModItems.ETRIONIC_CAPACITOR));
        list.add(EnergyUtils.energyFilledItem(ModItems.ENERGIZER));
        list.add(FluidUtils.fluidFilledItem(ModItems.GAS_TANK, ModFluids.OXYGEN));
        list.add(FluidUtils.fluidFilledItem(ModItems.LARGE_GAS_TANK, ModFluids.OXYGEN));

        return list.stream();
    }

    public static void init() {} // NO-OP
}