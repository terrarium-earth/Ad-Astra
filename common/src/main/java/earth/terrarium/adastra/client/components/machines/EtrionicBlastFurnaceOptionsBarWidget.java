package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetFurnaceModePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class EtrionicBlastFurnaceOptionsBarWidget extends OptionsBarWidget {

    public EtrionicBlastFurnaceOptionsBarWidget(int right, int top, @Nullable Runnable onSettingsPressed, @Nullable EtrionicBlastFurnaceBlockEntity entity) {
        super(right, top, onSettingsPressed, entity, true);
    }

    @Override
    protected void init() {
        if (entity instanceof EtrionicBlastFurnaceBlockEntity furnace) {
            layout.addChild(new PressableImageButton(-10, 0, 18, 18, 0, 0, 18, furnace.mode().icon(), 18, 54,
                button -> {
                    EtrionicBlastFurnaceBlockEntity.Mode next = Screen.hasShiftDown() ? furnace.mode().previous() : furnace.mode().next();
                    furnace.setMode(next);
                    NetworkHandler.CHANNEL.sendToServer(new ServerboundSetFurnaceModePacket(entity.getBlockPos(), next));
                    button.setTooltip(Tooltip.create(getModeTooltip(next)));
                    ((PressableImageButton) button).setTexture(next.icon());
                },
                getModeTooltip(furnace.mode())
            ), 0, 0);
        }

        addSettingsButton(1);
        addRedstoneButton(2);

        addBattery(3);

        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);
    }

    public static Component getModeTooltip(EtrionicBlastFurnaceBlockEntity.Mode mode) {
        return CommonComponents.joinLines(
            ConstantComponents.ETRIONIC_BLAST_FURNACE_MODE,
            Component.translatable("tooltip.ad_astra.etrionic_blast_furnace.mode", mode.translation().getString()).withStyle(ChatFormatting.GOLD)
        );
    }
}
