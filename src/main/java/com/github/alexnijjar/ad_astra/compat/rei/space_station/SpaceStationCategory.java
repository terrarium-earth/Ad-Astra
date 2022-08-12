package com.github.alexnijjar.ad_astra.compat.rei.space_station;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.client.screens.utils.ScreenUtils;
import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpaceStationCategory implements DisplayCategory<SpaceStationDisplay> {

    public static final Identifier ICON = new ModIdentifier("textures/gui/space_station_icon.png");

    @Override
    public Renderer getIcon() {
        return new Renderer() {

            @Override
            public void render(MatrixStack matrices, Rectangle bounds, int mouseX, int mouseY, float delta) {
                ScreenUtils.addTexture(matrices, bounds.x, bounds.y, 16, 16, ICON);
            }

            @Override
            public int getZ() {
                return 0;
            }

            @Override
            public void setZ(int z) {
            }
        };
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("rei.category.ad_astra.space_station");
    }

    @Override
    public int getDisplayWidth(SpaceStationDisplay display) {
        return 150;
    }

    @Override
    public int getDisplayHeight() {
        return 51;
    }

    @Override
    public CategoryIdentifier<? extends SpaceStationDisplay> getCategoryIdentifier() {
        return REICategories.SPACE_STATION_CATEGORY;
    }

    @Override
    public List<Widget> setupDisplay(SpaceStationDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 71, bounds.getCenterY() - 20);

        List<Widget> widgets = new ArrayList<>();
        List<EntryIngredient> inputs = display.getInputEntries();

        widgets.add(Widgets.createRecipeBase(bounds));
        for (int i = 0; i < 8; i++) {
            if (i < inputs.size()) {
                Item item = ((ItemStack) inputs.get(i).get(0).getValue()).getItem();
                widgets.add(Widgets.createSlot(new Point(startPoint.x + 18 * i, startPoint.y)).markInput().entries(List.of(EntryStacks.of(new ItemStack(item, display.recipe().getStackCounts().get(i))))));
            } else {
                widgets.add(Widgets.createSlot(new Point(startPoint.x + 18 * i, startPoint.y)));
            }
        }

        return widgets;
    }
}