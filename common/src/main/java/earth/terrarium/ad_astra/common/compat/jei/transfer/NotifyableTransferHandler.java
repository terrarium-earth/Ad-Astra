package earth.terrarium.ad_astra.common.compat.jei.transfer;

import java.util.Optional;

import net.minecraft.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.Nullable;

import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.client.NotifyRecipeTransferPacket;
import earth.terrarium.ad_astra.common.recipe.ModRecipe;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

@MethodsReturnNonnullByDefault
public class NotifyableTransferHandler<MENU extends AbstractMachineMenu<?>, RECIPE extends ModRecipe> implements IRecipeTransferHandler<MENU, RECIPE> {

    private final IRecipeTransferHandler<MENU, RECIPE> internal;

    public NotifyableTransferHandler(IRecipeTransferHandler<MENU, RECIPE> internal) {
        this.internal = internal;
    }

    @Override
    public Class<? extends MENU> getContainerClass() {
        return this.internal.getContainerClass();
    }

    @Override
    public Optional<MenuType<MENU>> getMenuType() {
        return this.internal.getMenuType();
    }

    @Override
    public RecipeType<RECIPE> getRecipeType() {
        return this.internal.getRecipeType();
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(MENU container, RECIPE recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer) {

        IRecipeTransferError transferError = this.internal.transferRecipe(container, recipe, recipeSlots, player, maxTransfer, doTransfer);
        if (transferError == null && doTransfer) {
            NetworkHandling.CHANNEL.sendToServer(new NotifyRecipeTransferPacket(recipe.id()));
        }

        return transferError;
    }

}
