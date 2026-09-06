package earth.terrarium.adastra.client.screens.blocks;

import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.packets.ServerboundSetFlagUrlPacket;
import earth.terrarium.adastra.common.utils.ImageHostUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public class FlagUrlScreen extends Screen {

    private final BlockPos pos;
    private EditBox urlField;
    private Button button;

    public FlagUrlScreen(BlockPos pos) {
        super(Component.empty());
        this.pos = pos;
    }

    public static void open(BlockPos pos) {
        Minecraft.getInstance().setScreen(new FlagUrlScreen(pos));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 20;
        this.button = addRenderableWidget(new Button(x + 50, y + 30, 100, 20, ConstantComponents.CONFIRM, (button) -> {
            String url = this.urlField.getValue();
            if (ImageHostUtils.isValidFlagImageURL(url)) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetFlagUrlPacket(this.pos, url));
                this.onClose();
            }
        }, Supplier::get) {});
        button.active = false;
        urlField = addRenderableWidget(new EditBox(font, x, y, 200, 20, Component.literal("https://imgur.com/urURL")));
        urlField.setResponder(url -> {
            if (ImageHostUtils.isValidFlagImageURL(url)) {
                this.button.active = true;
                this.urlField.setTextColor(0x00FF00);
            } else {
                this.button.active = false;
                this.urlField.setTextColor(0xFF0000);
            }
        });
        urlField.setMaxLength(64);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 20;
        graphics.drawString(font, ConstantComponents.FLAG_URL, x, y - 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
