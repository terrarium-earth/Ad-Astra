package earth.terrarium.adastra.client.screens.blocks;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetFlagUrlPacket;
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

    private static final Pattern URL_REGEX = Pattern.compile("^https://i\\.imgur\\.com/(\\w+)\\.(png|jpeg|jpg|webp)$");

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
            var matcher = URL_REGEX.matcher(this.urlField.getValue());
            if (matcher.matches()) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetFlagUrlPacket(this.pos, matcher.group(1)));
                this.onClose();
            }
        }, Supplier::get) {});
        button.active = false;
        urlField = addRenderableWidget(new EditBox(font, x, y, 200, 20, Component.literal("https://imgur.com/urURL")));
        urlField.setResponder(url -> {
            if (URL_REGEX.matcher(url).matches()) {
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
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 20;
        graphics.drawString(font, ConstantComponents.FLAG_URL, x, y - 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
