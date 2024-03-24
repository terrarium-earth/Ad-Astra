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
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.components.LabeledImageButton;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.packets.ServerboundLandOnSpaceStationPacket;
import earth.terrarium.adastra.common.network.packets.ServerboundLandPacket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlanetsScreen extends AbstractContainerScreen<PlanetsMenu> {
    public static final ResourceLocation SELECTION_MENU = new ResourceLocation(AdAstra.MOD_ID, "planets/selection_menu");
    public static final ResourceLocation SMALL_SELECTION_MENU = new ResourceLocation(AdAstra.MOD_ID, "planets/small_selection_menu");

    public static final WidgetSprites BUTTON_SPRITES = new WidgetSprites(
        new ResourceLocation(AdAstra.MOD_ID, "planets/button"),
        new ResourceLocation(AdAstra.MOD_ID, "planets/button_highlighted")
    );

    public static final WidgetSprites BACK_BUTTON_SPRITES = new WidgetSprites(
        new ResourceLocation(AdAstra.MOD_ID, "planets/back_button"),
        new ResourceLocation(AdAstra.MOD_ID, "planets/back_button_highlighted")
    );

    public static final WidgetSprites PLUS_BUTTON_SPRITES = new WidgetSprites(
        new ResourceLocation(AdAstra.MOD_ID, "planets/plus_button"),
        new ResourceLocation(AdAstra.MOD_ID, "planets/plus_button_highlighted")
    );

    private final List<Button> buttons = new ArrayList<>();
    private Button backButton;
    private double scrollAmount;

    private final List<Button> spaceStationButtons = new ArrayList<>();
    private Button addSpaceStatonButton;
    private double spaceStationScrollAmount;

    private final boolean hasMultipleSolarSystems;
    private int pageIndex;
    @Nullable
    private ResourceLocation selectedSolarSystem = PlanetConstants.SOLAR_SYSTEM;

    @Nullable
    private Planet selectedPlanet;

    public PlanetsScreen(PlanetsMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = width;
        this.imageHeight = height;

        var planets = AdAstraData.planets().values().stream()
            .filter(planet -> !menu.disabledPlanets().contains(planet.dimension().location()))
            .filter(planet -> menu.tier() >= planet.tier()).toList();
        hasMultipleSolarSystems = planets.stream().map(Planet::solarSystem).distinct().count() > 1;
        pageIndex = hasMultipleSolarSystems ? 0 : 1;
    }

    @Override
    protected void init() {
        super.init();
        buttons.clear();
        spaceStationButtons.clear();
        spaceStationScrollAmount = 0;

        switch (pageIndex) {
            case 0 -> createSolarSystemButtons();
            case 1, 2 -> {
                createPlanetButtons();
                if (pageIndex == 2 && selectedPlanet != null) {
                    createSelectedPlanetButtons();
                }
            }
        }

        backButton = addRenderableWidget(new LabeledImageButton(10, height / 2 - 85, 12, 12, BACK_BUTTON_SPRITES, b -> {
            pageIndex--;
            rebuildWidgets();
        }));

        addSpaceStatonButton = addRenderableWidget(new LabeledImageButton(114, height / 2 - 41, 12, 12, PLUS_BUTTON_SPRITES, b -> {
            if (selectedPlanet != null) {
                int ownedSpaceStationCount = menu.getOwnedAndTeamSpaceStations(selectedPlanet.orbitIfPresent()).size();
                Component name = Component.translatable("text.ad_astra.text.space_station_name", ownedSpaceStationCount + 1);
                menu.constructSpaceStation(selectedPlanet.dimension(), name);
                close();
            }
        }));
        if (selectedPlanet != null) {
            addSpaceStatonButton.setTooltip(getSpaceStationRecipeTooltip(selectedPlanet.orbitIfPresent()));
            addSpaceStatonButton.active = selectedPlanet != null && menu.canConstruct(selectedPlanet.orbitIfPresent()) && !menu.isInSpaceStation(selectedPlanet.orbitIfPresent());
        }


        backButton.visible = pageIndex > (hasMultipleSolarSystems ? 0 : 1);
        addSpaceStatonButton.visible = pageIndex == 2 && selectedPlanet != null;
    }

    private void createSolarSystemButtons() {
        selectedSolarSystem = null;
        AdAstraData.solarSystems().forEach(solarSystem -> {
            var button = addWidget(new LabeledImageButton(10, 0, 99, 20, BUTTON_SPRITES, b -> {
                pageIndex = 1;
                selectedSolarSystem = solarSystem;
                rebuildWidgets();
            }, Component.translatableWithFallback("solar_system.%s.%s".formatted(solarSystem.getNamespace(), solarSystem.getPath()), title(solarSystem.getPath()))));
            buttons.add(button);
        });
    }

    private void createPlanetButtons() {
        for (var planet : menu.getSortedPlanets()) {
            if (planet.isSpace()) continue;
            if (menu.tier() < planet.tier()) continue;
            if (!planet.solarSystem().equals(selectedSolarSystem)) continue;
            buttons.add(addWidget(new LabeledImageButton(10, 0, 99, 20, BUTTON_SPRITES, b -> {
                pageIndex = 2;
                selectedPlanet = planet;
                rebuildWidgets();
            }, menu.getPlanetName(planet.dimension()))));
        }
    }

    private void createSelectedPlanetButtons() {
        if (selectedPlanet == null) return;
        var pos = menu.getLandingPos(selectedPlanet.dimension(), true);
        var button = addRenderableWidget(new LabeledImageButton(
            114, height / 2 - 77, 99, 20, BUTTON_SPRITES,
            b -> land(selectedPlanet.dimension()), ConstantComponents.LAND));
        button.setTooltip(Tooltip.create(Component.translatable("tooltip.ad_astra.land",
            menu.getPlanetName(selectedPlanet.dimension()), pos.getX(), pos.getZ()).withStyle(ChatFormatting.AQUA)));

        addSpaceStationButtons(selectedPlanet.orbitIfPresent());
    }

    private void addSpaceStationButtons(ResourceKey<Level> dimension) {
        menu.getOwnedAndTeamSpaceStations(dimension).forEach(station -> {
            var pos = station.getSecond().position();
            var button = addWidget(new LabeledImageButton(114, height / 2, 99, 20, BUTTON_SPRITES, b ->
                landOnSpaceStation(dimension, pos), station.getSecond().name()));
            button.setTooltip(getSpaceStationLandTooltip(dimension, pos, station.getFirst()));
            spaceStationButtons.add(button);
        });
    }

    public Tooltip getSpaceStationLandTooltip(ResourceKey<Level> dimension, ChunkPos pos, String owner) {
        return Tooltip.create(CommonComponents.joinLines(
            Component.translatable("tooltip.ad_astra.space_station_land", menu.getPlanetName(dimension), pos.getMiddleBlockX(), pos.getMiddleBlockZ()).withStyle(ChatFormatting.AQUA),
            Component.translatable("tooltip.ad_astra.space_station_owner", owner).withStyle(ChatFormatting.GOLD)
        ));
    }

    public Tooltip getSpaceStationRecipeTooltip(ResourceKey<Level> planet) {
        List<Component> tooltip = new ArrayList<>();
        var pos = menu.getLandingPos(planet, false);
        tooltip.add(Component.translatable("tooltip.ad_astra.construct_space_station_at", menu.getPlanetName(planet), pos.getX(), pos.getZ()).withStyle(ChatFormatting.AQUA));

        if (menu.isInSpaceStation(planet) || menu.isClaimed(planet)) {
            tooltip.add(ConstantComponents.SPACE_STATION_ALREADY_EXISTS);
            return Tooltip.create(CommonComponents.joinLines(tooltip));
        } else {
            tooltip.add(ConstantComponents.CONSTRUCTION_COST.copy().withStyle(ChatFormatting.AQUA));
        }

        List<Pair<ItemStack, Integer>> ingredients = menu.ingredients().get(planet);
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

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        renderButtons(graphics, mouseX, mouseY, partialTick);
        backButton.visible = pageIndex > (hasMultipleSolarSystems ? 0 : 1);
        addSpaceStatonButton.visible = pageIndex == 2 && selectedPlanet != null;
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

        if (pageIndex == 2 && selectedPlanet != null) {
            int spaceStationScrollPixels = (int) spaceStationScrollAmount;

            try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), graphics.pose(), 112, height / 2 - 2, 112, 90)) {
                for (var button : spaceStationButtons) {
                    button.render(graphics, mouseX, mouseY, partialTick);
                }

                for (int i = 0; i < spaceStationButtons.size(); i++) {
                    var button = spaceStationButtons.get(i);
                    button.setY((i * 24 - spaceStationScrollPixels) + (height / 2));
                }
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {}

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.fill(0, 0, width, height, 0xff000419);

        // Render diamond pattern lines
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
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

        if (PlanetConstants.PROXIMA_CENTAURI.equals(selectedSolarSystem)) {
            drawCircles(1, 1, 0xff008080, bufferBuilder);
        } else if (PlanetConstants.SOLAR_SYSTEM.equals(selectedSolarSystem)) {
            drawCircles(0, 4, 0xff24327b, bufferBuilder);
        }

        tessellator.end();

        if (PlanetConstants.PROXIMA_CENTAURI.equals(selectedSolarSystem)) {
            renderProximaCentauri(graphics);
        } else if (PlanetConstants.SOLAR_SYSTEM.equals(selectedSolarSystem)) {
            renderSolarSystem(graphics);
        }
        renderSelectionMenu(graphics);
    }

    protected void renderSelectionMenu(GuiGraphics graphics) {
        if (pageIndex == 2) {
            graphics.blitSprite(SELECTION_MENU, 7, height / 2 - 88, 209, 177);
            graphics.drawCenteredString(font, ConstantComponents.SPACE_STATION, 163, height / 2 - 15, 0xffffff);
        } else {
            graphics.blitSprite(SMALL_SELECTION_MENU, 7, height / 2 - 88, 105, 177);
        }

        if (pageIndex == 2 && selectedPlanet != null) {
            var title = Component.translatableWithFallback("planet.%s.%s".formatted(selectedPlanet.dimension().location().getNamespace(), selectedPlanet.dimension().location().getPath()), title(selectedPlanet.dimension().location().getPath()));
            graphics.drawCenteredString(font, title, 57, height / 2 - 60, 0xffffff);
        } else if (pageIndex == 1 && selectedSolarSystem != null) {
            var title = Component.translatableWithFallback("solar_system.%s.%s".formatted(selectedSolarSystem.getNamespace(), selectedSolarSystem.getPath()), title(selectedSolarSystem.getPath()));
            graphics.drawCenteredString(font, title, 57, height / 2 - 60, 0xffffff);
        } else {
            graphics.drawCenteredString(font, ConstantComponents.CATALOG, 57, height / 2 - 60, 0xffffff);
        }
    }

    public void renderSolarSystem(GuiGraphics graphics) {
        graphics.blit(DimensionRenderingUtils.SUN, width / 2 - 8, height / 2 - 8, 0, 0, 16, 16, 16, 16);
        float yRot = Util.getMillis() / 100f;
        for (int i = 1; i < 5; i++) {
            graphics.pose().pushPose();
            graphics.pose().translate(width / 2f, height / 2f, 0);
            graphics.pose().mulPose(Axis.ZP.rotationDegrees(yRot * (5 - i) / 2));
            graphics.pose().translate(31 * i - 10, 0, 0);
            graphics.blit(DimensionRenderingUtils.SOLAR_SYSTEM_TEXTURES.get(i - 1), 0, 0, 0, 0, 12, 12, 12, 12);
            graphics.pose().popPose();
        }
    }

    public void renderProximaCentauri(GuiGraphics graphics) {
        graphics.blit(DimensionRenderingUtils.BLUE_SUN, width / 2 - 8, height / 2 - 8, 0, 0, 16, 16, 16, 16);
        float yRot = Util.getMillis() / 100f % 360f;
        graphics.pose().pushPose();
        graphics.pose().translate(width / 2f, height / 2f, 0);
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(yRot));
        graphics.pose().translate(53, 0, 0);
        graphics.blit(DimensionRenderingUtils.GLACIO, 0, 0, 0, 0, 12, 12, 12, 12);
        graphics.pose().popPose();
    }

    public void drawCircles(int start, int count, int color, BufferBuilder bufferBuilder) {
        for (int i = 1 + start; i < count + start + 1; i++) {
            drawCircle(bufferBuilder, width / 2f, height / 2f, 30 * i, 75, color);
        }
    }

    public static void drawCircle(BufferBuilder bufferBuilder, double x, double y, double radius, int sides, int color) {
        for (double r = radius - 0.5; r <= radius + 0.5; r += 0.1) {
            for (int i = 0; i < sides; i++) {
                double angle = i * 2.0 * Math.PI / sides;
                double nextAngle = (i + 1) * 2.0 * Math.PI / sides;
                double x1 = x + r * Math.cos(angle);
                double y1 = y + r * Math.sin(angle);
                double x2 = x + r * Math.cos(nextAngle);
                double y2 = y + r * Math.sin(nextAngle);

                bufferBuilder.vertex(x1, y1, 0).color(color).endVertex();
                bufferBuilder.vertex(x2, y2, 0).color(color).endVertex();
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX < 112 && mouseX > 6 && mouseY > height / 2f - 43 && mouseY < height / 2f + 88) {
            setScrollAmount(scrollAmount - scrollY * 16 / 2f);
        } else if (mouseX > 112 && mouseX < 224 && mouseY > height / 2f - 2 && mouseY < height / 2f + 88) {
            setSpaceStationScrollAmount(spaceStationScrollAmount - scrollY * 16 / 2f);
        }
        return true;
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
        else if (!(player.getVehicle() instanceof Rocket)) super.onClose();
    }

    protected void close() {
        pageIndex = 0;
        onClose();
    }

    protected void setScrollAmount(double amount) {
        scrollAmount = Mth.clamp(amount, 0.0, Math.max(0, buttons.size() * 24 - 131));
    }

    protected void setSpaceStationScrollAmount(double amount) {
        spaceStationScrollAmount = Mth.clamp(amount, 0.0, Math.max(0, spaceStationButtons.size() * 24 - 90));
    }

    public void land(ResourceKey<Level> dimension) {
        NetworkHandler.CHANNEL.sendToServer(new ServerboundLandPacket(dimension, true));
        close();
    }

    public void landOnSpaceStation(ResourceKey<Level> dimension, ChunkPos pos) {
        NetworkHandler.CHANNEL.sendToServer(new ServerboundLandOnSpaceStationPacket(dimension, pos));
        close();
    }

    // StringUtils only replaces the first word so WordUtils is needed
    @SuppressWarnings("deprecation")
    private String title(String string) {
        return WordUtils.capitalizeFully(string.replace("_", " "));
    }
}
