package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;

public class OverlayConfigScreen extends Screen {

    private final Screen parent;

    private List<Overlay> overlays;

    private double clickedX;
    private double clickedY;
    private double pivotX;
    private double pivotY;
    private boolean dragging;
    private Overlay selectdOverlay;

    public OverlayConfigScreen(Screen parent) {
        super(Component.empty());
        this.parent = parent;

        this.overlays = new ArrayList<>();
        this.overlays.add(new OxygenTankOverlay());
        this.overlays.add(new EnergyBarOverlay());
    }

    @Override
    protected void init() {
        super.init();

        for (Overlay overlay : this.overlays) {
            overlay.load(this.minecraft);
        }

        int buttonCount = 3;
        int buttonWidth = 80;
        int buttonHeight = 20;
        int buttonMargin = 5;

        int totalWidth = buttonCount * buttonWidth + (buttonCount - 1) * buttonMargin;
        int buttonOffset = buttonWidth + buttonMargin;
        int x = (this.width - totalWidth) / 2;
        int y = this.height - 27;

        addRenderableWidget(new Button(x + buttonOffset * 0, y, buttonWidth, buttonHeight, Component.translatable("gui.ad_astra.text.save"), (button) -> {
            for (Overlay overlay : this.overlays) {
                overlay.save(this.minecraft);
            }
            AdAstra.CONFIGURATOR.saveConfig(AdAstraConfig.class);
            this.onClose();
        }));
        addRenderableWidget(new Button(x + buttonOffset * 1, y, buttonWidth, buttonHeight, Component.translatable("gui.ad_astra.text.reset"), (button) -> {
            for (Overlay overlay : this.overlays) {
                overlay.reset(this.minecraft);
            }
        }));
        addRenderableWidget(new Button(x + buttonOffset * 2, y, buttonWidth, buttonHeight, Component.translatable("gui.ad_astra.text.back"), (button) -> {
            this.onClose();
        }));
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.getParent());

        for (Overlay overlay : this.overlays) {
            overlay.revert(this.minecraft);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {

        this.renderBackground(poseStack);
        this.drag(mouseX, mouseY);

        for (Overlay overlay : this.overlays) {
            overlay.apply(this.minecraft);
            overlay.render(poseStack, this.minecraft);
        }

        super.render(poseStack, mouseX, mouseY, delta);

        int x = 10;
        int y = 10;
        GuiComponent.drawString(poseStack, this.font, Component.translatable("gui.ad_astra.text.overlay_config1"), x, y, 0xFFFFFF);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("gui.ad_astra.text.overlay_config2"), x, y + this.font.lineHeight, 0xFFFFFF);
    }

    private void drag(int mouseX, int mouseY) {
        Overlay overlay = this.selectdOverlay;

        if (overlay == null) {
            return;
        }

        if (!this.dragging) {
            int dragThreashold = 3;
            double movementX = mouseX - this.clickedX;
            double movementY = mouseY - this.clickedY;
            this.dragging = Math.abs(movementX) >= dragThreashold || Math.abs(movementY) >= dragThreashold;
        }

        if (!this.dragging) {
            return;
        }

        float scale = overlay.getScale();
        double unscaledMouseX = mouseX / scale;
        double unscaledMouseY = mouseY / scale;
        Rect2i renderRect = overlay.getUnscaledRenderRect(this.minecraft);
        Vec2 prevPosition = overlay.getUnscaledPosition();

        double offsetX = (renderRect.getX() - prevPosition.x) + Mth.lerp(this.pivotX, 0.0D, renderRect.getWidth());
        double offsetY = (renderRect.getY() - prevPosition.y) + Mth.lerp(this.pivotY, 0.0D, renderRect.getHeight());
        Vec2 position = new Vec2((int) (unscaledMouseX - offsetX), (int) (unscaledMouseY - offsetY));
        overlay.setUnscaledPosition(position);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        Overlay overlay = this.getOverlay(mouseX, mouseY);

        if (overlay != null) {
            this.select(overlay, mouseX, mouseY);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private Overlay getOverlay(double mouseX, double mouseY) {

        for (Overlay overlay : this.overlays) {
            float scale = overlay.getScale();
            double unscaledMouseX = mouseX / scale;
            double unscaledMouseY = mouseY / scale;
            Rect2i renderRect = overlay.getUnscaledRenderRect(this.minecraft);

            if (renderRect.contains((int) unscaledMouseX, (int) unscaledMouseY)) {
                return overlay;
            }
        }
        return null;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {

        Overlay overlay = this.getOverlay(mouseX, mouseY);

        if (overlay != null) {

            float scale = overlay.getScale();
            double prevUnscaledMouseX = mouseX / scale;
            double prevUnscaledMouseY = mouseY / scale;
            Rect2i prevRenderRect = overlay.getUnscaledRenderRect(this.minecraft);
            Vec2 prevPosition = overlay.getUnscaledPosition();
            double pivotX = Mth.inverseLerp(prevUnscaledMouseX - prevRenderRect.getX(), 0.0D, prevRenderRect.getWidth());
            double pivotY = Mth.inverseLerp(prevUnscaledMouseY - prevRenderRect.getY(), 0.0D, prevRenderRect.getHeight());

            float factor = 1.1F;

            if (delta > 0) {
                scale = scale * factor;
            } else {
                scale = scale / factor;
            }

            overlay.setScale(scale);

            double nextUnscaledMouseX = mouseX / scale;
            double nextUnscaledMouseY = mouseY / scale;
            Rect2i nextRenderRect = overlay.getUnscaledRenderRect(this.minecraft);
            double errorX = nextRenderRect.getX() + Mth.lerp(pivotX, 0.0D, nextRenderRect.getWidth()) - nextUnscaledMouseX;
            double errorY = nextRenderRect.getY() + Mth.lerp(pivotY, 0.0D, nextRenderRect.getHeight()) - nextUnscaledMouseY;

            Vec2 position = new Vec2((float) (prevPosition.x - errorX), (float) (prevPosition.y - errorY));
            overlay.setUnscaledPosition(position);

            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        this.deselect();
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void select(Overlay overlay, double clickedX, double clickedY) {

        float scale = overlay.getScale();
        double unscaledClickedX = clickedX / scale;
        double unscaledClickedY = clickedY / scale;
        Rect2i renderRect = overlay.getUnscaledRenderRect(this.minecraft);

        this.clickedX = clickedX;
        this.clickedY = clickedY;
        this.pivotX = Mth.inverseLerp(unscaledClickedX - renderRect.getX(), 0.0D, renderRect.getWidth());
        this.pivotY = Mth.inverseLerp(unscaledClickedY - renderRect.getY(), 0.0D, renderRect.getHeight());
        this.dragging = false;
        this.selectdOverlay = overlay;
    }

    private void deselect() {

        this.dragging = false;
        this.selectdOverlay = null;
    }

    @Override
    public boolean isPauseScreen() {
        return this.getParent().isPauseScreen();
    }

    public Screen getParent() {
        return this.parent;
    }

    public abstract class Overlay {
        private Vec2 unscaledPositionBackup;
        private Vec2 unscaledPosition;
        private float scaleBackup;
        private float scale;

        public Overlay() {
            this.unscaledPosition = new Vec2(0.0F, 0.0F);
            this.scale = 0.0F;
        }

        /**
         * Load config values and backup values for revert
         *
         * @param minecraft
         */
        public void load(Minecraft minecraft) {
            this.onLoad(minecraft);
            this.backup();
        }

        /**
         * Restore config values to backup
         *
         * @param minecraft
         */
        public void revert(Minecraft minecraft) {
            this.restore();
            this.apply(minecraft);
        }

        /**
         * Reset config values to default, Can revert
         *
         * @param minecraft
         */
        public void reset(Minecraft minecraft) {
            this.onResetConfig(minecraft);
            this.onLoad(minecraft);
        }

        /**
         * Apply current values to config and backup
         *
         * @param minecraft
         */
        public void save(Minecraft minecraft) {
            this.apply(minecraft);
            this.backup();
        }

        protected void backup() {
            this.unscaledPositionBackup = this.unscaledPosition;
            this.scaleBackup = this.scale;
        }

        protected void restore() {
            this.unscaledPosition = this.unscaledPositionBackup;
            this.scale = this.scaleBackup;
        }

        protected abstract void onLoad(Minecraft minecraft);

        protected abstract void onResetConfig(Minecraft minecraft);

        public abstract void apply(Minecraft minecraft);

        public abstract void render(PoseStack poseStack, Minecraft minecraft);

        public abstract Rect2i getUnscaledRenderRect(Minecraft minecraft);

        public Vec2 getUnscaledPosition() {
            return this.unscaledPosition;
        }

        public void setUnscaledPosition(Vec2 position) {
            this.setUnscaledPosition(position.x, position.y);
        }

        public void setUnscaledPosition(float x, float y) {
            this.unscaledPosition = new Vec2(x, y);
        }

        public float getScale() {
            return this.scale;
        }

        public void setScale(float scale) {
            this.scale = Math.max(scale, 0.0F);
        }
    }

    public class OxygenTankOverlay extends Overlay {

        @Override
        protected void onLoad(Minecraft minecraft) {
            this.setUnscaledPosition(AdAstraConfig.oxygenBarXOffset, AdAstraConfig.oxygenBarYOffset);
            this.setScale(AdAstraConfig.oxygenBarScale);
        }

        @Override
        public void apply(Minecraft minecraft) {
            Vec2 position = this.getUnscaledPosition();
            AdAstraConfig.oxygenBarXOffset = (int) position.x;
            AdAstraConfig.oxygenBarYOffset = (int) position.y;
            AdAstraConfig.oxygenBarScale = this.getScale();
        }

        @Override
        protected void onResetConfig(Minecraft minecraft) {
            ResourcefulConfig config = AdAstra.CONFIGURATOR.getConfig(AdAstraConfig.class);
            config.getEntry("oxygenBarXOffset").get().reset();
            config.getEntry("oxygenBarYOffset").get().reset();
            config.getEntry("oxygenBarScale").get().reset();
        }

        @Override
        public void render(PoseStack poseStack, Minecraft minecraft) {
            PlayerOverlayScreen.renderOxygenTank(poseStack, minecraft);
        }

        @Override
        public Rect2i getUnscaledRenderRect(Minecraft minecraft) {
            return PlayerOverlayScreen.getOxygenTankUnscaledRect(minecraft);
        }
    }

    public class EnergyBarOverlay extends Overlay {

        @Override
        protected void onLoad(Minecraft minecraft) {
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            this.setUnscaledPosition(screenWidth - AdAstraConfig.energyBarXOffset, AdAstraConfig.energyBarYOffset);
            this.setScale(AdAstraConfig.energyBarScale);
        }

        @Override
        public void apply(Minecraft minecraft) {
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            Vec2 position = this.getUnscaledPosition();
            AdAstraConfig.energyBarXOffset = (int) (screenWidth - position.x);
            AdAstraConfig.energyBarYOffset = (int) position.y;
            AdAstraConfig.energyBarScale = this.getScale();
        }

        @Override
        protected void onResetConfig(Minecraft minecraft) {
            ResourcefulConfig config = AdAstra.CONFIGURATOR.getConfig(AdAstraConfig.class);
            config.getEntry("energyBarXOffset").get().reset();
            config.getEntry("energyBarYOffset").get().reset();
            config.getEntry("energyBarScale").get().reset();
        }

        @Override
        public void render(PoseStack poseStack, Minecraft minecraft) {
            PlayerOverlayScreen.renderEnergyBar(poseStack, minecraft);
        }

        @Override
        public Rect2i getUnscaledRenderRect(Minecraft minecraft) {
            return PlayerOverlayScreen.getEnergyBarUnscaledRect(minecraft);
        }
    }
}
