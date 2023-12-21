package earth.terrarium.adastra.common.menus;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundSyncSpaceStationsPacket;
import earth.terrarium.adastra.common.recipes.SpaceStationRecipe;
import earth.terrarium.adastra.common.recipes.base.IngredientHolder;
import earth.terrarium.adastra.common.registry.ModMenus;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetsMenu extends AbstractContainerMenu {

    @Nullable
    protected Rocket rocket;
    protected int tier = 255;
    protected final Inventory inventory;
    protected final Player player;
    protected final Level level;

    public PlanetsMenu(int containerId, Inventory inventory) {
        super(ModMenus.PLANETS.get(), containerId);
        this.inventory = inventory;
        player = inventory.player;
        level = player.level();
        if (player.getVehicle() instanceof Rocket vehicle) {
            rocket = vehicle;
            tier = rocket.tier();
        }
        if (level instanceof ServerLevel serverLevel) {
            NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncSpaceStationsPacket(SpaceStationHandler.getAllSpaceStations(serverLevel)), player);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Nullable
    public Rocket rocket() {
        return rocket;
    }

    public int tier() {
        return tier;
    }

    public Player player() {
        return player;
    }

    public Map<ResourceLocation, List<Pair<ItemStack, Integer>>> getSpaceStationRecipes() {
        List<SpaceStationRecipe> spaceStationRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get());
        Map<ResourceLocation, List<Pair<ItemStack, Integer>>> recipes = new HashMap<>(spaceStationRecipes.size());
        for (var recipe : spaceStationRecipes) {
            for (IngredientHolder holder : recipe.ingredients()) {
                int count = 0;
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    var stack = inventory.getItem(i);
                    if (holder.ingredient().test(stack)) {
                        count += stack.getCount();
                    }
                }
                recipes.computeIfAbsent(recipe.dimension(), k -> new ArrayList<>()).add(new Pair<>(holder.ingredient().getItems()[0].copyWithCount(holder.count()), count));
            }
        }
        return recipes;
    }

    public static class Provider implements MenuProvider {
        @Override
        public Component getDisplayName() {
            return Component.empty();
        }

        @Override
        public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
            return new PlanetsMenu(containerId, inventory);
        }
    }
}
