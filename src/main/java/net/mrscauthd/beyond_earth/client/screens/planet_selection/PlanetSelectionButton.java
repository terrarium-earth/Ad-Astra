package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PlanetSelectionButton extends ButtonWidget {

    public static final Identifier RED_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/red_button.png");
    public static final Identifier RED_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/red_button_2.png");

    public static final Identifier BLUE_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/blue_button.png");
    public static final Identifier BLUE_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/blue_button_2.png");

    public static final Identifier GREEN_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/green_button.png");
    public static final Identifier GREEN_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/green_button_2.png");

    public static final Identifier DARK_BLUE_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/dark_blue_button.png");
    public static final Identifier DARK_BLUE_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/dark_blue_button_2.png");

    public static final Identifier SMALL_BLUE_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/small_blue_button.png");
    public static final Identifier SMALL_BLUE_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/small_blue_button_2.png");

    public static final Identifier LARGE_GREEN_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/big_green_button.png");
    public static final Identifier LARGE_GREEN_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/big_green_button_2.png");

    public static final Identifier LARGE_RED_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/big_red_button.png");
    public static final Identifier LARGE_RED_LIGHT_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/big_red_button_2.png");

    public static final int BUTTON_TEXTURE_WIDTH = 70;
    public static final int BUTTON_TEXTURE_HEIGHT = 20;
    private final int tier;
    private final ToolTipType tooltip;
    private Identifier buttonTexture = null;
    private Identifier hoverButtonTexture = null;

    public PlanetSelectionButton(int y, PlanetSelectionScreen.ButtonType type, Text message, ToolTipType tooltip, int tier, PressAction onPress) {
        super(10, y, 70, 20, message, onPress);
        switch (type) {
            case RED -> {
                buttonTexture = RED_BUTTON_TEXTURE;
                hoverButtonTexture = RED_LIGHT_BUTTON_TEXTURE;
            }
            case BLUE -> {
                buttonTexture = BLUE_BUTTON_TEXTURE;
                hoverButtonTexture = BLUE_LIGHT_BUTTON_TEXTURE;
            }
            case GREEN -> {
                buttonTexture = GREEN_BUTTON_TEXTURE;
                hoverButtonTexture = GREEN_LIGHT_BUTTON_TEXTURE;
            }
            case DARK_BLUE -> {
                buttonTexture = DARK_BLUE_BUTTON_TEXTURE;
                hoverButtonTexture = DARK_BLUE_LIGHT_BUTTON_TEXTURE;
            }
        }
        this.tier = tier;
        this.tooltip = tooltip;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            MinecraftClient client = MinecraftClient.getInstance();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.enableDepthTest();

            // button texture.
            RenderSystem.setShaderTexture(0, this.isMouseOver(mouseX, mouseY) ? hoverButtonTexture : buttonTexture);
            drawTexture(matrices, this.x, this.y, 0, 0, this.width, this.height, BUTTON_TEXTURE_WIDTH, BUTTON_TEXTURE_HEIGHT);

            TextRenderer textRenderer = client.textRenderer;
            int colour = this.active ? 16777215 : 10526880;
            drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, colour | MathHelper.ceil(this.alpha * 255.0F) << 24);

            renderTooltips(matrices, mouseX, mouseY);

            RenderSystem.disableDepthTest();
        }
    }

    private void renderTooltips(MatrixStack matrices, int mouseX, int mouseY) {
        if (this.isMouseOver(mouseX, mouseY)) {

            Screen screen = MinecraftClient.getInstance().currentScreen;
            if (screen != null) {

                char condition = 'a';
                List<Text> list = new LinkedList<>();

                switch (tooltip) {
                    case NONE -> {
                        return;
                    }
                    case CATEGORY -> {
                        list = Arrays.asList(
                                Text.of("\u00A79" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": \u00A7" + condition + this.getMessage().getString()),
                                Text.of("\u00A79" + PlanetSelectionScreen.PROVIDED_TEXT.getString() + ": \u00A7b" + "Tier " + tier + " Rocket")
                        );
                    }
                    case PLANET_STAT -> {

                    }
                }

                screen.renderTooltip(matrices, list, mouseX, mouseY);
            }
        }
    }

    public enum ToolTipType {
        NONE,
        CATEGORY,
        PLANET_STAT
    }
}
