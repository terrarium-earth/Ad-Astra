package com.github.alexnijjar.beyond_earth.client.screens.utils;

import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.client.screens.utils.PlanetSelectionScreen.TooltipType;
import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.util.ColourHolder;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.util.ModUtils;
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

@Environment(EnvType.CLIENT)
public class CustomButton extends ButtonWidget {

    public static final Identifier LARGE_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/large_button.png");
    public static final Identifier BUTTON_TEXTURE = new ModIdentifier("textures/buttons/button.png");
    public static final Identifier SMALL_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/small_button.png");

    public static final Identifier WHITE_TEXTURE = new ModIdentifier("textures/white.png");

    private int startY;
    private Text label;
    private ButtonSize buttonSize;
    private ColourHolder buttonColourLightened;
    private ColourHolder buttonColour;
    private Planet planetInfo;

    private final PlanetSelectionScreen.TooltipType tooltip;
    public boolean doMask = true;

    public CustomButton(int x, int y, Text label, ButtonSize size, ButtonColour buttonColour, PlanetSelectionScreen.TooltipType tooltip, Planet planetInfo, PressAction onPress) {

        super(x, y, size.getWidth(), size.getHeight(), adjustText(label), onPress);

        this.startY = y;
        this.label = label;
        this.buttonSize = size;
        ColourHolder colour = buttonColour.getColour();
        // The button becomes lightened when the mouse hovers over the button.
        this.buttonColourLightened = new ColourHolder(colour.r() + 0.1f, colour.g() + 0.1f, colour.b() + 0.1f);
        // This is the normal colour when the button is not being hovered over.
        this.buttonColour = colour;
        this.tooltip = tooltip;
        this.planetInfo = planetInfo;
    }

    // Cut off the text if it's too large.
    public static Text adjustText(Text label) {
        int length = label.getString().length();
        if (length > 12 && length != 13) {
            return Text.of(label.asTruncatedString(12) + ".");
        } else {
            return label;
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        this.y = startY;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {

            MinecraftClient client = MinecraftClient.getInstance();
            double scale = client.getWindow().getScaleFactor();
            int screenHeight = client.getWindow().getScaledHeight();
            int scissorY = (int)(((screenHeight / 2) - 83) * scale);

            ColourHolder lightColour = this.buttonColourLightened;
            ColourHolder color = this.buttonColour;
            boolean over = this.isMouseOver(mouseX, mouseY);

            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableDepthTest();

            if (this.doMask) {
                // Render mask
                RenderSystem.enableScissor(0, scissorY, (int)(215 * scale), (int)(127 * scale));
            }

            RenderSystem.setShaderColor((over ? lightColour.r() : color.r()), (over ? lightColour.g() : color.g()), (over ? lightColour.b() : color.b()), 1.0f);
            RenderSystem.setShaderTexture(0, switch (this.buttonSize) {
            case LARGE -> LARGE_BUTTON_TEXTURE;
            case NORMAL -> BUTTON_TEXTURE;
            case SMALL -> SMALL_BUTTON_TEXTURE;
            });

            drawTexture(matrices, this.x, this.y, 0, 0, this.width, this.height, buttonSize.getWidth(), buttonSize.getHeight());
            drawText(matrices, client);

            if (this.doMask) {
                RenderSystem.disableScissor();
            }

            if (this.isMouseOver(mouseX, mouseY)) {
                renderTooltips(matrices, mouseX, mouseY, client);
            }

            RenderSystem.disableDepthTest();
        }
    }

    public void drawText(MatrixStack matrices, MinecraftClient client) {
        TextRenderer textRenderer = client.textRenderer;
        int colour = this.active ? 16777215 : 10526880;
        drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, colour | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }

    public int getStartY() {
        return this.startY;
    }

    private void renderTooltips(MatrixStack matrices, int mouseX, int mouseY, MinecraftClient client) {

        Screen screen = client.currentScreen;
        List<Text> textEntries = new LinkedList<>();

        switch (tooltip) {
        case NONE -> {

        }
        case SOLAR_SYSTEM -> {
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": \u00A7b" + label.getString()));
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": \u00A73" + PlanetSelectionScreen.SOLAR_SYSTEM_TEXT.getString()));
        }
        case CATEGORY -> {
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": \u00A7a" + label.getString()));
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.PROVIDED_TEXT.getString() + ": \u00A7b" + "Tier " + planetInfo.rocketTier() + " Rocket"));
        }
        case PLANET -> {
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": \u00A73" + (planetInfo.parentWorld() == null ? PlanetSelectionScreen.PLANET_TEXT.getString() : PlanetSelectionScreen.MOON_TEXT.getString())));
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.GRAVITY_TEXT.getString() + ": \u00A73" + planetInfo.gravity() + " m/s"));
            textEntries
                    .add(Text.of("\u00A79" + PlanetSelectionScreen.OXYGEN_TEXT.getString() + ": \u00A7" + (planetInfo.hasOxygen() ? ('a' + PlanetSelectionScreen.OXYGEN_TRUE_TEXT.getString()) : ('c' + PlanetSelectionScreen.OXYGEN_FALSE_TEXT.getString()))));
            String temperatureColour = "\u00A7a";

            // Make the temperature text look orange when the temperature is hot and blue when the temperature is cold.
            if (planetInfo.temperature() > 50) {
                // Hot.
                temperatureColour = "\u00A76";
            } else if (planetInfo.temperature() < -20) {
                // Cold.
                temperatureColour = "\u00A71";
            }

            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.TEMPERATURE_TEXT.getString() + ": " + temperatureColour + " " + planetInfo.temperature() + " °C"));
        }
        case SPACE_STATION -> {
            PlanetSelectionScreen currentScreen = (PlanetSelectionScreen) client.currentScreen;
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.ITEM_REQUIREMENT_TEXT.getString()));

            currentScreen.ingredients.forEach(ingredient -> {
                boolean isEnough = ingredient.first.getCount() >= ingredient.second;
                textEntries.add(Text.of("\u00A7" + (isEnough ? "a" : "c") + ingredient.first.getCount() + "/" + ingredient.second + " \u00A73" + ingredient.first.getName().getString()));
            });
            textEntries.add(Text.of("\u00A7c----------------"));
        }
        default -> {

        }
        }

        if (tooltip.equals(TooltipType.ORBIT) || tooltip.equals(TooltipType.SPACE_STATION)) {
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": \u00A73" + PlanetSelectionScreen.ORBIT_TEXT.getString()));
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.GRAVITY_TEXT.getString() + ": \u00A73" + PlanetSelectionScreen.NO_GRAVITY_TEXT.getString()));
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.OXYGEN_TEXT.getString() + ": \u00A7c " + PlanetSelectionScreen.OXYGEN_FALSE_TEXT.getString()));
            textEntries.add(Text.of("\u00A79" + PlanetSelectionScreen.TEMPERATURE_TEXT.getString() + ": \u00A71 " + ModUtils.ORBIT_TEMPERATURE + " °C"));
        }

        screen.renderTooltip(matrices, textEntries, mouseX, mouseY);
    }
}