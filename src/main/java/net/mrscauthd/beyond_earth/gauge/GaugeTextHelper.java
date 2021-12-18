package net.mrscauthd.beyond_earth.gauge;

import java.text.NumberFormat;

import com.google.common.collect.Lists;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.capability.IOxygenStorage;

public class GaugeTextHelper {

	public static final ResourceLocation USING_NAME = new ResourceLocation(BeyondEarthMod.MODID, "using");
	public static final ResourceLocation USING2_NAME = new ResourceLocation(BeyondEarthMod.MODID, "using2");
	public static final ResourceLocation GENERATING_NAME = new ResourceLocation(BeyondEarthMod.MODID, "generating");
	public static final ResourceLocation MAXGENERATION_NAME = new ResourceLocation(BeyondEarthMod.MODID, "maxgeneration");
	public static final ResourceLocation TOTAL_NAME = new ResourceLocation(BeyondEarthMod.MODID, "total");
	public static final ResourceLocation STORED_NAME = new ResourceLocation(BeyondEarthMod.MODID, "stored");
	public static final ResourceLocation CAPACITY_NAME = new ResourceLocation(BeyondEarthMod.MODID, "capacity");
	public static final ResourceLocation TRANSFER_NAME = new ResourceLocation(BeyondEarthMod.MODID, "transfer");

	public static final String USING_KEY = makeTranslationKey(USING_NAME);
	public static final String USING2_KEY = makeTranslationKey(USING2_NAME);
	public static final String GENERATING_KEY = makeTranslationKey(GENERATING_NAME);
	public static final String MAXGNERATION_KEY = makeTranslationKey(MAXGENERATION_NAME);
	public static final String TOTAL_KEY = makeTranslationKey(TOTAL_NAME);
	public static final String STORED_KEY = makeTranslationKey(STORED_NAME);
	public static final String CAPACITY_KEY = makeTranslationKey(CAPACITY_NAME);
	public static final String TRANSFER_KEY = makeTranslationKey(TRANSFER_NAME);

	public static final String PER_TICK_UNIT_SUFFIX = "/t";

	public static String makeTranslationKey(ResourceLocation name) {
		return "gauge_text." + name.getNamespace() + "." + name.getPath();
	}

	public static GaugeTextBuilder getText(IGaugeValue value, String key, Object... values) {
		return new GaugeTextBuilder(value, key, Lists.newArrayList(values));
	}

	public static GaugeTextBuilder getValueText(IGaugeValue value) {
		return getText(value, "%1$s: %2$s");
	}

	public static GaugeTextBuilder getStorageText(IGaugeValue value) {
		return getText(value, "%1$s: %2$s \u00A78| %3$s");
	}

	public static GaugeTextBuilder getPercentText(IGaugeValue value, int decimals) {
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(decimals);
		return getText(value, "%1$s: %4$s", numberInstance.format(value.getDisplayRatio() * 100.0D) + "%");
	}

	public static GaugeTextBuilder getPercentText(IGaugeValue value) {
		return getPercentText(value, 0);
	}

	public static GaugeTextBuilder getUsingText(IGaugeValue value) {
		return getText(value, USING_KEY);
	}

	public static GaugeTextBuilder getUsingPerTickText(IGaugeValue value) {
		return getUsingText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static GaugeTextBuilder getUsingText2(IGaugeValue value, int interval) {
		return getText(value, USING2_KEY, String.valueOf(interval));
	}

	public static GaugeTextBuilder getTotalText(IGaugeValue value) {
		return getText(value, TOTAL_KEY);
	}

	public static GaugeTextBuilder getGeneratingText(IGaugeValue value) {
		return getText(value, GENERATING_KEY);
	}

	public static GaugeTextBuilder getGeneratingPerTickText(IGaugeValue value) {
		return getGeneratingText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static GaugeTextBuilder getMaxGenerationText(IGaugeValue value) {
		return getText(value, MAXGNERATION_KEY);
	}

	public static GaugeTextBuilder getMaxGenerationPerTickText(IGaugeValue value) {
		return getMaxGenerationText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static GaugeTextBuilder getStoredText(IGaugeValue value) {
		return getText(value, STORED_KEY);
	}

	public static GaugeTextBuilder getCapacityText(IGaugeValue value) {
		return getText(value, CAPACITY_KEY);
	}

	public static GaugeTextBuilder getTransferText(IGaugeValue value) {
		return getText(value, TRANSFER_KEY);
	}

	public static GaugeTextBuilder getTransferPerTickText(IGaugeValue value) {
		return getTransferText(value).setUnitSuffix(PER_TICK_UNIT_SUFFIX);
	}

	public static MutableComponent buildSpacesuitOxygenTooltip(IGaugeValue value) {
		GaugeTextBuilder builder = GaugeTextHelper.getText(value, "%1$s: %2$s \u00A78| %3$s");
		builder.setTextStyle(Style.EMPTY.withColor(ChatFormatting.BLUE));
		builder.setAmountStyle(Style.EMPTY.withColor(ChatFormatting.GOLD));
		builder.setCapacityStyle(Style.EMPTY.withColor(ChatFormatting.RED));
		return builder.build();
	}

	public static MutableComponent buildSpacesuitOxygenTooltip(IOxygenStorage oxygenStorage) {
		IGaugeValue oxygen;

		if (oxygenStorage != null) {
			oxygen = GaugeValueHelper.getOxygen(oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored());
		} else {
			oxygen = GaugeValueHelper.getOxygen(0, 0);
		}

		return buildSpacesuitOxygenTooltip(oxygen);
	}

	public static MutableComponent buildBlockTooltip(GaugeTextBuilder builder, ChatFormatting color) {
		builder.setTextStyle(Style.EMPTY.withColor(ChatFormatting.BLUE));
		builder.setAmountStyle(Style.EMPTY.withColor(color));
		builder.setCapacityStyle(Style.EMPTY.withColor(color));
		builder.setExtraStyle(0, Style.EMPTY.withColor(color));
		return builder.build();
	}

	public static MutableComponent buildBlockTooltip(GaugeTextBuilder builder) {
		return buildBlockTooltip(builder, ChatFormatting.GRAY);
	}
}
