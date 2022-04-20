package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class PlanetSelectionScreen extends HandledScreen<PlanetSelectionScreenHandler> {

    public static final Identifier BACKGROUND_TEXTURE = new ModIdentifier("textures/screens/planet_selection.png");

    public static final Identifier MILKY_WAY_TEXTURE = new ModIdentifier("textures/sky/gui/milky_way.png");

    public static final Identifier SUN_SOLAR_SYSTEM_TEXTURE = new ModIdentifier("textures/solar_system.png");
    public static final Identifier PROXIMA_CENTAURI_SOLAR_SYSTEM_TEXTURE = new ModIdentifier("textures/proxima_centauri.png");

    public static final Identifier SUN_TEXTURE = new ModIdentifier("textures/sky/gui/sun.png");
    public static final Identifier BLUE_SUN_TEXTURE = new ModIdentifier("textures/sky/gui/blue_sun.png");
    public static final Identifier MARS_TEXTURE = new ModIdentifier("textures/sky/gui/mars.png");
    public static final Identifier EARTH_TEXTURE = new ModIdentifier("textures/sky/gui/earth.png");
    public static final Identifier VENUS_TEXTURE = new ModIdentifier("textures/sky/gui/venus.png");
    public static final Identifier MERCURY_TEXTURE = new ModIdentifier("textures/sky/gui/mercury.png");
    public static final Identifier GLACIO_TEXTURE = new ModIdentifier("textures/sky/gui/glacio.png");

    public static final Identifier SMALL_MENU_LIST = new ModIdentifier("textures/rocket_menu_list.png");
    public static final Identifier LARGE_MENU_TEXTURE = new ModIdentifier("textures/rocket_menu_list_2.png");

    // Text.
    public static final Text CATALOG_TEXT = PlanetSelectionUtil.createText("catalog");
    public static final Text BACK_TEXT = PlanetSelectionUtil.createText("back");

    public static final Text SUN_TEXT = PlanetSelectionUtil.createText("sun");
    public static final Text PROXIMA_CENTAURI_TEXT = PlanetSelectionUtil.createText("proxima_centauri");

    public static final Text SOLAR_SYSTEM_TEXT = PlanetSelectionUtil.createText("solar_system");

    public static final Text SOLAR_SYSTEM_SUN_TEXT = PlanetSelectionUtil.createText("solar_system_sun");
    public static final Text SOLAR_SYSTEM_PROXIMA_CENTAURI_TEXT = PlanetSelectionUtil.createText("solar_system_proxima_centauri");

    public static final Text EARTH_TEXT = PlanetSelectionUtil.createText("earth");
    public static final Text MARS_TEXT = PlanetSelectionUtil.createText("mars");
    public static final Text MERCURY_TEXT = PlanetSelectionUtil.createText("mercury");
    public static final Text VENUS_TEXT = PlanetSelectionUtil.createText("venus");
    public static final Text GLACIO_TEXT = PlanetSelectionUtil.createText("glacio");

    public static final Text PLANET_TEXT = PlanetSelectionUtil.createText("planet");
    public static final Text MOON_TEXT = PlanetSelectionUtil.createText("moon");

    public static final Text ORBIT_TEXT = PlanetSelectionUtil.createText("orbit");

    public static final Text NO_GRAVITY_TEXT = PlanetSelectionUtil.createText("no_gravity");

    public static final Text SPACE_STATION_TEXT = PlanetSelectionUtil.createText("space_station");

    public static final Text CATEGORY_TEXT = PlanetSelectionUtil.createText("category");
    public static final Text PROVIDED_TEXT = PlanetSelectionUtil.createText("provided");
    public static final Text TYPE_TEXT = PlanetSelectionUtil.createText("type");
    public static final Text GRAVITY_TEXT = PlanetSelectionUtil.createText("gravity");
    public static final Text OXYGEN_TEXT = PlanetSelectionUtil.createText("oxygen");
    public static final Text TEMPERATURE_TEXT = PlanetSelectionUtil.createText("temperature");
    public static final Text OXYGEN_TRUE_TEXT = PlanetSelectionUtil.createText("oxygen.true");
    public static final Text OXYGEN_FALSE_TEXT = PlanetSelectionUtil.createText("oxygen.false");
    public static final Text ITEM_REQUIREMENT_TEXT = PlanetSelectionUtil.createText("item_requirement");

    public static final Text ROCKET_TIER_1_TEXT = new TranslatableText("entity." + BeyondEarth.MOD_ID + ".rocket_t" + 1);
    public static final Text ROCKET_TIER_2_TEXT = new TranslatableText("entity." + BeyondEarth.MOD_ID + ".rocket_t" + 2);
    public static final Text ROCKET_TIER_3_TEXT = new TranslatableText("entity." + BeyondEarth.MOD_ID + ".rocket_t" + 3);
    public static final Text ROCKET_TIER_4_TEXT = new TranslatableText("entity." + BeyondEarth.MOD_ID + ".rocket_t" + 4);

    private static final int BUTTON_START_HEIGHT = 170;

    private final Map<Category, LinkedList<PlanetSelectionButton>> categoryButtons = new HashMap<>();
    PlanetSelectionButton backButton;
    private Category currentCategory = Category.MILKY_WAY_CATEGORY;

    private long guiTime;

    public PlanetSelectionScreen(PlanetSelectionScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.backgroundWidth = 512;
        this.backgroundHeight = 512;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);

        // Catalog text.
        this.textRenderer.draw(matrices, CATALOG_TEXT, 24, (this.height / 2.0f) - 143.0f / 2.0f, -1);
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        // For rotations.
        guiTime++;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Planet selection background.
        PlanetSelectionUtil.addTexture(matrices, 0, 0, this.width, this.height, BACKGROUND_TEXTURE);

        // Render the Solar System when inside the Solar System category.
        if (this.currentCategory.solarSystemType().equals(Category.SOLAR_SYSTEM_TYPE)) {
            PlanetSelectionUtil.addTexture(matrices, (this.width - 185) / 2, (this.height - 185) / 2, 185, 185, SUN_SOLAR_SYSTEM_TEXTURE);
            PlanetSelectionUtil.addTexture(matrices, (this.width - 15) / 2, (this.height - 15) / 2, 15, 15, SUN_TEXTURE);
            // orbit the planets along the sun.
            PlanetSelectionUtil.addRotatingTexture(this, matrices, -21, -21, 10, 10, MERCURY_TEXTURE, 4.15f / 2.0f);
            PlanetSelectionUtil.addRotatingTexture(this, matrices, -37, -37, 10, 10, VENUS_TEXTURE, 1.62f / 2.0f);
            PlanetSelectionUtil.addRotatingTexture(this, matrices, -54, -54, 10, 10, EARTH_TEXTURE, 1.00f / 2.0f);
            PlanetSelectionUtil.addRotatingTexture(this, matrices, -70, -70, 10, 10, MARS_TEXTURE, 0.53f / 2.0f);

        } else if (this.currentCategory.solarSystemType().equals(Category.PROXIMA_CENTAURI_TYPE)) {
            PlanetSelectionUtil.addTexture(matrices, (this.width - 185) / 2, (this.height - 185) / 2, 185, 185, PROXIMA_CENTAURI_SOLAR_SYSTEM_TEXTURE);
            PlanetSelectionUtil.addTexture(matrices, (this.width - 15) / 2, (this.height - 15) / 2, 15, 15, BLUE_SUN_TEXTURE);
            // orbit the planets along the blue sun.
            PlanetSelectionUtil.addRotatingTexture(this, matrices, -21, -21, 10, 10, GLACIO_TEXTURE, 4.15f / 2.0f);

        } else if (this.currentCategory.solarSystemType().equals(Category.MILKY_WAY_TYPE)) {
            // Rotate Milky Way.
            PlanetSelectionUtil.addRotatingTexture(this, matrices, -125, -125, 250, 250, MILKY_WAY_TEXTURE, 0.5f);
        }

        // Display either the small or large menu when a planet category is opened.
        Category parent = this.currentCategory.parent();
        if (parent != null && parent.parent() != null) {
            PlanetSelectionUtil.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 215, 177, LARGE_MENU_TEXTURE);
        } else {
            PlanetSelectionUtil.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 105, 177, SMALL_MENU_LIST);
        }

        // Disable the back button when there is nothing to go back to.
        backButton.visible = parent != null;
        // Disable buttons that are not part of the current category.
        this.categoryButtons.forEach((category, buttons) -> buttons.forEach((button) -> button.visible = category.equals(this.currentCategory)));

        RenderSystem.disableBlend();
    }

    @Override
    protected void init() {
        super.init();

        ClientWorld world = client != null ? client.world : null;
        if (world != null) {
            guiTime = world.getTime();
        }

        // Back button.
        backButton = new PlanetSelectionButton(BUTTON_START_HEIGHT, ButtonType.DARK_BLUE, BACK_TEXT, PlanetSelectionButton.ToolTipType.NONE, 0, button -> {
            if (currentCategory.parent() != null) {
                currentCategory = currentCategory.parent();
            }
        });

        // Milky way.
        this.createButton(ButtonType.BLUE, SOLAR_SYSTEM_SUN_TEXT, Category.MILKY_WAY_CATEGORY, Category.SOLAR_SYSTEM_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 1);
        this.createButton(ButtonType.BLUE, SOLAR_SYSTEM_PROXIMA_CENTAURI_TEXT, Category.MILKY_WAY_CATEGORY, Category.PROXIMA_CENTAURI_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 4);

        // Solar System.
        this.createButton(ButtonType.GREEN, EARTH_TEXT, Category.SOLAR_SYSTEM_CATEGORY, Category.EARTH_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 1);
        this.createButton(ButtonType.GREEN, MARS_TEXT, Category.SOLAR_SYSTEM_CATEGORY, Category.MARS_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 2);
        this.createButton(ButtonType.GREEN, VENUS_TEXT, Category.SOLAR_SYSTEM_CATEGORY, Category.VENUS_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 3);
        this.createButton(ButtonType.GREEN, MERCURY_TEXT, Category.SOLAR_SYSTEM_CATEGORY, Category.MERCURY_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 3);

        // Earth.
        this.createButton(ButtonType.BLUE, EARTH_TEXT, Category.EARTH_CATEGORY, Category.EARTH_CATEGORY, PlanetSelectionButton.ToolTipType.PLANET_STAT, 1);
        this.createButton(ButtonType.BLUE, MOON_TEXT, Category.EARTH_CATEGORY, Category.EARTH_CATEGORY, PlanetSelectionButton.ToolTipType.PLANET_STAT, 1);

        // Proxima Centauri.
        this.createButton(ButtonType.RED, GLACIO_TEXT, Category.PROXIMA_CENTAURI_CATEGORY, Category.GLACIO_CATEGORY, PlanetSelectionButton.ToolTipType.CATEGORY, 4);

        this.addDrawableChild(backButton);
        categoryButtons.forEach((category, buttons) -> buttons.forEach(this::addDrawableChild));
    }

    @Override
    public boolean shouldPause() {
        return true;
    }

    private void createButton(ButtonType type, Text message, Category category, Category child, PlanetSelectionButton.ToolTipType tooltip, int minTier) {
        // Only add the button if the rocket is a high enough tier.
        if (this.handler.getTier() >= minTier) {
            LinkedList<PlanetSelectionButton> buttons = this.categoryButtons.get(category);

            if (buttons == null) {
                buttons = new LinkedList<>();
            }

            int index = buttons.size() + 1;
            int y = category.parent() != null ? BUTTON_START_HEIGHT + 22 * index : (BUTTON_START_HEIGHT + 22 * index) - 22;
            PlanetSelectionButton button = new PlanetSelectionButton(y, type, message, tooltip, minTier, pressed -> this.currentCategory = child);

            buttons.add(button);
            this.categoryButtons.put(category, buttons);
        }
    }

    public long getGuiTime() {
        return guiTime;
    }

    // Colour of the button.
    public enum ButtonType {
        GREEN,
        RED,
        DARK_BLUE,
        BLUE
    }
}
