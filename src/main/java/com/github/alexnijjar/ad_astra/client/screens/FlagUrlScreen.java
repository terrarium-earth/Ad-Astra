package com.github.alexnijjar.ad_astra.client.screens;

import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

import java.util.regex.Pattern;

public class FlagUrlScreen extends Screen {

    private static final Pattern URL_REGEX = Pattern.compile("^https://i\\.imgur\\.com/(\\w+)\\.png$");

    private final BlockPos pos;
    private TextFieldWidget urlField;
    private ButtonWidget button;

    public FlagUrlScreen(BlockPos pos) {
        super(Text.of(""));
        this.pos = pos;
    }

    public static void open(BlockPos pos) {
        MinecraftClient.getInstance().setScreen(new FlagUrlScreen(pos));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 20;
        this.button = addDrawableChild(new ButtonWidget(x+50, y + 30, 100, 20, new TranslatableText("gui.ad_astra.text.confirm"), (button) -> {
            var matcher = URL_REGEX.matcher(this.urlField.getText());
            if (matcher.matches()) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(this.pos);
                buf.writeString(matcher.group(1));
                ClientPlayNetworking.send(ModC2SPackets.FLAG_URL, buf);
                this.onClose();
            }
        }));
        this.button.active = false;
        this.urlField = addDrawableChild(new TextFieldWidget(textRenderer, x, y, 200, 20, Text.of("https://imgur.com/urURL")));
        this.urlField.setChangedListener(url -> {
            if (URL_REGEX.matcher(url).matches()) {
                this.button.active = true;
                this.urlField.setEditableColor(0x00FF00);
            } else {
                this.button.active = false;
                this.urlField.setEditableColor(0xFF0000);
            }
        });
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 20;
        drawTextWithShadow(matrices, textRenderer, new TranslatableText("gui.ad_astra.text.flag_url"), x, y - 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
