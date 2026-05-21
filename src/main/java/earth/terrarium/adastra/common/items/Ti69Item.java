package earth.terrarium.adastra.common.items;

import earth.terrarium.adastra.client.renderers.ti69.apps.SensorApp;
import earth.terrarium.adastra.client.renderers.ti69.apps.Ti69App;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Ti69Item extends Item {

    public static final Ti69App APP = new SensorApp();

    public Ti69Item(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.TI_69_INFO);
    }
}
