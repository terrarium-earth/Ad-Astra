package earth.terrarium.ad_astra.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class GuiUtils {
    public static final ResourceLocation ENERGY_BAR_EMPTY = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/energy_bar_empty.png");
    public static final ResourceLocation ENERGY_BAR_FULL = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/energy_bar_full.png");
    public static final ResourceLocation FLUID_BAR_BACK = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/fluid_bar_back.png");
    public static final ResourceLocation FLUID_BAR_OVERLAY = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/fluid_bar_overlay.png");

    public static void createEnergyBar(PoseStack poseStack, int x, int y, WrappedBlockEnergyContainer energyContainer) {
        createEnergyBar(poseStack, x, y, energyContainer.getStoredEnergy() / (double) energyContainer.getMaxCapacity());
    }

    public static void createEnergyBar(PoseStack poseStack, int x, int y, double ratio) {
        RenderSystem.setShaderTexture(0, ENERGY_BAR_EMPTY);
        GuiComponent.blit(poseStack, x, y, 0, 0, 19, 64, 19, 64);

        try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), poseStack, x + 3, y + 8 + 46 - (int) (46 * ratio), 13, 46)) {
            RenderSystem.setShaderTexture(0, ENERGY_BAR_FULL);
            GuiComponent.blit(poseStack, x + 3, y + 8, 0, 0, 13, 46, 13, 46);
        }
    }

    public static void createFluidBar(PoseStack poseStack, int x, int y, int tank, WrappedBlockFluidContainer fluidContainer) {
        createFluidBar(poseStack, x, y, fluidContainer.getFluids().get(tank).getFluidAmount() / (double) fluidContainer.getTankCapacity(tank), fluidContainer.getFluids().get(tank));
    }

    public static void createFluidBar(PoseStack poseStack, int x, int y, double ratio, FluidHolder fluid) {
        RenderSystem.setShaderTexture(0, FLUID_BAR_BACK);
        GuiComponent.blit(poseStack, x, y, 0, 0, 24, 59, 24, 59);

        if (!fluid.isEmpty()) {
            TextureAtlasSprite sprite = ClientFluidHooks.getFluidSprite(fluid);
            int colour = ClientFluidHooks.getFluidColor(fluid);
            RenderSystem.setShaderColor((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f);
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

            try (var ignored = RenderUtils.createScissorBox(Minecraft.getInstance(), poseStack, x + 6, y + 39 + 12 - (int) (46 * ratio), 13, 46)) {
                for (int i = 1; i < 12 * 4; i += 12) {
                    GuiComponent.blit(poseStack, x + 6, y + 39 - i, 0, 12, 12, sprite);
                }
            }
        }
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, FLUID_BAR_OVERLAY);
        GuiComponent.blit(poseStack, x + 6, y + 4, 0, 0, 12, 46, 12, 46);
    }

    public static void createEnergyTooltip(AbstractContainerScreen<?> screen, int mouseX, int mouseY, PoseStack poseStack, int x, int y, WrappedBlockEnergyContainer energyContainer) {
        if (isHovering(x, y, 19, 64, mouseX, mouseY)) {
            Component component = Component.translatable("tooltip.ad_astra.energy_bar", energyContainer.getStoredEnergy(), energyContainer.getMaxCapacity());
            screen.renderTooltip(poseStack, component, mouseX, mouseY);
        }
    }

    public static void createFluidTooltip(AbstractContainerScreen<?> screen, int mouseX, int mouseY, PoseStack poseStack, int x, int y, int tank, WrappedBlockFluidContainer fluidContainer) {
        if (isHovering(x, y, 24, 59, mouseX, mouseY)) {
            Component component = Component.translatable("tooltip.ad_astra.fluid_tank", FluidHooks.toMillibuckets(fluidContainer.getFluids().get(tank).getFluidAmount()), FluidHooks.toMillibuckets(fluidContainer.getTankCapacity(tank)), getFluidTranslation(fluidContainer.getFluids().get(tank).getFluid()).getString());
            screen.renderTooltip(poseStack, component, mouseX, mouseY);
        }
    }

    public static Component getFluidTranslation(Fluid fluid) {
        if (fluid.equals(Fluids.EMPTY)) {
            return Component.translatable("tooltip.ad_astra.empty_tank").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA));
        }
        return Component.translatable(fluid.defaultFluidState().createLegacyBlock().getBlock().getDescriptionId()).setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA));
    }

    public static boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= (double) (x - 1) && mouseX < (double) (x + width + 1) && mouseY >= (double) (y - 1) && mouseY < (double) (y + height + 1);
    }
}