package earth.terrarium.adastra.client.components.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.components.base.ContainerWidget;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.RedstoneControl;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetRedstoneControlPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OptionsBarWidget extends ContainerWidget {

    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/optionbar.png");
    public static final int PADDING = 6;
    public static final int SPACING = 3;

    private final GridLayout layout = Util.make(new GridLayout(0, 0), layout -> {
        layout.columnSpacing(SPACING);
        layout.defaultCellSetting().alignVerticallyMiddle();
    });

    private final @Nullable Runnable onSettingsPressed;
    private final @Nullable ContainerMachineBlockEntity entity;
    private final boolean hasBattery;

    public OptionsBarWidget(int right, int top, @Nullable Runnable onSettingsPressed, @Nullable ContainerMachineBlockEntity entity, boolean hasBattery) {
        super(0, 0, 0, 0);

        this.onSettingsPressed = onSettingsPressed;
        this.entity = entity;
        this.hasBattery = hasBattery;

        init();

        this.width = layout.getWidth() + PADDING * 2;
        this.height = layout.getHeight() + PADDING * 2;
        this.x = right - this.getWidth();
        this.y = top - this.getHeight();

        layout.setX(this.getX() + PADDING);
        layout.setY(this.getY() + PADDING);
    }

    protected void init() {
        if (this.onSettingsPressed != null) {
            layout.addChild(new PressableImageButton(0, 0, 18, 18, 0, 0, 18, GuiUtils.SETTINGS_BUTTON, 18, 54,
                button -> this.onSettingsPressed.run(),
                ConstantComponents.SIDE_CONFIG
            ), 0, 0);
        }

        if (this.entity != null) {
            layout.addChild(new PressableImageButton(0, 0, 18, 18, 0, 0, 18, entity.getRedstoneControl().icon(), 18, 54,
                button -> {
                    RedstoneControl next = Screen.hasShiftDown() ? entity.getRedstoneControl().previous() : entity.getRedstoneControl().next();
                    entity.setRedstoneControl(next);
                    NetworkHandler.CHANNEL.sendToServer(new ServerboundSetRedstoneControlPacket(entity.getBlockPos(), next));
                    button.setTooltip(Tooltip.create(getRedstoneControlTooltip(next)));
                    ((PressableImageButton) button).setTexture(next.icon());
                },
                getRedstoneControlTooltip(entity.getRedstoneControl())
            ), 0, 1);
        }

        if (this.hasBattery) {
            layout.addChild(new SpacerElement(18, 18), 0, 2);
        }

        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blitNineSliced(TEXTURE,
            this.getX(), this.getY(),
            this.getWidth(), this.getHeight(),
            PADDING, PADDING, PADDING, PADDING,
            30, 30,
            0, 0
        );
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    public static Component getRedstoneControlTooltip(RedstoneControl redstoneControl) {
        return CommonComponents.joinLines(
            ConstantComponents.REDSTONE_CONTROL,
            Component.translatable("tooltip.ad_astra.redstone_control.mode", redstoneControl.translation().getString()).withStyle(ChatFormatting.GOLD)
        );
    }

    @Override
    public void onDimensionsChanged() {
        layout.setX(this.getX() + PADDING);
        layout.setY(this.getY() + PADDING);
    }
}
