package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetFurnaceModePacket;
import earth.terrarium.adastra.common.network.messages.ServerboundSetRedstoneControlPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class OptionBarOptions {

    public static PressableImageButton createSettings(Runnable action) {
        return new PressableImageButton(0, 0, 18, 18, GuiUtils.SETTINGS_BUTTON_SPRITES,
            button -> action.run(),
            ConstantComponents.SIDE_CONFIG
        );
    }

    public static PressableImageButton createRedstone(ContainerMachineBlockEntity entity) {
        return new PressableImageButton(0, 0, 18, 18, spritesFromRedstoneControl(entity.getRedstoneControl()),
            button -> {
                RedstoneControl next = Screen.hasShiftDown() ? entity.getRedstoneControl().previous() : entity.getRedstoneControl().next();
                entity.setRedstoneControl(next);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetRedstoneControlPacket(entity.getBlockPos(), next));
                button.setTooltip(Tooltip.create(getRedstoneControlTooltip(next)));
                ((PressableImageButton) button).setSprites(spritesFromRedstoneControl(next));
            },
            getRedstoneControlTooltip(entity.getRedstoneControl())
        );
    }

    public static PressableImageButton createBlastFurnaceMode(EtrionicBlastFurnaceBlockEntity entity) {
        return new PressableImageButton(0, 0, 18, 18, spritesFromEtrionicBlastFurnaceMode(entity.mode()),
            button -> {
                EtrionicBlastFurnaceBlockEntity.Mode next = Screen.hasShiftDown() ? entity.mode().previous() : entity.mode().next();
                entity.setMode(next);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetFurnaceModePacket(entity.getBlockPos(), next));
                button.setTooltip(Tooltip.create(getModeTooltip(next)));
                ((PressableImageButton) button).setSprites(spritesFromEtrionicBlastFurnaceMode(next));
            },
            getModeTooltip(entity.mode())
        );
    }

    public static PressableImageButton createOxygenDistributorShowMode() {
        return new PressableImageButton(0, 0, 18, 18, AdAstraConfigClient.showOxygenDistributorArea ? GuiUtils.SHOW_BUTTON_SPRITES : GuiUtils.HIDE_BUTTON_SPRITES,
            button -> {
                AdAstraConfigClient.showOxygenDistributorArea = !AdAstraConfigClient.showOxygenDistributorArea;
                Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
                ((PressableImageButton) button).setSprites(AdAstraConfigClient.showOxygenDistributorArea ? GuiUtils.SHOW_BUTTON_SPRITES : GuiUtils.HIDE_BUTTON_SPRITES);
            },
            ConstantComponents.OXYGEN_DISTRIBUTION_AREA
        );
    }

    public static PressableImageButton createGravityNormalizerShowMode() {
        return new PressableImageButton(0, 0, 18, 18, AdAstraConfigClient.showGravityNormalizerArea ? GuiUtils.SHOW_BUTTON_SPRITES : GuiUtils.HIDE_BUTTON_SPRITES,
            button -> {
                AdAstraConfigClient.showGravityNormalizerArea = !AdAstraConfigClient.showGravityNormalizerArea;
                Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
                ((PressableImageButton) button).setSprites(AdAstraConfigClient.showGravityNormalizerArea ? GuiUtils.SHOW_BUTTON_SPRITES : GuiUtils.HIDE_BUTTON_SPRITES);
            },
            ConstantComponents.GRAVITY_DISTRIBUTION_AREA
        );
    }

    private static Component getRedstoneControlTooltip(RedstoneControl redstoneControl) {
        return CommonComponents.joinLines(
            ConstantComponents.REDSTONE_CONTROL,
            Component.translatable("tooltip.ad_astra.redstone_control.mode", redstoneControl.translation().getString()).withStyle(ChatFormatting.GOLD)
        );
    }

    private static Component getModeTooltip(EtrionicBlastFurnaceBlockEntity.Mode mode) {
        return CommonComponents.joinLines(
            ConstantComponents.ETRIONIC_BLAST_FURNACE_MODE,
            Component.translatable("tooltip.ad_astra.etrionic_blast_furnace.mode", mode.translation().getString()).withStyle(ChatFormatting.GOLD)
        );
    }

    public static WidgetSprites spritesFromRedstoneControl(RedstoneControl redstoneControl) {
        return switch (redstoneControl) {
            case ALWAYS_ON -> GuiUtils.REDSTONE_ALWAYS_ON_SPRITES;
            case ON_WHEN_POWERED -> GuiUtils.REDSTONE_ON_WHEN_POWERED_SPRITES;
            case ON_WHEN_NOT_POWERED -> GuiUtils.REDSTONE_ON_WHEN_NOT_POWERED_SPRITES;
            case NEVER_ON -> GuiUtils.REDSTONE_NEVER_ON_SPRITES;
        };
    }

    public static WidgetSprites spritesFromEtrionicBlastFurnaceMode(EtrionicBlastFurnaceBlockEntity.Mode mode) {
        return switch (mode) {
            case ALLOYING -> GuiUtils.CRAFTING_BUTTON_SPRITES;
            case BLASTING -> GuiUtils.FURNACE_BUTTON_SPRITES;
        };
    }
}
