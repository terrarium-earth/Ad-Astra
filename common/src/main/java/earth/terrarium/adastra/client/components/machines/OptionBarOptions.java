package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetFurnaceModePacket;
import earth.terrarium.adastra.common.network.messages.ServerboundSetRedstoneControlPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class OptionBarOptions {

    public static PressableImageButton createSettings(Runnable action) {
        return new PressableImageButton(0, 0, 18, 18, 0, 0, 18, GuiUtils.SETTINGS_BUTTON, 18, 54,
            button -> action.run(),
            ConstantComponents.SIDE_CONFIG
        );
    }

    public static PressableImageButton createRedstone(ContainerMachineBlockEntity entity) {
        return new PressableImageButton(0, 0, 18, 18, 0, 0, 18, entity.getRedstoneControl().icon(), 18, 54,
            button -> {
                RedstoneControl next = Screen.hasShiftDown() ? entity.getRedstoneControl().previous() : entity.getRedstoneControl().next();
                entity.setRedstoneControl(next);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetRedstoneControlPacket(entity.getBlockPos(), next));
                button.setTooltip(Tooltip.create(getRedstoneControlTooltip(next)));
                ((PressableImageButton) button).setTexture(next.icon());
            },
            getRedstoneControlTooltip(entity.getRedstoneControl())
        );
    }

    public static PressableImageButton createBlastFurnaceMode(EtrionicBlastFurnaceBlockEntity furnace) {
        return new PressableImageButton(-10, 0, 18, 18, 0, 0, 18, furnace.mode().icon(), 18, 54,
            button -> {
                EtrionicBlastFurnaceBlockEntity.Mode next = Screen.hasShiftDown() ? furnace.mode().previous() : furnace.mode().next();
                furnace.setMode(next);
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetFurnaceModePacket(furnace.getBlockPos(), next));
                button.setTooltip(Tooltip.create(getModeTooltip(next)));
                ((PressableImageButton) button).setTexture(next.icon());
            },
            getModeTooltip(furnace.mode())
        );
    }

    // Tooltips

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
}
