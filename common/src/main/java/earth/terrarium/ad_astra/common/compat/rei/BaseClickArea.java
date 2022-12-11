package earth.terrarium.ad_astra.common.compat.rei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.ClickArea;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class BaseClickArea<T extends Screen> implements ClickArea<T> {

	@Override
	public Result handle(ClickAreaContext<T> context) {
		if (this.test(context)) {
			return this.getSuccess(context.getScreen());
		} else {
			return this.getFail();
		}
	}

	public abstract Rectangle getBounds(T screen);

	public abstract Result getSuccess(T screen);

	public Result getFail() {
		return Result.fail();
	}

	public boolean test(ClickAreaContext<T> context) {
		T screen = context.getScreen();
		Point mouse = context.getMousePosition();
		return this.getBounds(screen).contains(mouse.getX(), mouse.getY());
	}

	public static Result category(CategoryIdentifier<?> category) {
		return Result.success().category(category);
	}

	public static Result category(List<CategoryIdentifier<?>> categories) {
		return Result.success().categories(categories);
	}

	public static Result categoryWithTooltip(CategoryIdentifier<?> category, Component tooltip) {
		return categoryWithTooltip(category, Collections.singletonList(tooltip));
	}

	public static Result categoryWithTooltip(CategoryIdentifier<?> category, List<Component> tooltip) {
		return categoryWithTooltip(Collections.singletonList(category), tooltip);
	}

	public static Result categoryWithTooltip(List<CategoryIdentifier<?>> categories, Component tooltip) {
		return categoryWithTooltip(categories, Collections.singletonList(tooltip));
	}

	public static Result categoryWithTooltip(List<CategoryIdentifier<?>> categories, List<Component> tooltip) {
		Result result = Result.success().categories(categories);
		List<Component> newTooltipBuilder = new ArrayList<>();
		newTooltipBuilder.addAll(tooltip);
		newTooltipBuilder.addAll(Arrays.stream(result.getTooltips()).toList());
		Component[] newTooltip = newTooltipBuilder.toArray(new Component[0]);
		return result.tooltip(() -> newTooltip);
	}

}
