package earth.terrarium.adastra.common.items;

import earth.terrarium.adastra.api.ti69.client.Ti69AppApi;
import earth.terrarium.adastra.client.ti69.apps.SensorApp;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundSyncWeatherPacket;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ti69Item extends Item {
    public static ResourceLocation selectedApp = SensorApp.ID;

    public Ti69Item(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (level.isClientSide()) {
            List<ResourceLocation> apps = new ArrayList<>(Ti69AppApi.API.apps().keySet());
            apps.sort(ResourceLocation::compareTo);
            int index = apps.indexOf(selectedApp);
            if (index == -1) {
                index = 0;
            } else {
                index = (index + 1) % apps.size();
            }
            selectedApp = apps.get(index);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        ComponentUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.TI_69_INFO);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide()) return;
        if (level.getGameTime() % 20 != 0) return;
        if (!(entity instanceof Player player)) return;

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Ti69Item || player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Ti69Item) {
            ServerLevelData data = Objects.requireNonNull(player.getServer()).getWorldData().overworldData();
            int clearTime = data.getClearWeatherTime();
            int rainTime = data.getRainTime();
            int thunderTime = data.getThunderTime();
            boolean raining = data.isRaining();
            boolean thundering = data.isThundering();
            NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncWeatherPacket(clearTime, rainTime, thunderTime, raining, thundering), player);
        }
    }
}
