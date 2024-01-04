package earth.terrarium.adastra.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.LabeledImageButton;
import earth.terrarium.adastra.client.utils.DimensionUtils;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundConstructSpaceStationPacket;
import earth.terrarium.adastra.common.network.messages.ServerboundLandPacket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlanetsScreen extends AbstractContainerScreen<PlanetsMenu> {
    public static final ResourceLocation BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/button.png");
    public static final ResourceLocation BACK_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/back_button.png");
    public static final ResourceLocation PLUS_BUTTON = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/plus_button.png");
    public static final ResourceLocation SELECTION_MENU = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/selection_menu.png");
    public static final ResourceLocation SMALL_SELECTION_MENU = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/planets/small_selection_menu.png");

    private static final Map<ResourceLocation, Map<ChunkPos, UUID>> SPACE_STATIONS = new HashMap<>();
    private final Map<ResourceLocation, List<Pair<ItemStack, Integer>>> ingredientsForSpaceStations;

    private final List<Button> buttons = new ArrayList<>();
    private Button backButton;

    private double scrollAmount;
    private final boolean hasMultipleSolarSystems;

    private int pageIndex;
    private ResourceLocation selectedSolarSystem = PlanetConstants.SOLAR_SYSTEM;
    @Nullable
    private Planet selectedPlanet;

    private static final List<ResourceLocation> SOLAR_SYSTEM_TEXTURES = List.of(
        DimensionUtils.MERCURY,
        DimensionUtils.VENUS,
        DimensionUtils.EARTH,
        DimensionUtils.MARS
    );

    public PlanetsScreen(PlanetsMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = width;
        this.imageHeight = height;

        var planets = AdAstraData.planets().values().stream().filter(planet -> menu.tier() >= planet.tier()).toList();
        hasMultipleSolarSystems = planets.stream().map(Planet::solarSystem).distinct().count() > 1;

        pageIndex = hasMultipleSolarSystems ? 0 : 1;
        ingredientsForSpaceStations = menu.getSpaceStationRecipes();
    }

    public static void setSpaceStations(Map<ResourceLocation, Map<ChunkPos, UUID>> spaceStations) {
        PlanetsScreen.SPACE_STATIONS.clear();
        PlanetsScreen.SPACE_STATIONS.putAll(spaceStations);
    }

    @Override
    protected void init() {
        super.init();
        buttons.clear();
        switch (pageIndex) {
            case 0 -> AdAstraData.solarSystems().forEach(solarSystem -> {
                var button = new LabeledImageButton(10, 0, 99, 20, 0, 0, 20, BUTTON, 99, 40, b -> {
                    pageIndex = 1;
                    selectedSolarSystem = solarSystem;
                    rebuildWidgets();
                }, Component.translatable("solar_system.%s.%s".formatted(solarSystem.getNamespace(), solarSystem.getPath())));
                buttons.add(button);
                addWidget(button);
            });
            case 1, 2 -> {
                var planets = new ArrayList<>(AdAstraData.planets().values());
                planets.sort(Comparator.comparing(o -> {
                    var dimension = o.dimension().location();
                    if (dimension.equals(Level.OVERWORLD.location())) {
                        dimension = new ResourceLocation(AdAstra.MOD_ID, "earth");
                    }
                    return dimension.getPath();
                }));
                for (var planet : planets) {
                    if (planet.isSpace()) continue;
                    if (menu.tier() < planet.tier()) continue;
                    if (!planet.solarSystem().equals(selectedSolarSystem)) continue;
                    Component title = Component.translatable("planet.%s.%s".formatted(planet.dimension().location().getNamespace(), planet.dimension().location().getPath()));
                    var button = new LabeledImageButton(10, 0, 99, 20, 0, 0, 20, BUTTON, 99, 40, b -> {
                        pageIndex = 2;
                        selectedPlanet = planet;
                        rebuildWidgets();
                    }, title);
                    buttons.add(button);
                    addWidget(button);
                }

                if (pageIndex == 2 && selectedPlanet != null) {
                    var pos = menu.player().chunkPosition();
                    var blockpos = menu.player().blockPosition();

                    if (isOwnedSpaceStationAt(selectedPlanet, pos)) {
                        var button = addRenderableWidget(new LabeledImageButton(114, height / 2 + 7, 99, 20, 0, 0, 20, BUTTON, 99, 40, b ->
                            visitSpaceStation(selectedPlanet, pos), ConstantComponents.LAND));
                        button.setTooltip(Tooltip.create(Component.translatable("tooltip.ad_astra.space_station_land", getOrbitName(selectedPlanet), blockpos.getX(), blockpos.getZ()).withStyle(ChatFormatting.AQUA)));
                    } else {
                        var button = addRenderableWidget(new LabeledImageButton(114, height / 2 + 7, 99, 20, 0, 0, 20, BUTTON, 99, 40, b ->
                            constructSpaceStation(selectedPlanet, pos), ConstantComponents.CONSTRUCT));
                        button.setTooltip(getSpaceStationRecipeTooltip(selectedPlanet, pos, blockpos));
                        button.active = canConstructSpaceStation(selectedPlanet) && !isSpaceStationAt(selectedPlanet, pos);
                    }
                    var button = addRenderableWidget(new LabeledImageButton(114, height / 2 - 41, 99, 20, 0, 0, 20, BUTTON, 99, 40, b ->
                        land(selectedPlanet), ConstantComponents.LAND));
                    button.setTooltip(Tooltip.create(Component.translatable("tooltip.ad_astra.land", getPlanetName(selectedPlanet), blockpos.getX(), blockpos.getZ()).withStyle(ChatFormatting.AQUA)));
                }
            }
        }

        backButton = addRenderableWidget(new LabeledImageButton(10, height / 2 - 85, 12, 12, 0, 12, 12, BACK_BUTTON, 12, 24, b -> {
            pageIndex--;
            rebuildWidgets();
        }));
        backButton.visible = pageIndex > (hasMultipleSolarSystems ? 0 : 1);
    }

    public Tooltip getSpaceStationRecipeTooltip(Planet planet, ChunkPos chunkPos, BlockPos pos) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.translatable("tooltip.ad_astra.construct_space_station_at", getOrbitName(planet), pos.getX(), pos.getZ()).withStyle(ChatFormatting.AQUA));

        if (isSpaceStationAt(planet, chunkPos)) {
            tooltip.add(ConstantComponents.SPACE_STATION_ALREADY_EXISTS);
            return Tooltip.create(CommonComponents.joinLines(tooltip));
        } else {
            tooltip.add(ConstantComponents.CONSTRUCTION_COST.copy().withStyle(ChatFormatting.AQUA));
        }

        List<Pair<ItemStack, Integer>> ingredients = ingredientsForSpaceStations.get(planet.orbitIfPresent().location());
        if (ingredients == null) return Tooltip.create(CommonComponents.joinLines(tooltip));
        for (var ingredient : ingredients) {
            var stack = ingredient.getFirst();
            int amountOwned = ingredient.getSecond();
            boolean hasEnough = menu.player().isCreative() || menu.player().isSpectator() || amountOwned >= stack.getCount();
            tooltip.add(Component.translatable("tooltip.ad_astra.requirement", amountOwned, stack.getCount(), stack.getHoverName()
                    .copy().withStyle(ChatFormatting.DARK_AQUA))
                .copy().withStyle(hasEnough ? ChatFormatting.GREEN : ChatFormatting.RED));
        }

        return Tooltip.create(CommonComponents.joinLines(tooltip));
    }

    public Component getPlanetName(Planet planet) {
        return Component.translatable("planet.%s.%s".formatted(planet.dimension().location().getNamespace(), planet.dimension().location().getPath()));
    }

    public Component getOrbitName(Planet planet) {
        return planet.orbit().map(key ->
            Component.translatable("planet.%s.%s".formatted(
                key.location().getNamespace(),
                key.location().getPath()))).orElseGet(Component::empty);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        if (selectedSolarSystem.equals(PlanetConstants.PROXIMA_CENTAURI)) {
            renderProximaCentauri(graphics);
        } else {
            renderSolarSystem(graphics);
        }
        super.render(graphics, mouseX, mouseY, partialTick);

        renderButtons(graphics, mouseX, mouseY, partialTick);
        backButton.visible = pageIndex > (hasMultipleSolarSystems ? 0 : 1);
    }

    private void renderButtons(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int scrollPixels = (int) scrollAmount;

        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), 0, height / 2 - 43, 112, 131)) {
            for (var button : buttons) {
                button.render(graphics, mouseX, mouseY, partialTick);
            }

            for (int i = 0; i < buttons.size(); i++) {
                var button = buttons.get(i);
                button.setY((i * 24 - scrollPixels) + (height / 2 - 41));
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        if (pageIndex == 2) {
            graphics.blit(SELECTION_MENU, 7, height / 2 - 88, 0, 0, 209, 177, 209, 177);
            graphics.drawCenteredString(font, ConstantComponents.SPACE_STATION, 163, height / 2 - 7, 0xffffff);
        } else {
            graphics.blit(SMALL_SELECTION_MENU, 7, height / 2 - 88, 0, 0, 105, 177, 105, 177);
        }

        if (pageIndex == 2 && selectedPlanet != null) {
            var title = Component.translatable("planet.%s.%s".formatted(selectedPlanet.dimension().location().getNamespace(), selectedPlanet.dimension().location().getPath()));
            graphics.drawCenteredString(font, title, 57, height / 2 - 60, 0xffffff);
        } else if (pageIndex == 1) {
            var title = Component.translatable("solar_system.%s.%s".formatted(selectedSolarSystem.getNamespace(), selectedSolarSystem.getPath()));
            graphics.drawCenteredString(font, title, 57, height / 2 - 60, 0xffffff);
        } else {
            graphics.drawCenteredString(font, ConstantComponents.CATALOG, 57, height / 2 - 60, 0xffffff);
        }
    }

    @Override
    public void renderBackground(GuiGraphics graphics) {
        graphics.fill(0, 0, width, height, 0xff000419);

        // Render diamond pattern lines
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        for (int i = -height; i <= width; i += 24) {
            bufferBuilder.vertex(i, 0, 0).color(0xff0f2559).endVertex();
            bufferBuilder.vertex(i + height, height, 0).color(0xff0f2559).endVertex();
        }

        for (int i = width + height; i >= 0; i -= 24) {
            bufferBuilder.vertex(i, 0, 0).color(0xff0f2559).endVertex();
            bufferBuilder.vertex(i - height, height, 0).color(0xff0f2559).endVertex();
        }

        tessellator.end();
    }

    public void renderSolarSystem(GuiGraphics graphics) {
        graphics.blit(DimensionUtils.SUN, width / 2 - 8, height / 2 - 8, 0, 0, 16, 16, 16, 16);
        float yRot = Util.getMillis() / 100f;
        for (int i = 1; i < 5; i++) {
            drawCircle(width / 2f, height / 2f, 30 * i, 75, 0xff24327b);

            graphics.pose().pushPose();
            graphics.pose().translate(width / 2f, height / 2f, 0);
            graphics.pose().mulPose(Axis.ZP.rotationDegrees(yRot * (5 - i) / 2));
            graphics.pose().translate(29 * i - 10, 0, 0);
            graphics.blit(SOLAR_SYSTEM_TEXTURES.get(i - 1), 0, 0, 0, 0, 12, 12, 12, 12);
            graphics.pose().popPose();
        }
    }

    public void renderProximaCentauri(GuiGraphics graphics) {
        graphics.blit(DimensionUtils.BLUE_SUN, width / 2 - 8, height / 2 - 8, 0, 0, 16, 16, 16, 16);
        float yRot = Util.getMillis() / 100f % 360f;
        drawCircle(width / 2f, height / 2f, 60, 75, 0xff008080);

        graphics.pose().pushPose();
        graphics.pose().translate(width / 2f, height / 2f, 0);
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(yRot));
        graphics.pose().translate(50, 0, 0);
        graphics.blit(DimensionUtils.GLACIO, 0, 0, 0, 0, 12, 12, 12, 12);
        graphics.pose().popPose();
    }

    public static void drawCircle(double x, double y, double radius, int sides, int color) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        double scale = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 400.0;
        radius *= scale;

        double width = radius - 0.6;
        for (double i = width; i < radius - 0.5 + 1; i += 0.1) {
            bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
            for (int j = 0; j <= sides; j++) {
                double angle = (Math.PI * 2 * j / sides) + Math.toRadians(180);
                bufferBuilder.vertex(x + Math.sin(angle) * i, y + Math.cos(angle) * i, 0)
                    .color(color)
                    .endVertex();
            }
            tessellator.end();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        setScrollAmount(scrollAmount - delta * 16 / 2f);
        return true;
    }

    protected void setScrollAmount(double amount) {
        scrollAmount = Mth.clamp(amount, 0.0, Math.max(0, buttons.size() * 24 - 131));
    }

    public boolean isSpaceStationAt(Planet planet, ChunkPos pos) {
        var location = planet.orbitIfPresent().location();
        if (SPACE_STATIONS.containsKey(location)) {
            var stations = SPACE_STATIONS.get(location);
            return stations.containsKey(pos);
        }
        return false;
    }

    public boolean isOwnedSpaceStationAt(Planet planet, ChunkPos pos) {
        var location = planet.orbitIfPresent().location();
        if (SPACE_STATIONS.containsKey(location)) {
            var stations = SPACE_STATIONS.get(location);
            return menu.player().getUUID().equals(stations.get(pos));
        }
        return false;
    }

    public boolean canConstructSpaceStation(Planet planet) {
        if (menu.player().isCreative() || menu.player().isSpectator()) return true;
        List<Pair<ItemStack, Integer>> ingredients = ingredientsForSpaceStations.get(planet.orbitIfPresent().location());
        if (ingredients == null) return false;
        for (var ingredient : ingredients) {
            var stack = ingredient.getFirst();
            int amountOwned = ingredient.getSecond();
            if (amountOwned < stack.getCount()) return false;
        }
        return true;
    }

    public void constructSpaceStation(Planet planet, ChunkPos pos) {
        if (!canConstructSpaceStation(planet)) return;
        NetworkHandler.CHANNEL.sendToServer(new ServerboundConstructSpaceStationPacket(planet.dimension().location(), pos));
        visitSpaceStation(planet, pos);
    }

    public void visitSpaceStation(Planet planet, ChunkPos pos) {
        var orbit = planet.orbitIfPresent();
        NetworkHandler.CHANNEL.sendToServer(new ServerboundLandPacket(orbit.location(), pos.getMiddleBlockPosition(100), false));
        pageIndex = 0;
        onClose();
    }

    public void land(Planet planet) {
        NetworkHandler.CHANNEL.sendToServer(new ServerboundLandPacket(planet.dimensionLocation(), menu.player().blockPosition(), true));
        pageIndex = 0;
        onClose();
    }

    @Override
    public void onClose() {
        if (pageIndex > 0) {
            pageIndex--;
            rebuildWidgets();
            return;
        }
        var player = menu.player();
        if (player.isCreative() || player.isSpectator()) super.onClose();
        if (!(player.getVehicle() instanceof Rocket)) super.onClose();
    }
}
