package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.client.BeyondEarthClient;
import net.mrscauthd.beyond_earth.data.ButtonColour;
import net.mrscauthd.beyond_earth.data.Planet;
import net.mrscauthd.beyond_earth.data.SolarSystem;
import net.mrscauthd.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import net.mrscauthd.beyond_earth.networking.ModC2SPackets;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class PlanetSelectionScreen extends Screen implements ScreenHandlerProvider<PlanetSelectionScreenHandler> {

        // Textures.
        public static final Identifier BACKGROUND_TEXTURE = new ModIdentifier("textures/screens/planet_selection.png");

        public static final Identifier MILKY_WAY_TEXTURE = new ModIdentifier("textures/sky/gui/milky_way.png");

        public static final Identifier SUN_SOLAR_SYSTEM_TEXTURE = new ModIdentifier("textures/solar_system.png");
        public static final Identifier PROXIMA_CENTAURI_SOLAR_SYSTEM_TEXTURE = new ModIdentifier("textures/proxima_centauri.png");

        public static final Identifier SMALL_MENU_LIST = new ModIdentifier("textures/rocket_menu_list.png");
        public static final Identifier LARGE_MENU_TEXTURE = new ModIdentifier("textures/rocket_menu_list_2.png");

        // Text.
        public static final Text CATALOG_TEXT = PlanetSelectionUtil.createText("catalog");
        public static final Text BACK_TEXT = PlanetSelectionUtil.createText("back");

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

        private final PlanetSelectionScreenHandler handler;

        private final Map<Category, LinkedList<PlanetSelectionButton>> categoryButtons = new HashMap<>();
        private PlanetSelectionButton backButton;

        private Category currentCategory = Category.MILKY_WAY_CATEGORY;

        private float guiTime;

        List<Category> solarSystemsCategories = new LinkedList<>();

        public PlanetSelectionScreen(PlanetSelectionScreenHandler handler, PlayerInventory inventory, Text title) {
                super(title);
                this.handler = handler;
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

                this.renderBackground(matrices, mouseX, mouseY, delta);
                super.render(matrices, mouseX, mouseY, delta);

                // Catalog text.
                this.textRenderer.draw(matrices, CATALOG_TEXT, 24, (this.height / 2.0f) - 143.0f / 2.0f, -1);
        }

        private void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
                super.renderBackground(matrices);

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                // For rotations.
                this.guiTime += delta;

                // // Planet selection background.
                PlanetSelectionUtil.addTexture(matrices, 0, 0, this.width, this.height, BACKGROUND_TEXTURE);

                int currentPage = this.getPage();

                SolarSystem solarSystem = null;
                for (SolarSystem system : BeyondEarthClient.solarSystems) {
                        if (this.currentCategory.id().equals(system.solarSystem())) {
                                solarSystem = system;
                                break;
                        }
                }

                switch (currentPage) {
                case 1 -> {
                        PlanetSelectionUtil.addRotatingTexture(this, matrices, -125, -125, 250, 250, MILKY_WAY_TEXTURE, 0.6f);
                }
                // Render the Solar System when inside the Solar System category.
                case 2, 3 -> {

                        // if (planet != null && solarSystem != null) {
                        if (solarSystem != null) {

                                int rings = solarSystem.planetaryRings().size();

                                // Rings.
                                PlanetSelectionUtil.addTexture(matrices, (this.width - 185) / 2, (this.height - 185) / 2, 185, 185, SUN_SOLAR_SYSTEM_TEXTURE);
                                // Sun.
                                PlanetSelectionUtil.addTexture(matrices, (this.width - 15) / 2, (this.height - 15) / 2, 15, 15, new ModIdentifier("textures/sky/gui/" + solarSystem.sunType().toString().toLowerCase() + ".png"));

                                // Planets.
                                for (int i = 0; i < solarSystem.planetaryRings().size(); i++) {
                                        int days = 1;
                                        int coordinates = -21 - (i * 16);
                                        Identifier texture = solarSystem.planetaryRings().get(i);
                                        for (Planet currentPlanet : BeyondEarthClient.planets) {
                                                if (texture.getPath().equals(currentPlanet.name())) {
                                                        days = currentPlanet.daysInYear();
                                                        break;
                                                }
                                        }
                                        PlanetSelectionUtil.addRotatingTexture(this, matrices, coordinates, coordinates, 10, 10, new ModIdentifier("textures/sky/gui/" + texture.getPath() + ".png"), 365 / (float)days);
                                }
                        }
                }
                }

                // Display either the small or large menu when a planet category is opened.
                if (currentPage == 3) {
                        PlanetSelectionUtil.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 215, 177, LARGE_MENU_TEXTURE);
                } else {
                        PlanetSelectionUtil.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 105, 177, SMALL_MENU_LIST);
                }

                // Disable the back button when there is nothing to go back to.
                this.backButton.visible = this.currentCategory.parent() != null;

                // Disable buttons that are not part of the current category.
                this.categoryButtons.forEach((category, buttons) -> buttons.forEach((button) -> button.visible = currentCategory.equals(category)));

                RenderSystem.disableBlend();
        }

        @Override
        protected void init() {
                super.init();

                // Set the initial gui time to the world time. This creates a random start position for each
                // rotating object.
                guiTime = client.world.getTime();

                // The back button. It is always element [0] in the buttons list.
                this.backButton = new PlanetSelectionButton(10, this.height / 2 - 33, BACK_TEXT, ButtonSize.NORMAL, ButtonColour.DARK_BLUE, pressed -> {
                        if (currentCategory.parent() != null) {
                                currentCategory = currentCategory.parent();
                        }
                });
                this.addDrawableChild(backButton);

                List<Planet> planets = BeyondEarthClient.planets;
                planets.forEach(planet -> {
                        if (this.handler.getTier() >= planet.rocketTier()) {
                                Category galaxyCategory = new Category(planet.galaxy(), null);
                                Category solarSystemCategory = new Category(planet.solarSystem(), galaxyCategory);
                                Category planetCategory = new Category(planet.parentDimension() == null ? planet.dimension().getValue() : planet.parentDimension().getValue(), solarSystemCategory);

                                String name = planet.name();
                                Text label = PlanetSelectionUtil.createText(name);

                                if (!solarSystemsCategories.contains(solarSystemCategory)) {
                                        solarSystemsCategories.add(solarSystemCategory);
                                }

                                if (planet.parentDimension() == null) {
                                        createNavigationButton(label, solarSystemCategory, ButtonSize.NORMAL, planet.buttonColour(), planetCategory);
                                }

                                createTeleportButton(1, label, planetCategory, ButtonSize.NORMAL, ButtonColour.BLUE, planet.dimension().getValue());
                                createTeleportButton(2, ORBIT_TEXT, planetCategory, ButtonSize.SMALL, ButtonColour.BLUE, planet.orbitDimension().getValue());
                                createSpaceStationTeleportButton(3, SPACE_STATION_TEXT, planetCategory, ButtonSize.NORMAL, ButtonColour.GREEN, planet.orbitDimension().getValue());
                        }
                });

                this.solarSystemsCategories.forEach((solarSystemCategory -> createSolarSystemButton(solarSystemCategory)));
        }

        @Override
        public boolean shouldPause() {
                return true;
        }

        private void createSolarSystemButton(Category solarSystemCategory) {
                String name = solarSystemCategory.id().getPath();
                Text label = PlanetSelectionUtil.createText(name);
                Category category = new Category(solarSystemCategory.parent().id(), null);
                createNavigationButton(label, category, ButtonSize.NORMAL, ButtonColour.BLUE, solarSystemCategory);
        }

        private void createNavigationButton(Text label, Category category, ButtonSize size, ButtonColour colour, Category target) {
                createButton(label, category, size, colour, press -> {
                        this.currentCategory = target;
                });
        }

        private void createSpaceStationTeleportButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, Identifier targetDimension) {
                // TODO
                createTeleportButton(row, label, category, size, colour, targetDimension);
        }

        private void createTeleportButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, Identifier targetDimension) {
                int newRow = 0;
                if (row == 2) {
                        newRow = 76;
                } else if (row == 3) {
                        newRow = 118;
                }

                LinkedList<PlanetSelectionButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

                int column = getColumn(category) - (row - 1) * 22;
                column -= 44 * (buttons.size() / 3);
                createButton(newRow + 10, column, label, category, size, colour, press -> {
                        this.client.player.closeHandledScreen();
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeIdentifier(targetDimension);
                        // Tell the server to teleport the player after the button has been pressed.
                        ClientPlayNetworking.send(ModC2SPackets.TELEPORT_TO_PLANET_PACKET_ID, buf);
                });
        }

        private PlanetSelectionButton createButton(Text label, Category category, ButtonSize size, ButtonColour colour, Consumer<ButtonWidget> onClick) {
                return createButton(10, label, category, size, colour, onClick);
        }

        private PlanetSelectionButton createButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, Consumer<ButtonWidget> onClick) {

                int column = getColumn(category);
                return createButton(row, column, label, category, size, colour, onClick);
        }

        private PlanetSelectionButton createButton(int row, int column, Text label, Category category, ButtonSize size, ButtonColour colour, Consumer<ButtonWidget> onClick) {

                LinkedList<PlanetSelectionButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

                PlanetSelectionButton button = new PlanetSelectionButton(row, column, label, size, colour, pressed -> onClick.accept(pressed));
                this.addDrawableChild(button);

                buttons.add(button);
                categoryButtons.put(category, buttons);
                return button;
        }

        private int getPage() {
                Category category = this.currentCategory;
                if (category.parent() == null) {
                        // Galaxy screen.
                        return 1;
                } else if (category.parent().parent() == null) {
                        // Solar system screen.
                        return 2;
                } else if (category.parent().parent().parent() == null) {
                        // Planet screen.
                        return 3;
                }
                // Should never be called.
                BeyondEarth.LOGGER.warn("Invalid page!");
                return 0;
        }

        private int getColumn(Category category) {
                LinkedList<PlanetSelectionButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());
                int index = buttons.size() + 1;
                int startY = this.height / 2 - 55;
                return startY + 22 * index + (category.parent() != null ? 22 : 0);
        }

        public int getActiveButtons() {
                int activeButtons = 0;
                for (List<PlanetSelectionButton> buttons : this.categoryButtons.values()) {
                        for (PlanetSelectionButton button : buttons)
                                if (button.active) {
                                        activeButtons++;
                                }
                }
                return activeButtons;
        }

        public float getGuiTime() {
                return this.guiTime;
        }

        @Override
        public PlanetSelectionScreenHandler getScreenHandler() {
                return this.handler;
        }
}