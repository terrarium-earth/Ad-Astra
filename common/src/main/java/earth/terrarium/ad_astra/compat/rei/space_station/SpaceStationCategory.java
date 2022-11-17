package earth.terrarium.ad_astra.compat.rei.space_station;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.screens.utils.ScreenUtils;
import earth.terrarium.ad_astra.compat.rei.REICategories;
import earth.terrarium.ad_astra.util.ModResourceLocation;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SpaceStationCategory implements DisplayCategory<SpaceStationDisplay> {

    public static final ResourceLocation ICON = new ModResourceLocation("textures/gui/space_station_icon.png");

    @Override
    public Renderer getIcon() {
        return new Renderer() {

            @Override
            public void render(PoseStack poseStack, Rectangle bounds, int mouseX, int mouseY, float delta) {
                ScreenUtils.addTexture(poseStack, bounds.x, bounds.y, 16, 16, ICON);
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
    public Component getTitle() {
        return Component.translatable("rei.category.ad_astra.space_station");
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
                widgets.add(Widgets.createSlot(new Point(startPoint.x + 18 * i, startPoint.y)).markInput().entries(List.of(EntryStacks.of(new ItemStack(item, display.recipe().getHolders().get(i).count())))));
            } else {
                widgets.add(Widgets.createSlot(new Point(startPoint.x + 18 * i, startPoint.y)));
            }
        }

        return widgets;
    }
}