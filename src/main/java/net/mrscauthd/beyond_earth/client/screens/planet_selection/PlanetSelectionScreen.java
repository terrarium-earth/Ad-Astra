package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import net.mrscauthd.beyond_earth.networking.ModC2SPackets;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.util.ModUtils;
import net.mrscauthd.beyond_earth.util.PlanetFacts;

@Environment(EnvType.CLIENT)
public class PlanetSelectionScreen extends HandledScreen<PlanetSelectionScreenHandler> {

        public static final Identifier BACKGROUND_TEXTURE = new ModIdentifier("textures/screens/planet_selection.png");

        public static final Identifier MILKY_WAY_TEXTURE = new ModIdentifier("textures/sky/gui/milky_way.png");

        public static final Identifier SUN_SOLAR_SYSTEM_TEXTURE = new ModIdentifier("textures/solar_system.png");
        public static final Identifier PROXIMA_CENTAURI_SOLAR_SYSTEM_TEXTURE = new ModIdentifier(
                        "textures/proxima_centauri.png");

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
        public static final Text SOLAR_SYSTEM_PROXIMA_CENTAURI_TEXT = PlanetSelectionUtil
                        .createText("solar_system_proxima_centauri");

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

        public static final Text ROCKET_TIER_1_TEXT = new TranslatableText(
                        "entity." + BeyondEarth.MOD_ID + ".rocket_t" + 1);
        public static final Text ROCKET_TIER_2_TEXT = new TranslatableText(
                        "entity." + BeyondEarth.MOD_ID + ".rocket_t" + 2);
        public static final Text ROCKET_TIER_3_TEXT = new TranslatableText(
                        "entity." + BeyondEarth.MOD_ID + ".rocket_t" + 3);
        public static final Text ROCKET_TIER_4_TEXT = new TranslatableText(
                        "entity." + BeyondEarth.MOD_ID + ".rocket_t" + 4);

        private final Map<Category, LinkedList<PlanetSelectionButton>> categoryButtons = new HashMap<>();
        PlanetSelectionButton backButton;
        private Category currentCategory = Category.MILKY_WAY_CATEGORY;

        private float guiTime;

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
                this.guiTime += delta;

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                // Planet selection background.
                PlanetSelectionUtil.addTexture(matrices, 0, 0, this.width, this.height, BACKGROUND_TEXTURE);

                // Render the Solar System when inside the Solar System category.
                if (this.currentCategory.solarSystemType().equals(Category.SOLAR_SYSTEM_TYPE)) {
                        PlanetSelectionUtil.addTexture(matrices, (this.width - 185) / 2, (this.height - 185) / 2, 185,
                                        185,
                                        SUN_SOLAR_SYSTEM_TEXTURE);
                        PlanetSelectionUtil.addTexture(matrices, (this.width - 15) / 2, (this.height - 15) / 2, 15, 15,
                                        SUN_TEXTURE);
                        // orbit the planets along the sun.
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -21, -21, 10, 10, MERCURY_TEXTURE,
                                        PlanetFacts.EARTH_CYCLE / PlanetFacts.MERCURY_CYCLE);
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -37, -37, 10, 10, VENUS_TEXTURE,
                                        PlanetFacts.EARTH_CYCLE / PlanetFacts.VENUS_CYCLE);
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -54, -54, 10, 10, EARTH_TEXTURE,
                                        PlanetFacts.EARTH_CYCLE / PlanetFacts.EARTH_CYCLE);
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -70, -70, 10, 10, MARS_TEXTURE,
                                        PlanetFacts.EARTH_CYCLE / PlanetFacts.MARS_CYCLE);

                } else if (this.currentCategory.solarSystemType().equals(Category.PROXIMA_CENTAURI_TYPE)) {
                        PlanetSelectionUtil.addTexture(matrices, (this.width - 185) / 2, (this.height - 185) / 2, 185,
                                        185,
                                        PROXIMA_CENTAURI_SOLAR_SYSTEM_TEXTURE);
                        PlanetSelectionUtil.addTexture(matrices, (this.width - 15) / 2, (this.height - 15) / 2, 15, 15,
                                        BLUE_SUN_TEXTURE);
                        // orbit the planets along the blue sun.
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -21, -21, 10, 10, GLACIO_TEXTURE,
                                        PlanetFacts.EARTH_CYCLE / PlanetFacts.GLACIO_CYCLE);

                } else if (this.currentCategory.solarSystemType().equals(Category.MILKY_WAY_TYPE)) {
                        // Rotate Milky Way.
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -125, -125, 250, 250, MILKY_WAY_TEXTURE,
                                        0.6f);
                }

                // Display either the small or large menu when a planet category is opened.
                Category parent = this.currentCategory.parent();
                if (parent != null && parent.parent() != null) {
                        PlanetSelectionUtil.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 215, 177,
                                        LARGE_MENU_TEXTURE);
                } else {
                        PlanetSelectionUtil.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 105, 177,
                                        SMALL_MENU_LIST);
                }

                // Disable the back button when there is nothing to go back to.
                backButton.visible = parent != null;
                // Disable buttons that are not part of the current category.
                this.categoryButtons.forEach((category, buttons) -> buttons
                                .forEach((button) -> button.visible = category.equals(this.currentCategory)));

                RenderSystem.disableBlend();
        }

        @Override
        protected void init() {
                super.init();

                // Set the initial gui time to the world time. This creates a random start
                // position for each rotating object.
                guiTime = client.world.getTime();

                // Back button.
                backButton = new PlanetSelectionButton(this.height / 2 - 33, ButtonColour.DARK_BLUE, BACK_TEXT,
                                PlanetSelectionButton.TooltipType.NONE, 0, ButtonSize.NORMAL, null, button -> {
                                        if (currentCategory.parent() != null) {
                                                currentCategory = currentCategory.parent();
                                        }
                                });

                // Milky way.
                this.createButton(ButtonColour.BLUE, SOLAR_SYSTEM_SUN_TEXT, Category.MILKY_WAY_CATEGORY,
                                Category.SOLAR_SYSTEM_CATEGORY, PlanetSelectionButton.TooltipType.CATEGORY, 1,
                                ButtonSize.NORMAL);
                this.createButton(ButtonColour.BLUE, SOLAR_SYSTEM_PROXIMA_CENTAURI_TEXT, Category.MILKY_WAY_CATEGORY,
                                Category.PROXIMA_CENTAURI_CATEGORY, PlanetSelectionButton.TooltipType.CATEGORY, 4,
                                ButtonSize.NORMAL);

                // Solar System.
                this.createButton(ButtonColour.GREEN, EARTH_TEXT, Category.SOLAR_SYSTEM_CATEGORY,
                                Category.EARTH_CATEGORY,
                                PlanetSelectionButton.TooltipType.CATEGORY, 1, ButtonSize.NORMAL);
                this.createButton(ButtonColour.GREEN, MARS_TEXT, Category.SOLAR_SYSTEM_CATEGORY, Category.MARS_CATEGORY,
                                PlanetSelectionButton.TooltipType.CATEGORY, 2, ButtonSize.NORMAL);
                this.createButton(ButtonColour.GREEN, VENUS_TEXT, Category.SOLAR_SYSTEM_CATEGORY,
                                Category.VENUS_CATEGORY,
                                PlanetSelectionButton.TooltipType.CATEGORY, 3, ButtonSize.NORMAL);
                this.createButton(ButtonColour.GREEN, MERCURY_TEXT, Category.SOLAR_SYSTEM_CATEGORY,
                                Category.MERCURY_CATEGORY,
                                PlanetSelectionButton.TooltipType.CATEGORY, 3, ButtonSize.NORMAL);

                // Proxima Centauri.
                this.createButton(ButtonColour.RED, GLACIO_TEXT, Category.PROXIMA_CENTAURI_CATEGORY,
                                Category.GLACIO_CATEGORY,
                                PlanetSelectionButton.TooltipType.CATEGORY, 4, ButtonSize.NORMAL);

                // Earth.
                this.createOrbitButton(ButtonColour.BLUE, EARTH_TEXT, Category.EARTH_CATEGORY,
                                PlanetSelectionButton.TooltipType.PLANET_STAT, 1, ButtonSize.NORMAL,
                                World.OVERWORLD.getValue());
                this.createOrbitButton(ButtonColour.BLUE, MOON_TEXT, Category.EARTH_CATEGORY,
                                PlanetSelectionButton.TooltipType.PLANET_STAT, 1, ButtonSize.NORMAL, ModUtils.MOON);

                // Mars.
                this.createOrbitButton(ButtonColour.BLUE, MARS_TEXT, Category.MARS_CATEGORY,
                                PlanetSelectionButton.TooltipType.PLANET_STAT, 2, ButtonSize.NORMAL, ModUtils.MARS);

                // Venus.
                this.createOrbitButton(ButtonColour.BLUE, VENUS_TEXT, Category.VENUS_CATEGORY,
                                PlanetSelectionButton.TooltipType.PLANET_STAT, 2, ButtonSize.NORMAL, ModUtils.VENUS);

                // Mercury.
                this.createOrbitButton(ButtonColour.BLUE, MERCURY_TEXT, Category.MERCURY_CATEGORY,
                                PlanetSelectionButton.TooltipType.PLANET_STAT, 3, ButtonSize.NORMAL, ModUtils.MERCURY);

                // Glacio.
                this.createOrbitButton(ButtonColour.BLUE, GLACIO_TEXT, Category.GLACIO_CATEGORY,
                                PlanetSelectionButton.TooltipType.PLANET_STAT, 4, ButtonSize.NORMAL, ModUtils.GLACIO);

                // Add all category buttons.
                this.addDrawableChild(backButton);
                categoryButtons.forEach((category, buttons) -> buttons.forEach(this::addDrawableChild));
        }

        @Override
        public boolean shouldPause() {
                return true;
        }

        // Navigation button.
        private void createButton(ButtonColour type, Text message, Category category, Category child,
                        PlanetSelectionButton.TooltipType tooltip, int minTier, ButtonSize size) {
                createButton(type, message, category, tooltip, minTier, size, null,
                                pressed -> this.currentCategory = child);
        }

        // Teleports the player to a dimension.
        private void createOrbitButton(ButtonColour type, Text message, Category category,
                        PlanetSelectionButton.TooltipType tooltip, int minTier, ButtonSize size,
                        Identifier targetDimension) {
                createButton(type, message, category, tooltip, minTier, size,
                                RegistryKey.of(Registry.WORLD_KEY, targetDimension),
                                pressed -> {
                                        this.client.player.closeHandledScreen();
                                        PacketByteBuf buf = PacketByteBufs.create();
                                        buf.writeIdentifier(targetDimension);
                                        // Tell the server to teleport the player after the button has been pressed.
                                        ClientPlayNetworking.send(ModC2SPackets.TELEPORT_TO_PLANET_PACKET_ID, buf);
                                });

        }

        private void createButton(ButtonColour type, Text message, Category category,
                        PlanetSelectionButton.TooltipType tooltip, int minTier, ButtonSize size,
                        RegistryKey<World> world,
                        Consumer<ButtonWidget> consumer) {
                // Only add the button if the rocket is a high enough tier.
                if (this.handler.getTier() >= minTier) {
                        LinkedList<PlanetSelectionButton> buttons = this.categoryButtons.get(category);

                        if (buttons == null) {
                                buttons = new LinkedList<>();
                        }

                        int index = buttons.size() + 1;
                        int startY = this.height / 2 - 55;
                        int y = startY + 22 * index + (category.parent() != null ? 22 : 0);

                        PlanetSelectionButton button = new PlanetSelectionButton(y, type, message, tooltip, minTier,
                                        size, world,
                                        pressed -> consumer.accept(pressed));

                        buttons.add(button);
                        this.categoryButtons.put(category, buttons);
                }
        }

        public float getGuiTime() {
                return this.guiTime;
        }

        // Colour of the button.
        public enum ButtonColour {
                GREEN(74.0f, 156.0f, 64.0f, 255.0f),
                RED(166.0f, 47.0f, 48.0f, 255.0f),
                DARK_BLUE(51.0f, 94.0f, 189.0f, 255.0f),
                BLUE(75.0f, 158.0f, 217.0f, 255.0f);

                private ModUtils.ColourHolder colour;

                private ButtonColour(float r, float g, float b, float a) {
                        this.colour = new ModUtils.ColourHolder(r / 255.0f + 0.1f, g / 255.0f + 0.1f, b / 255.0f + 0.1f,
                                        a / 255.0f + 0.1f);
                }

                public ModUtils.ColourHolder getColour() {
                        return this.colour;
                }
        }

        public enum ButtonSize {
                LARGE(75, 20),
                NORMAL(71, 20),
                SMALL(37, 20);

                private int width;
                private int height;

                private ButtonSize(int width, int height) {
                        this.width = width;
                        this.height = height;
                }

                public int getWidth() {
                        return this.width;
                }

                public int getHeight() {
                        return this.height;
                }
        }
}