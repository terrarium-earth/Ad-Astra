package earth.terrarium.adastra.common.constants;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ConstantComponents {
    public static final Component ITEM_GROUP = Component.translatable("itemGroup.adastra.main");
    public static final Component DEATH_OXYGEN = Component.translatable("death.attack.oxygen");
    public static final Component DEATH_OXYGEN_PLAYER = Component.translatable("death.attack.oxygen.player");

    public static final Component TOGGLE_SUIT_FLIGHT_KEY = Component.translatable("key.adastra.toggle_suit_flight");
    public static final Component AD_ASTRA_CATEGORY = Component.translatable("key.categories.adastra");

    public static final Component SUIT_FLIGHT_ENABLED = Component.translatable("message.adastra.suit_flight_enabled").withStyle(ChatFormatting.GOLD);
    public static final Component SUIT_FLIGHT_DISABLED = Component.translatable("message.adastra.suit_flight_disabled").withStyle(ChatFormatting.GOLD);

    public static final Component OXYGEN_TRUE = Component.translatable("text.adastra.oxygen_true");
    public static final Component OXYGEN_FALSE = Component.translatable("text.adastra.oxygen_false");
}
