package earth.terrarium.adastra.common.items.armor;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetheriteSpaceSuitItem extends SpaceSuitItem {

    public NetheriteSpaceSuitItem(ArmorMaterial material, Type type, long tankSize, Properties properties) {
        super(material, type, tankSize, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(TooltipUtils.getFluidComponent(
            FluidUtils.getTank(stack),
            FluidConstants.fromMillibuckets(tankSize),
            ModFluids.OXYGEN.get()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.NETHERITE_SPACE_SUIT_INFO);
    }
}
