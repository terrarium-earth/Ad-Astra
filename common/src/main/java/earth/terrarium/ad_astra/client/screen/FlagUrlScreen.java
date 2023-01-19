package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.common.networking.packet.client.FlagUrlPacket;
import earth.terrarium.ad_astra.common.registry.ModNetworkHandling;
import earth.terrarium.ad_astra.common.util.LangUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public class FlagUrlScreen extends Screen {

    private static final Pattern URL_REGEX = Pattern.compile("^https://i\\.imgur\\.com/(\\w+)\\.(png|jpeg|jpg)$");

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
        int x = width / 2 - 100;
        int y = height / 2 - 20;
        button = addRenderableWidget(new Button(x + 50, y + 30, 100, 20, Component.translatable(LangUtils.CONFIRM), (button) -> {
            var matcher = URL_REGEX.matcher(urlField.getValue());
            if (matcher.matches()) {
                ModNetworkHandling.CHANNEL.sendToServer(new FlagUrlPacket(pos, matcher.group(1)));
                onClose();
            }
        }, Supplier::get) {
        });
        button.active = false;
        urlField = addRenderableWidget(new EditBox(font, x, y, 200, 20, Component.literal("https://imgur.com/urURL")));
        urlField.setResponder(url -> {
            if (URL_REGEX.matcher(url).matches()) {
                button.active = true;
                urlField.setTextColor(0x00FF00);
            } else {
                button.active = false;
                urlField.setTextColor(0xFF0000);
            }
        });
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        int x = width / 2 - 100;
        int y = height / 2 - 20;
        drawString(poseStack, font, Component.translatable(LangUtils.FLAG_URL), x, y - 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
