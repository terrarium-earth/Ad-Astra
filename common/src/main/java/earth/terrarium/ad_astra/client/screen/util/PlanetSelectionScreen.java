package earth.terrarium.ad_astra.client.screen.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.resourcepack.Galaxy;
import earth.terrarium.ad_astra.client.resourcepack.PlanetRing;
import earth.terrarium.ad_astra.client.resourcepack.SolarSystem;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.data.ButtonColor;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.client.CreateSpaceStationPacket;
import earth.terrarium.ad_astra.common.networking.packet.client.TeleportToPlanetPacket;
import earth.terrarium.ad_astra.common.recipe.SpaceStationRecipe;
import earth.terrarium.ad_astra.common.screen.menu.PlanetSelectionMenu;
import earth.terrarium.ad_astra.common.util.MathUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class PlanetSelectionScreen extends Screen implements MenuAccess<PlanetSelectionMenu> {

    public static final ResourceLocation SMALL_MENU_LIST = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/selection_menu.png");
    public static final ResourceLocation LARGE_MENU_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/selection_menu_large.png");
    public static final ResourceLocation SCROLL_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/scroll_bar.png");

    public static final int SCROLL_BAR_X = 92;
    public static final int SCROLL_SENSITIVITY = 5;
    // Text
    public static final Component CATALOG_TEXT = ScreenUtils.createText("catalog");
    public static final Component BACK_TEXT = ScreenUtils.createText("back");
    public static final Component PLANET_TEXT = ScreenUtils.createText("planet");
    public static final Component MOON_TEXT = ScreenUtils.createText("moon");
    public static final Component ORBIT_TEXT = ScreenUtils.createText("orbit");
    public static final Component NO_GRAVITY_TEXT = ScreenUtils.createText("no_gravity");
    public static final Component SPACE_STATION_TEXT = ScreenUtils.createText("space_station");
    public static final Component SOLAR_SYSTEM_TEXT = ScreenUtils.createText("solar_system");
    public static final Component GALAXY_TEXT = ScreenUtils.createText("galaxy");
    public static final Component CATEGORY_TEXT = ScreenUtils.createText("category");
    public static final Component PROVIDED_TEXT = ScreenUtils.createText("provided");
    public static final Component TYPE_TEXT = ScreenUtils.createText("type");
    public static final Component GRAVITY_TEXT = ScreenUtils.createText("gravity");
    public static final Component OXYGEN_TEXT = ScreenUtils.createText("oxygen");
    public static final Component TEMPERATURE_TEXT = ScreenUtils.createText("temperature");
    public static final Component OXYGEN_TRUE_TEXT = ScreenUtils.createText("oxygen.true");
    public static final Component OXYGEN_FALSE_TEXT = ScreenUtils.createText("oxygen.false");
    public static final Component ITEM_REQUIREMENT_TEXT = ScreenUtils.createText("item_requirement");
    public final List<Pair<ItemStack, Integer>> ingredients = new ArrayList<>();
    final Set<Category> solarSystemsCategories = new HashSet<>();
    final Set<Category> galaxyCategories = new HashSet<>();
    private final PlanetSelectionMenu handler;
    private final Map<Category, LinkedList<CustomButton>> categoryButtons = new HashMap<>();
    public int minScrollY = 177;
    public int maxScrollY = 274;
    private Category currentCategory = Category.GALAXY_CATEGORY;
    private float guiTime;
    private Button scrollBar;

    public PlanetSelectionScreen(PlanetSelectionMenu handler, Inventory inventory, Component title) {
        super(title);
        this.handler = handler;

        if (AdAstraClient.galaxies.size() <= 1) {
            currentCategory = Category.MILKY_WAY_CATEGORY;

        }

        // Set the initial gui time to the level time. This creates a random start position for each rotating object.
        guiTime = handler.getPlayer().level.getRandom().nextFloat() * 100000.0f;

        // Get recipe.
        SpaceStationRecipe.getRecipes(handler.getPlayer().level).forEach(recipe -> {
            if (recipe != null) {
                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                    if (recipe.getIngredients().get(i).getItems().length == 0) return;
                    ItemStack stack = recipe.getIngredients().get(i).getItems()[0].copy();
                    // Sets the custom name to the item name to ensure that it always displays the item name and not "Air."
                    stack.setHoverName(stack.getHoverName());
                    stack.setCount(0);

                    for (ItemStack slot : inventory.items) {
                        if (slot != null && !slot.isEmpty()) {
                            if (recipe.getIngredients().get(i).test(slot)) {
                                stack.setCount(inventory.countItem(slot.getItem()));
                            }
                        }
                    }

                    ingredients.add(Pair.of(stack, recipe.getHolders().get(i).count()));
                }
            }
        });
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {

        // For rotations
        this.guiTime += delta;
        this.renderBackground(poseStack, mouseX, mouseY, delta);
        super.render(poseStack, mouseX, mouseY, delta);

        // Catalog text.
        this.font.draw(poseStack, CATALOG_TEXT, 29, (this.height / 2.0f) - 143.0f / 2.0f, -1);
    }

    private void drawBackground(PoseStack poseStack) {
        GuiComponent.fill(poseStack, 0, 0, this.width, this.height, 0xff000419);
        for (int i = 0; i < this.width / 24; i++) {
            ScreenUtils.drawLine(i * 24, this.width + i * 24, 0, this.width, 0xff0f2559);
            ScreenUtils.drawLine(0, this.width - i * 24, i * 24, this.width, 0xff0f2559);
            ScreenUtils.drawLine(i * 24, 0, 0, i * 24, 0xff0f2559);
            ScreenUtils.drawLine(this.width, i * 24, i * 24, this.width, 0xff0f2559);
        }

        RenderSystem.enableBlend();
    }

    private void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        super.renderBackground(poseStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Planet selection background
        drawBackground(poseStack);

        int currentPage = this.getPage();

        SolarSystem solarSystem = null;
        Set<PlanetRing> planetRings = new HashSet<>();
        for (SolarSystem system : AdAstraClient.solarSystems) {
            if (this.currentCategory.id().equals(system.solarSystem()) || this.currentCategory.parent() != null && this.currentCategory.parent().id().equals(system.solarSystem())) {
                solarSystem = system;
                break;
            }
        }

        for (PlanetRing ring : AdAstraClient.planetRings) {
            if (this.currentCategory.id().equals(ring.solarSystem()) || this.currentCategory.parent() != null && this.currentCategory.parent().id().equals(ring.solarSystem())) {
                planetRings.add(ring);
            }
        }

        if (currentPage == 1) {
            AdAstraClient.galaxies.stream().filter(g -> g.galaxy().equals(this.currentCategory.id()))
                    .findFirst()
                    .ifPresent(galaxy -> ScreenUtils.addRotatingTexture(this, poseStack, -125, -125, galaxy.scale(), galaxy.scale(), galaxy.texture(), 0.6f));
        }
        // Render the Solar System when inside the Solar System category
        else {
            if (solarSystem != null) {

                // Sun
                ScreenUtils.addTexture(poseStack, (this.width - solarSystem.sunScale()) / 2, (this.height - solarSystem.sunScale()) / 2, solarSystem.sunScale(), solarSystem.sunScale(), solarSystem.sun());

                for (PlanetRing ring : planetRings) {
                    ScreenUtils.drawCircle(this.width / 2f, this.height / 2f, ring.radius() * 24, 75, solarSystem.ringColour());
                }

                for (PlanetRing ring : planetRings) {
                    int coordinates = (int) (ring.radius() * 17 - (ring.scale() / 1.9));
                    ScreenUtils.addRotatingTexture(this, poseStack, coordinates, coordinates, ring.scale(), ring.scale(), ring.texture(), 365 / (float) ring.speed());
                }
            }
        }

        // Display either the small or large menu when a planet category is opened.
        if (currentPage == 3) {
            ScreenUtils.addTexture(poseStack, 0, (this.height / 2) - 177 / 2, 215, 177, LARGE_MENU_TEXTURE);
            this.scrollBar.x = 210;
        } else {
            ScreenUtils.addTexture(poseStack, 0, (this.height / 2) - 177 / 2, 105, 177, SMALL_MENU_LIST);
            this.scrollBar.x = SCROLL_BAR_X;
        }

        this.categoryButtons.forEach((category, buttons) -> buttons.forEach(button -> button.visible = this.currentCategory.equals(category)));

        // Disable the back button when there is nothing to go back to.
        CustomButton backButton = this.categoryButtons.get(Category.BACK).get(0);
        backButton.visible = this.currentCategory.parent() != null;
        if (currentPage == 1 && AdAstraClient.galaxies.size() <= 1) {
            backButton.visible = false;
        }
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

        // The back button. It is always element [0] in the buttons list.
        LinkedList<CustomButton> backButtonList = new LinkedList<>();
        CustomButton backButton = new CustomButton(10, this.height / 2 - 36, BACK_TEXT, ButtonType.NORMAL, ButtonColor.BLUE, TooltipType.NONE, null, pressed -> onNavigationButtonClick(currentCategory.parent()));
        this.addRenderableWidget(backButton);
        backButtonList.add(backButton);

        this.categoryButtons.put(Category.BACK, backButtonList);

        // All buttons are data-driven; they are created from files in the /planet_data/planets directory.
        List<Planet> planets = new ArrayList<>(PlanetData.planets());
        planets.sort(Comparator.comparing(g -> g.translation().substring(Math.abs(g.translation().indexOf(".text")))));
        planets.forEach(planet -> {

            if (this.handler.getTier() >= planet.rocketTier()) {
                Category galaxyCategory = new Category(planet.galaxy(), Category.GALAXY_CATEGORY);
                Category solarSystemCategory = new Category(planet.solarSystem(), galaxyCategory);
                Category planetCategory = new Category(planet.parentWorld() == null ? planet.level().location() : planet.parentWorld().location(), solarSystemCategory);

                Component label = Component.translatable(planet.translation());

                this.galaxyCategories.add(galaxyCategory);
                this.solarSystemsCategories.add(solarSystemCategory);

                List<String> disabledPlanets = List.of(AdAstraConfig.disabledPlanets.split(","));

                if (planet.parentWorld() == null && !disabledPlanets.contains(planet.level().location().toString())) {
                    createNavigationButton(label, solarSystemCategory, ButtonType.NORMAL, planet.buttonColor(), TooltipType.CATEGORY, planet, planetCategory);
                }

                createTeleportButton(1, label, planetCategory, ButtonType.NORMAL, planet.buttonColor(), TooltipType.PLANET, planet, planet.level());
                createTeleportButton(2, ORBIT_TEXT, planetCategory, ButtonType.SMALL, planet.buttonColor(), TooltipType.ORBIT, null, planet.orbitWorld());
                createSpaceStationTeleportButton(3, SPACE_STATION_TEXT, planetCategory, ButtonType.NORMAL, planet.buttonColor(), planet.orbitWorld());
            }
        });

        this.galaxyCategories.forEach((this::createGalaxyButton));
        this.solarSystemsCategories.forEach((this::createSolarSystemButton));

        // Scroll bar
        this.scrollBar = new Button(SCROLL_BAR_X, minScrollY, 4, 8, Component.nullToEmpty(""), pressed -> {
        }) {
            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                if (this.visible) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, SCROLL_BAR);
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.enableDepthTest();
                    GuiComponent.blit(poseStack, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
                }
            }
        };
        this.addRenderableWidget(this.scrollBar);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    public void onNavigationButtonClick(Category target) {
        this.resetButtonScroll();
        this.scrollBar.y = minScrollY;
        this.currentCategory = target;
    }

    public void createGalaxyButton(Category galaxyCategory) {
        Component label = ScreenUtils.createText(galaxyCategory.id());
        Galaxy galaxy = AdAstraClient.galaxies.stream().filter(g -> g.galaxy().equals(galaxyCategory.id())).findFirst().orElse(null);
        createNavigationButton(label, Category.GALAXY_CATEGORY, ButtonType.LARGE, (galaxy != null ? galaxy.buttonColor() : ButtonColor.PURPLE), TooltipType.GALAXY, null, galaxyCategory);
    }

    public void createSolarSystemButton(Category solarSystemCategory) {
        Component label = ScreenUtils.createText(solarSystemCategory.id());
        SolarSystem solarSystem = AdAstraClient.solarSystems.stream().filter(g -> g.solarSystem().equals(solarSystemCategory.id())).findFirst().orElse(null);
        createNavigationButton(label, solarSystemCategory.parent(), ButtonType.NORMAL, (solarSystem != null ? solarSystem.buttonColor() : ButtonColor.BLUE), TooltipType.SOLAR_SYSTEM, null, solarSystemCategory);
    }

    public void createNavigationButton(Component label, Category category, ButtonType size, ButtonColor colour, TooltipType tooltip, Planet planetInfo, Category target) {
        createButton(label, category, size, colour, tooltip, planetInfo, press -> onNavigationButtonClick(target));
    }

    public void createSpaceStationTeleportButton(int row, Component label, Category category, ButtonType size, ButtonColor colour, ResourceKey<Level> level) {
        createTeleportButton(row, label, category, size, colour, TooltipType.SPACE_STATION, null, level, press -> {
            if (!handler.getPlayer().isCreative() && !handler.getPlayer().isSpectator()) {
                for (Pair<ItemStack, Integer> ingredient : this.ingredients) {
                    boolean isEnough = ingredient.getFirst().getCount() >= ingredient.getSecond();
                    if (!isEnough) {
                        // Don't do anything if the player does not have the necessary materials.
                        return;
                    }
                }
            }
            this.minecraft.player.closeContainer();
            NetworkHandling.CHANNEL.sendToServer(new CreateSpaceStationPacket(level.location()));
            teleportPlayer(level);
        });
    }

    public void createTeleportButton(int row, Component label, Category category, ButtonType size, ButtonColor colour, TooltipType tooltip, Planet planetInfo, ResourceKey<Level> level) {
        createTeleportButton(row, label, category, size, colour, tooltip, planetInfo, level, press -> teleportPlayer(level));
    }

    public void createTeleportButton(int row, Component label, Category category, ButtonType size, ButtonColor colour, TooltipType tooltip, Planet planetInfo, ResourceKey<Level> level, Consumer<Button> onClick) {
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

    public void teleportPlayer(ResourceKey<Level> level) {
        this.minecraft.player.closeContainer();
        // Tell the server to teleport the player after the button has been pressed.
        NetworkHandling.CHANNEL.sendToServer(new TeleportToPlanetPacket(level.location()));
    }

    public CustomButton createButton(Component label, Category category, ButtonType size, ButtonColor colour, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {
        return createButton(10, label, category, size, colour, tooltip, planetInfo, onClick);
    }

    public CustomButton createButton(int row, Component label, Category category, ButtonType size, ButtonColor colour, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {

        int column = getColumn(category);
        return createButton(row, column, label, category, size, colour, tooltip, planetInfo, onClick);
    }

    public CustomButton createButton(int row, int column, Component label, Category category, ButtonType size, ButtonColor colour, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {

        LinkedList<CustomButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

        CustomButton button = new CustomButton(row, column, label, size, colour, tooltip, planetInfo, onClick::accept);
        this.addRenderableWidget(button);

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
                        button2.y += sensitivity;

                        if (referencePoint >= minThreshold) {
                            button2.y -= referencePoint - minThreshold;
                        } else if (referencePoint <= maxThreshold) {
                            button2.y -= referencePoint - maxThreshold;
                        }
                    }

                    float min = maxThreshold / (float) minThreshold;
                    float ratio = backButton.y / (float) minThreshold;
                    ratio = MathUtil.invLerp(ratio, 1, min);

                    // Flip min and max for inverse operation.
                    this.scrollBar.y = (int) Mth.lerp(ratio, maxScrollY, minScrollY);
                    this.scrollBar.y = Mth.clamp(this.scrollBar.y, minScrollY, maxScrollY);
                }

                break;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    public void resetButtonScroll() {
        categoryButtons.values().forEach(list -> list.forEach(button -> {
            button.y = button.getStartY();
        }));
    }

    private int getPage() {
        Category category = this.currentCategory;
        if (category.parent() == null) {
            // Galaxy menu
            return 0;
        } else if (category.parent().parent() == null) {
            // Solar system menu
            return 1;
        } else if (category.parent().parent().parent() == null) {
            // Planet menu
            return 2;
        } else if (category.parent().parent().parent().parent() == null) {
            // Planet menu
            return 3;
        }
        // Should never be called
        AdAstra.LOGGER.warn("Invalid page!");
        return 0;
    }

    public int getColumn(Category category) {
        LinkedList<CustomButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());
        int index = buttons.size() + 1;
        int startY = this.height / 2 - 58;
        // Disable the galaxy category if the Milky Way is the only galaxy
        if (Category.GALAXY_CATEGORY.equals(category.parent()) && AdAstraClient.galaxies.size() <= 1) {
            return startY + 22 * index;
        }
        return startY + 22 * index + (category.parent() != null ? 22 : 0);
    }

    public float getGuiTime() {
        return this.guiTime;
    }

    @Override
    public PlanetSelectionMenu getMenu() {
        return this.handler;
    }

    public List<Pair<ItemStack, Integer>> getIngredients() {
        return this.ingredients;
    }

    // Do not close unless in creative mode
    @Override
    public void onClose() {
        Minecraft client = Minecraft.getInstance();
        if (client.player.isCreative() || client.player.isSpectator()) {
            super.onClose();
        }
    }

    // Reset the buttons when the window size is changed
    @Override
    public void resize(Minecraft client, int width, int height) {
        this.categoryButtons.clear();
        this.resetButtonScroll();
        super.resize(client, width, height);
    }

    public enum TooltipType {
        NONE, GALAXY, SOLAR_SYSTEM, CATEGORY, PLANET, ORBIT, SPACE_STATION
    }
}