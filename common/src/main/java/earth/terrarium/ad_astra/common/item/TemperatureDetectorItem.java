package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.common.system.TemperatureSystem;
import earth.terrarium.ad_astra.common.util.LangUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TemperatureDetectorItem extends Item {
    public TemperatureDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.displayClientMessage(Component.translatable(LangUtils.TEMPERATURE_DETECTED, TemperatureSystem.getPosTemperature(level, player.blockPosition())), true);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }
}
