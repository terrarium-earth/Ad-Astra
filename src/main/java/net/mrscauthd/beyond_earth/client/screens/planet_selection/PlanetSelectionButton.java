package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Environment(EnvType.CLIENT)
public class PlanetSelectionButton extends ButtonWidget {

    public static final Identifier LARGE_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/large_button.png");
    public static final Identifier BUTTON_TEXTURE = new ModIdentifier("textures/buttons/button.png");
    public static final Identifier SMALL_BUTTON_TEXTURE = new ModIdentifier("textures/buttons/small_button.png");

    private final int tier;
    private final TooltipType tooltip;
    private ModUtils.ColourHolder buttonColourLightened;
    private ModUtils.ColourHolder buttonColour;
    private PlanetSelectionScreen.ButtonSize buttonSize;
    private RegistryKey<World> world;

    public PlanetSelectionButton(int y, PlanetSelectionScreen.ButtonColour buttonColour, Text message,
            TooltipType tooltip,
            int tier, PlanetSelectionScreen.ButtonSize size, RegistryKey<World> world, PressAction onPress) {
        super(10, y, size.getWidth(), size.getHeight(), message, onPress);

        ModUtils.ColourHolder colour = buttonColour.getColour();
        this.buttonColourLightened = colour;
        this.buttonColour = new ModUtils.ColourHolder(colour.r() - 0.1f, colour.g() - 0.1f, colour.b() - 0.1f,
                colour.a() - 0.1f);
        this.buttonSize = size;
        this.world = world;

        this.tier = tier;
        this.tooltip = tooltip;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            MinecraftClient client = MinecraftClient.getInstance();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            ModUtils.ColourHolder lightColour = this.buttonColourLightened;
            ModUtils.ColourHolder color = this.buttonColour;
            Boolean over = this.isMouseOver(mouseX, mouseY);
            RenderSystem.setShaderColor((over ? lightColour.r() : color.r()), (over ? lightColour.g() : color.g()),
                    (over ? lightColour.b() : color.b()), (over ? lightColour.a() : color.a()));
            RenderSystem.enableDepthTest();

            // button texture.
            RenderSystem.setShaderTexture(0, switch (this.buttonSize) {
                case LARGE -> LARGE_BUTTON_TEXTURE;
                case NORMAL -> BUTTON_TEXTURE;
                case SMALL -> SMALL_BUTTON_TEXTURE;
            });

            drawTexture(matrices, this.x, this.y, 0, 0, this.width, this.height, buttonSize.getWidth(),
                    buttonSize.getHeight());

            TextRenderer textRenderer = client.textRenderer;
            int colour = this.active ? 16777215 : 10526880;
            drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2,
                    this.y + (this.height - 8) / 2, colour | MathHelper.ceil(this.alpha * 255.0f) << 24);

            renderTooltips(matrices, mouseX, mouseY, client);

            RenderSystem.disableDepthTest();
        }
    }

    private void renderTooltips(MatrixStack matrices, int mouseX, int mouseY, MinecraftClient client) {
        if (this.isMouseOver(mouseX, mouseY)) {

            Screen screen = client.currentScreen;

            char condition = 'a';
            List<Text> list = new LinkedList<>();

            switch (tooltip) {
                case NONE -> {
                    return;
                }
                case CATEGORY -> {
                    list = Arrays.asList(
                            Text.of("\u00A79" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": \u00A7"
                                    + condition + this.getMessage().getString()),
                            Text.of("\u00A79" + PlanetSelectionScreen.PROVIDED_TEXT.getString() + ": \u00A7b"
                                    + "Tier " + tier + " Rocket"));
                }
                case PLANET_STAT -> {
                    list = Arrays.asList(
                            Text.of("\u00A79" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": \u00A73"
                                    + (ModUtils.isPlanet(world) ? "Planet" : PlanetSelectionScreen.ORBIT_TEXT)),
                            Text.of("\u00A79" + PlanetSelectionScreen.GRAVITY_TEXT.getString() + ": \u00A73"
                                    + String.valueOf(ModUtils.getTrueGravity(world)) + " m/s"),
                            Text.of("\u00A79" + PlanetSelectionScreen.OXYGEN_TEXT.getString() + ": \u00A7 "
                                    + String.valueOf(ModUtils.dimensionHasOxygen(world))),
                            Text.of("\u00A79" + PlanetSelectionScreen.TEMPERATURE_TEXT.getString() + ": \u00A7 "
                                    + String.valueOf(ModUtils.getTemperature(world)) + " C"));
                }
            }

            screen.renderTooltip(matrices, list, mouseX, mouseY);
        }
    }

    public enum TooltipType {
        NONE,
        CATEGORY,
        PLANET_STAT
    }
}