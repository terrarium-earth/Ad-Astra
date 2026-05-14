package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.packets.ServerboundSetSideConfigPacket;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ConfigurationButton extends PressableImageButton {

    private final ContainerMachineBlockEntity entity;
    private final int configIndex;
    private final ConfigurationEntry entry;
    private final Direction direction;

    private Configuration configuration;

    public ConfigurationButton(ContainerMachineBlockEntity entity, int index, ConfigurationEntry entry, Direction direction, Configuration configuration) {
        super(0, 0, 16, 16, spritesFromConfiguration(configuration),
            (b) -> {},
            getSideConfigTooltip(entity, entry.type(), direction, configuration)
        );

        this.entity = entity;
        this.configIndex = index;
        this.entry = entry;
        this.direction = direction;

        this.configuration = configuration;
    }

    @Override
    public void onPress() {
        this.configuration = Screen.hasShiftDown() ? this.configuration.previous() : this.configuration.next();
        entry.set(direction, this.configuration);
        NetworkHandler.CHANNEL.sendToServer(new ServerboundSetSideConfigPacket(entity.getBlockPos(), configIndex, direction, this.configuration));
        setTooltip(Tooltip.create(getSideConfigTooltip(entity, entry.type(), direction, this.configuration)));
        setSprites(spritesFromConfiguration(configuration));
    }

    private static Component getSideConfigTooltip(BlockEntity entity, ConfigurationType type, Direction direction, Configuration action) {
        Direction relative = ModUtils.relative(entity, direction);

        return CommonComponents.joinLines(
            Component.translatable("side_config.ad_astra.type.type", type.translation().getString()).withStyle(ChatFormatting.GREEN),
            Component.translatable("side_config.ad_astra.type.direction", TooltipUtils.getRelativeDirectionComponent(direction), TooltipUtils.getDirectionComponent(relative)).withStyle(ChatFormatting.GOLD),
            Component.translatable("side_config.ad_astra.type.action", action.translation().getString()).withStyle(ChatFormatting.GOLD)
        );
    }

    public static WidgetSprites spritesFromConfiguration(Configuration configuration) {
        return switch (configuration) {
            case NONE -> GuiUtils.NONE_BUTTON_SPRITES;
            case PUSH -> GuiUtils.PUSH_BUTTON_SPRITES;
            case PULL -> GuiUtils.PULL_BUTTON_SPRITES;
            case PUSH_PULL -> GuiUtils.PUSH_PULL_BUTTON_SPRITES;
        };
    }
}
