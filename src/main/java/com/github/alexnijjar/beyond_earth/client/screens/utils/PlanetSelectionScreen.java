package com.github.alexnijjar.beyond_earth.client.screens.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.client.BeyondEarthClient;
import com.github.alexnijjar.beyond_earth.client.resource_pack.SolarSystem;
import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import com.github.alexnijjar.beyond_earth.networking.ModC2SPackets;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.util.MathUtil;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class PlanetSelectionScreen extends Screen implements ScreenHandlerProvider<PlanetSelectionScreenHandler> {

        // Textures.
        public static final Identifier BACKGROUND_TEXTURE = new ModIdentifier("textures/screens/planet_selection.png");
        public static final Identifier MILKY_WAY_TEXTURE = new ModIdentifier("textures/sky/gui/milky_way.png");

        public static final Identifier SMALL_MENU_LIST = new ModIdentifier("textures/selection_menu.png");
        public static final Identifier LARGE_MENU_TEXTURE = new ModIdentifier("textures/selection_menu_large.png");
        public static final Identifier SCROLL_BAR = new ModIdentifier("textures/scroll_bar.png");

        public static final int SCROLL_BAR_X = 92;
        public int minScrollY = 177;
        public int maxScrollY = 274;
        public static final int SCROLL_SENSITIVITY = 5;

        // Text.
        public static final Text CATALOG_TEXT = ScreenUtils.createText("catalog");
        public static final Text BACK_TEXT = ScreenUtils.createText("back");

        public static final Text PLANET_TEXT = ScreenUtils.createText("planet");
        public static final Text MOON_TEXT = ScreenUtils.createText("moon");

        public static final Text ORBIT_TEXT = ScreenUtils.createText("orbit");

        public static final Text NO_GRAVITY_TEXT = ScreenUtils.createText("no_gravity");

        public static final Text SPACE_STATION_TEXT = ScreenUtils.createText("space_station");

        public static final Text SOLAR_SYSTEM_TEXT = ScreenUtils.createText("solar_system");
        public static final Text CATEGORY_TEXT = ScreenUtils.createText("category");
        public static final Text PROVIDED_TEXT = ScreenUtils.createText("provided");
        public static final Text TYPE_TEXT = ScreenUtils.createText("type");
        public static final Text GRAVITY_TEXT = ScreenUtils.createText("gravity");
        public static final Text OXYGEN_TEXT = ScreenUtils.createText("oxygen");
        public static final Text TEMPERATURE_TEXT = ScreenUtils.createText("temperature");
        public static final Text OXYGEN_TRUE_TEXT = ScreenUtils.createText("oxygen.true");
        public static final Text OXYGEN_FALSE_TEXT = ScreenUtils.createText("oxygen.false");
        public static final Text ITEM_REQUIREMENT_TEXT = ScreenUtils.createText("item_requirement");

        private final PlanetSelectionScreenHandler handler;

        private final Map<Category, LinkedList<CustomButton>> categoryButtons = new HashMap<>();
        private Category currentCategory = Category.MILKY_WAY_CATEGORY;

        private float guiTime;

        private ButtonWidget scrollBar;

        List<Category> solarSystemsCategories = new LinkedList<>();

        public List<Pair<ItemStack, Integer>> ingredients = new ArrayList<>();

        public PlanetSelectionScreen(PlanetSelectionScreenHandler handler, PlayerInventory inventory, Text title) {
                super(title);
                this.handler = handler;

                // Get recipe.
                ModRecipes.SPACE_STATION_RECIPE.getRecipes(handler.getPlayer().world).forEach(recipe -> {
                        if (recipe != null) {

                                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                                        ItemStack stack = recipe.getIngredients().get(i).getMatchingStacks()[0].copy();
                                        // Sets the custom name to the item name to ensure that it always displays the item name and not "Air."
                                        stack.setCustomName(stack.getName());
                                        stack.setCount(0);

                                        for (ItemStack slot : inventory.main) {
                                                if (slot != null) {
                                                        if (recipe.getIngredients().get(i).test(slot)) {
                                                                stack.setCount(inventory.count(slot.getItem()));
                                                                break;
                                                        }
                                                }
                                        }

                                        ingredients.add(Pair.of(stack, recipe.getStackCounts().get(i)));
                                }
                        }
                });
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
                ScreenUtils.addTexture(matrices, 0, 0, this.width, this.height, BACKGROUND_TEXTURE);

                int currentPage = this.getPage();

                SolarSystem solarSystem = null;
                for (SolarSystem system : BeyondEarthClient.solarSystems) {
                        if (this.currentCategory.id().equals(system.solarSystem()) || this.currentCategory.parent() != null && this.currentCategory.parent().id().equals(system.solarSystem())) {
                                solarSystem = system;
                                break;
                        }
                }

                switch (currentPage) {
                case 1 -> {
                        ScreenUtils.addRotatingTexture(this, matrices, -125, -125, 250, 250, MILKY_WAY_TEXTURE, 0.6f);
                }
                // Render the Solar System when inside the Solar System category.
                case 2, 3 -> {

                        if (solarSystem != null) {

                                // Sun.
                                ScreenUtils.addTexture(matrices, (this.width - 15) / 2, (this.height - 15) / 2, 15, 15, new ModIdentifier("textures/sky/gui/" + solarSystem.sunType().toString().toLowerCase() + ".png"));

                                // Planets.
                                for (int i = 0; i < solarSystem.planetaryRings().size(); i++) {

                                        // Rings.
                                        double radius = solarSystem.planetaryRings().get(i).getRight();
                                        ScreenUtils.drawCircle(this.width / 2, this.height / 2, radius * 24, 75);

                                        int days = 1;
                                        int coordinates = (int) (radius * 17 - 5);
                                        Identifier texture = solarSystem.planetaryRings().get(i).getLeft();
                                        for (Planet currentPlanet : BeyondEarthClient.planets) {
                                                if (texture.getPath().equals(currentPlanet.name())) {
                                                        days = currentPlanet.daysInYear();
                                                        break;
                                                }
                                        }
                                        ScreenUtils.addRotatingTexture(this, matrices, coordinates, coordinates, 10, 10, new ModIdentifier("textures/sky/gui/" + texture.getPath() + ".png"), 365 / (float) days);
                                }
                        }
                }
                }

                // Display either the small or large menu when a planet category is opened.
                if (currentPage == 3) {
                        ScreenUtils.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 215, 177, LARGE_MENU_TEXTURE);
                        this.scrollBar.x = 210;
                } else {
                        ScreenUtils.addTexture(matrices, 0, (this.height / 2) - 177 / 2, 105, 177, SMALL_MENU_LIST);
                        this.scrollBar.x = SCROLL_BAR_X;
                }

                this.categoryButtons.forEach((category, buttons) -> buttons.forEach(button -> button.visible = this.currentCategory.equals(category)));

                // Disable the back button when there is nothing to go back to.
                CustomButton backButton = this.categoryButtons.get(Category.BACK).get(0);
                backButton.visible = this.currentCategory.parent() != null;
                if (this.categoryButtons.containsKey(this.currentCategory)) {
                        this.scrollBar.visible = this.categoryButtons.get(this.currentCategory).size() > (currentPage == 3 ? 13 : 5);
                }

                minScrollY = (this.height / 2) - 33;
                maxScrollY = (this.height / 2) + 64;

                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableScissor();
        }

        @Override
        protected void init() {
                super.init();

                // Set the initial gui time to the world time. This creates a random start position for each rotating object.
                guiTime = client.world.getTime();

                // The back button. It is always element [0] in the buttons list.
                LinkedList<CustomButton> backButtonList = new LinkedList<>();
                CustomButton backButton = new CustomButton(10, this.height / 2 - 36, BACK_TEXT, ButtonSize.NORMAL, ButtonColour.DARK_BLUE, TooltipType.NONE, null, pressed -> onNavigationButtonClick(currentCategory.parent()));
                this.addDrawableChild(backButton);
                backButtonList.add(backButton);

                this.categoryButtons.put(Category.BACK, backButtonList);

                // All buttons are data-driven; they are created from files in the /planet_data/planets directory.
                List<Planet> planets = BeyondEarthClient.planets;
                planets.sort(Comparator.comparing(s -> s.name()));
                planets.forEach(planet -> {
                        if (this.handler.getTier() >= planet.rocketTier()) {
                                Category galaxyCategory = new Category(planet.galaxy(), null);
                                Category solarSystemCategory = new Category(planet.solarSystem(), galaxyCategory);
                                Category planetCategory = new Category(planet.parentWorld() == null ? planet.world().getValue() : planet.parentWorld().getValue(), solarSystemCategory);

                                String name = planet.name();
                                Text label = ScreenUtils.createText(name);

                                if (!solarSystemsCategories.contains(solarSystemCategory)) {
                                        solarSystemsCategories.add(solarSystemCategory);
                                }

                                if (planet.parentWorld() == null) {
                                        createNavigationButton(label, solarSystemCategory, ButtonSize.NORMAL, planet.buttonColour(), TooltipType.CATEGORY, planet, planetCategory);
                                }

                                createTeleportButton(1, label, planetCategory, ButtonSize.NORMAL, ButtonColour.BLUE, TooltipType.PLANET, planet, planet.world());
                                createTeleportButton(2, ORBIT_TEXT, planetCategory, ButtonSize.SMALL, ButtonColour.BLUE, TooltipType.ORBIT, null, planet.orbitWorld());
                                createSpaceStationTeleportButton(3, SPACE_STATION_TEXT, planetCategory, ButtonSize.NORMAL, ButtonColour.GREEN, planet.orbitWorld());
                        }
                });

                this.solarSystemsCategories.forEach((solarSystemCategory -> createSolarSystemButton(solarSystemCategory)));

                // Scroll bar.
                this.scrollBar = new ButtonWidget(SCROLL_BAR_X, minScrollY, 4, 8, Text.of(""), pressed -> {
                }) {
                        @Override
                        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
                                if (this.visible) {
                                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                                        RenderSystem.setShaderTexture(0, SCROLL_BAR);
                                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                                        RenderSystem.enableBlend();
                                        RenderSystem.defaultBlendFunc();
                                        RenderSystem.enableDepthTest();
                                        DrawableHelper.drawTexture(matrices, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
                                }
                        }
                };
                this.addDrawableChild(this.scrollBar);
        }

        @Override
        public boolean shouldPause() {
                return true;
        }

        private void onNavigationButtonClick(Category target) {
                this.resetButtonScroll();
                this.scrollBar.y = minScrollY;
                this.currentCategory = target;
        }

        private void createSolarSystemButton(Category solarSystemCategory) {
                String name = solarSystemCategory.id().getPath();
                Text label = ScreenUtils.createText(name);
                Category category = new Category(solarSystemCategory.parent().id(), null);
                createNavigationButton(label, category, ButtonSize.NORMAL, ButtonColour.BLUE, TooltipType.SOLAR_SYSTEM, null, solarSystemCategory);
        }

        private void createNavigationButton(Text label, Category category, ButtonSize size, ButtonColour colour, TooltipType tooltip, Planet planetInfo, Category target) {
                createButton(label, category, size, colour, tooltip, planetInfo, press -> onNavigationButtonClick(target));
        }

        private void createSpaceStationTeleportButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, RegistryKey<World> world) {
                createTeleportButton(row, label, category, size, colour, TooltipType.SPACE_STATION, null, world, press -> {
                        for (int i = 0; i < this.ingredients.size(); i++) {
                                Pair<ItemStack, Integer> ingredient = this.ingredients.get(i);
                                boolean isEnough = ingredient.first.getCount() >= ingredient.second;
                                if (!isEnough) {
                                        // Don't do anything if the player does not have the necessary materials.
                                        return;
                                }
                        }
                        this.client.player.closeHandledScreen();
                        // Consume the required items to build the Space Station.
                        ClientPlayNetworking.send(ModC2SPackets.DELETE_SPACE_STATION_ITEMS, PacketByteBufs.empty());
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeIdentifier(world.getValue());
                        // Create the space station.
                        ClientPlayNetworking.send(ModC2SPackets.CREATE_SPACE_STATION, buf);
                        teleportPlayer(world);
                });
        }

        private void createTeleportButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, TooltipType tooltip, Planet planetInfo, RegistryKey<World> world) {
                createTeleportButton(row, label, category, size, colour, tooltip, planetInfo, world, press -> {
                        teleportPlayer(world);
                });
        }

        private void createTeleportButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, TooltipType tooltip, Planet planetInfo, RegistryKey<World> world, Consumer<ButtonWidget> onClick) {
                int newRow = 0;
                if (row == 2) {
                        newRow = 76;
                } else if (row == 3) {
                        newRow = 118;
                }

                LinkedList<CustomButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

                int column = getColumn(category) - (row - 1) * 22;
                column -= 44 * (buttons.size() / 3);
                createButton(newRow + 10, column, label, category, size, colour, tooltip, planetInfo, onClick);
        }

        private void teleportPlayer(RegistryKey<World> world) {
                this.client.player.closeHandledScreen();
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeIdentifier(world.getValue());
                // Tell the server to teleport the player after the button has been pressed.
                ClientPlayNetworking.send(ModC2SPackets.TELEPORT_TO_PLANET, buf);
        }

        private CustomButton createButton(Text label, Category category, ButtonSize size, ButtonColour colour, TooltipType tooltip, Planet planetInfo, Consumer<ButtonWidget> onClick) {
                return createButton(10, label, category, size, colour, tooltip, planetInfo, onClick);
        }

        private CustomButton createButton(int row, Text label, Category category, ButtonSize size, ButtonColour colour, TooltipType tooltip, Planet planetInfo, Consumer<ButtonWidget> onClick) {

                int column = getColumn(category);
                return createButton(row, column, label, category, size, colour, tooltip, planetInfo, onClick);
        }

        private CustomButton createButton(int row, int column, Text label, Category category, ButtonSize size, ButtonColour colour, TooltipType tooltip, Planet planetInfo, Consumer<ButtonWidget> onClick) {

                LinkedList<CustomButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

                CustomButton button = new CustomButton(row, column, label, size, colour, tooltip, planetInfo, pressed -> onClick.accept(pressed));
                this.addDrawableChild(button);

                buttons.add(button);
                categoryButtons.put(category, buttons);
                return button;
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
                // Simple scrollbar.
                for (Map.Entry<Category, LinkedList<CustomButton>> entry : this.categoryButtons.entrySet()) {
                        if (this.currentCategory.equals(entry.getKey())) {

                                List<CustomButton> buttons = new LinkedList<>();
                                CustomButton backButton = this.categoryButtons.get(Category.BACK).get(0);
                                // Create a new list with the back button included. We have to do this as the back button is a separate category, so it needs
                                // to manually be added to this category so that it scrolls with every other button.
                                buttons.add(backButton);
                                buttons.addAll(entry.getValue());

                                boolean isLargePage = this.getPage() == 3;
                                // The amount of buttons that are offscreen.
                                // The large page had 3 buttons per row so each row needs to be treated as one.
                                final int overflowButtons = buttons.size() - (isLargePage ? 13 : 5);

                                // Don't scroll if there are not enough buttons.
                                if (overflowButtons > 0) {
                                        final int referencePoint = backButton.y;
                                        int minThreshold = this.height / 2 - 35;
                                        int maxThreshold = (this.height / 2 - 38) - (overflowButtons * (isLargePage ? 7 : 21));
                                        int sensitivity = (int) (SCROLL_SENSITIVITY * amount);

                                        // Lock the scroll to the min and max thresholds.
                                        if (amount > 0) {
                                                if (referencePoint >= minThreshold) {
                                                        sensitivity = 0;
                                                }
                                        } else if (amount < 0) {
                                                if (referencePoint <= maxThreshold) {
                                                        sensitivity = 0;
                                                }
                                        }

                                        // Move all buttons based on the scroll.
                                        for (CustomButton button2 : buttons) {
                                                CustomButton button = button2;
                                                button.y += sensitivity;

                                                if (referencePoint >= minThreshold) {
                                                        button.y -= referencePoint - minThreshold;
                                                } else if (referencePoint <= maxThreshold) {
                                                        button.y -= referencePoint - maxThreshold;
                                                }
                                        }

                                        float min = maxThreshold / (float) minThreshold;
                                        float ratio = backButton.y / (float) minThreshold;
                                        ratio = MathUtil.invLerp(ratio, 1, min);

                                        // Flip min and max for inverse operation.
                                        this.scrollBar.y = (int) MathHelper.lerp(ratio, maxScrollY, minScrollY);
                                }

                                break;
                        }
                }

                return super.mouseScrolled(mouseX, mouseY, amount);
        }

        private void resetButtonScroll() {
                categoryButtons.values().forEach(list -> {
                        list.forEach(button -> {
                                button.y = button.getStartY();
                        });
                });

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
                LinkedList<CustomButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());
                int index = buttons.size() + 1;
                int startY = this.height / 2 - 58;
                return startY + 22 * index + (category.parent() != null ? 22 : 0);
        }

        public float getGuiTime() {
                return this.guiTime;
        }

        public enum TooltipType {
                NONE, SOLAR_SYSTEM, CATEGORY, PLANET, ORBIT, SPACE_STATION
        }

        @Override
        public PlanetSelectionScreenHandler getScreenHandler() {
                return this.handler;
        }

        public List<Pair<ItemStack, Integer>> getIngredients() {
                return this.ingredients;
        }

        // Do not close.
        @Override
        public void close() {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player.isCreative()) {
                        super.close();
                }
        }

        @Override
        public void resize(MinecraftClient client, int width, int height) {
                super.resize(client, width, height);
                // this.resetButtonScroll();
        }
}