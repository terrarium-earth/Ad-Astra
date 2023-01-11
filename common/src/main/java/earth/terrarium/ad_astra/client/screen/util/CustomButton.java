package earth.terrarium.ad_astra.client.screen.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.util.PlanetSelectionScreen.TooltipType;
import earth.terrarium.ad_astra.common.data.ButtonColor;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.util.ColourUtils;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.LinkedList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CustomButton extends Button {

    public static final ResourceLocation LARGE_BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/large_button.png");
    public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/button.png");
    public static final ResourceLocation SMALL_BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/small_button.png");
    public static final ResourceLocation STEEL_BUTTON_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/buttons/steel_button.png");

    private final int startY;
    private final Component label;
    private final ButtonType buttonSize;
    private final Color buttonColourLightened;
    private final Color buttonColour;
    private final Planet planetInfo;

    private final PlanetSelectionScreen.TooltipType tooltip;
    public boolean doScissor = true;

    public CustomButton(int x, int y, Component label, ButtonType size, ButtonColor buttonColor, PlanetSelectionScreen.TooltipType tooltip, Planet planetInfo, OnPress onPress) {

        super(x, y, size.getWidth(), size.getHeight(), adjustText(label), onPress, Button.DEFAULT_NARRATION);

        this.startY = y;
        this.label = label;
        this.buttonSize = size;
        Color colour = buttonColor.getColour();
        // The button becomes lightened when the mouse hovers over the button.
        this.buttonColourLightened = ColourUtils.lighten(colour, 0.1f);
        // This is the normal colour when the button is not being hovered over.
        this.buttonColour = colour;
        this.tooltip = tooltip;
        this.planetInfo = planetInfo;
    }

    // Cut off the text if it's too large.
    public static Component adjustText(Component label) {
        int length = label.getString().length();
        if (length > 12 && length != 13) {
            return Component.nullToEmpty(label.getString(12) + ".");
        } else {
            return label;
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        this.setY(startY);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            double scale = minecraft.getWindow().getGuiScale();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int scissorY = (int) (((screenHeight / 2) - 83) * scale);

            boolean over = this.isMouseOver(mouseX, mouseY);
            Color color = over ? this.buttonColourLightened : this.buttonColour;

            poseStack.pushPose();
            poseStack.translate(5, 0, 0);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableDepthTest();

            if (this.doScissor) {
                RenderSystem.enableScissor(0, scissorY, (int) (215 * scale), (int) (127 * scale));
            }

            RenderSystem.setShaderColor(color.getFloatRed(), color.getFloatGreen(), color.getFloatBlue(), this.buttonColour.getFloatAlpha());
            RenderSystem.setShaderTexture(0, switch (this.buttonSize) {
                case LARGE -> LARGE_BUTTON_TEXTURE;
                case NORMAL -> BUTTON_TEXTURE;
                case SMALL -> SMALL_BUTTON_TEXTURE;
                case STEEL -> STEEL_BUTTON_TEXTURE;
            });

            blit(poseStack, (this.buttonSize.equals(ButtonType.LARGE) ? this.getX() - 2 : this.getX()), this.getY(), 0, 0, this.width, this.height, buttonSize.getWidth(), buttonSize.getHeight());
            drawText(poseStack, minecraft);

            if (this.doScissor) {
                RenderSystem.disableScissor();
            }

            if (this.isMouseOver(mouseX, mouseY)) {
                renderTooltips(poseStack, mouseX, mouseY, minecraft);
            }

            poseStack.popPose();
            RenderSystem.disableDepthTest();
        }
    }

    public void drawText(PoseStack poseStack, Minecraft minecraft) {
        Font textRenderer = minecraft.font;
        int colour = this.active ? 16777215 : 10526880;
        poseStack.pushPose();
        float scale = 0.9f;
        poseStack.scale(scale, scale, scale);
        int x = (this.buttonSize.equals(ButtonType.LARGE) ? this.getX() - 2 : this.getX());
        poseStack.translate(4 + (x / 9.5f), this.getY() / 8.5f, 0);
        drawCenteredString(poseStack, textRenderer, this.getMessage(), x + this.width / 2, this.getY() + (this.height - 8) / 2, colour | Mth.ceil(this.alpha * 255.0f) << 24);
        poseStack.popPose();
    }

    public int getStartY() {
        return this.startY;
    }

    private void renderTooltips(PoseStack poseStack, int mouseX, int mouseY, Minecraft minecraft) {

        Screen screen = minecraft.screen;
        List<Component> textEntries = new LinkedList<>();

        switch (tooltip) {
            case NONE -> {

            }
            case GALAXY -> {
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": §b" + label.getString()));
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": §5" + PlanetSelectionScreen.GALAXY_TEXT.getString()));
            }
            case SOLAR_SYSTEM -> {
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": §b" + label.getString()));
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": §3" + PlanetSelectionScreen.SOLAR_SYSTEM_TEXT.getString()));
            }
            case CATEGORY -> {
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.CATEGORY_TEXT.getString() + ": §a" + label.getString()));
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.PROVIDED_TEXT.getString() + ": §b" + Component.translatable("item.ad_astra.tier_" + planetInfo.rocketTier() + "_rocket").getString()));
            }
            case PLANET -> {
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": §3" + (planetInfo.parentWorld() == null ? PlanetSelectionScreen.PLANET_TEXT.getString() : PlanetSelectionScreen.MOON_TEXT.getString())));
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.GRAVITY_TEXT.getString() + ": §3" + planetInfo.gravity() + " m/s"));
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.OXYGEN_TEXT.getString() + ": §" + (planetInfo.hasOxygen() ? ('a' + PlanetSelectionScreen.OXYGEN_TRUE_TEXT.getString()) : ('c' + PlanetSelectionScreen.OXYGEN_FALSE_TEXT.getString()))));
                String temperatureColour = "§a";

                // Make the temperature text look orange when the temperature is hot and blue when the temperature is cold.
                if (planetInfo.temperature() > 50) {
                    // Hot.
                    temperatureColour = "§6";
                } else if (planetInfo.temperature() < -20) {
                    // Cold.
                    temperatureColour = "§1";
                }

                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.TEMPERATURE_TEXT.getString() + ": " + temperatureColour + " " + planetInfo.temperature() + " °C"));
            }
            case SPACE_STATION -> {
                PlanetSelectionScreen currentScreen = (PlanetSelectionScreen) minecraft.screen;
                textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.ITEM_REQUIREMENT_TEXT.getString()));

                currentScreen.ingredients.forEach(ingredient -> {
                    boolean isEnough = ingredient.getFirst().getCount() >= ingredient.getSecond();
                    textEntries.add(Component.nullToEmpty("§" + (isEnough ? "a" : "c") + ingredient.getFirst().getCount() + "/" + ingredient.getSecond() + " §3" + ingredient.getFirst().getHoverName().getString()));
                });
                textEntries.add(Component.nullToEmpty("§c----------------"));
            }
            default -> {

            }
        }

        if (tooltip.equals(TooltipType.ORBIT) || tooltip.equals(TooltipType.SPACE_STATION)) {
            textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.TYPE_TEXT.getString() + ": §3" + PlanetSelectionScreen.ORBIT_TEXT.getString()));
            textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.GRAVITY_TEXT.getString() + ": §3" + PlanetSelectionScreen.NO_GRAVITY_TEXT.getString()));
            textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.OXYGEN_TEXT.getString() + ": §c " + PlanetSelectionScreen.OXYGEN_FALSE_TEXT.getString()));
            textEntries.add(Component.nullToEmpty("§9" + PlanetSelectionScreen.TEMPERATURE_TEXT.getString() + ": §1 " + ModUtils.ORBIT_TEMPERATURE + " °C"));
        }

        screen.renderComponentTooltip(poseStack, textEntries, mouseX, mouseY);
    }
}