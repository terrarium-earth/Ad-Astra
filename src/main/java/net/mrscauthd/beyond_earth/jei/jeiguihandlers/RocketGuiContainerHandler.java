package net.mrscauthd.beyond_earth.jei.jeiguihandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.mrscauthd.beyond_earth.entity.RocketTier1Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier2Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier3Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;
import net.mrscauthd.beyond_earth.gui.screens.rocket.RocketGuiWindow;
import net.mrscauthd.beyond_earth.jei.JeiPlugin;

public class RocketGuiContainerHandler implements IGuiContainerHandler<RocketGuiWindow> {

    public RocketGuiContainerHandler() {

    }

    @Override
    public Collection<IGuiClickableArea> getGuiClickableAreas(RocketGuiWindow containerScreen, double mouseX, double mouseY) {
        return Collections.singleton(new IGuiClickableArea() {
            @Override
            public Rect2i getArea() {
                return containerScreen.getFluidBounds().toRect2i();
            }

            @Override
            public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
                if (containerScreen.getRocket() instanceof RocketTier1Entity) {
                    recipesGui.showCategories(Arrays.asList(JeiPlugin.RocketTier1JeiCategory.Uid));
                }
                if (containerScreen.getRocket() instanceof RocketTier2Entity) {
                    recipesGui.showCategories(Arrays.asList(JeiPlugin.RocketTier2JeiCategory.Uid));
                }
                if (containerScreen.getRocket() instanceof RocketTier3Entity) {
                    recipesGui.showCategories(Arrays.asList(JeiPlugin.RocketTier3JeiCategory.Uid));
                }
                if (containerScreen.getRocket() instanceof RocketTier4Entity) {
                    recipesGui.showCategories(Arrays.asList(JeiPlugin.RocketTier4JeiCategory.Uid));
                }
            }

            @Override
            public List<Component> getTooltipStrings() {
                List<Component> list = new ArrayList<>();
                list.add(containerScreen.getFuelGaugeComponent());
                list.add(new TranslatableComponent("jei.tooltip.show.recipes"));
                return list;
            }
        });

    }
}