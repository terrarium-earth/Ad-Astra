package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class BossToolsRecipe implements IRecipe<IInventory> {

	private final ResourceLocation id;

	public BossToolsRecipe(ResourceLocation id, JsonObject json) {
		this.id = id;
	}

	public BossToolsRecipe(ResourceLocation id, PacketBuffer buffer) {
		this.id = id;
	}

	public BossToolsRecipe(ResourceLocation id) {
		this.id = id;
	}

	public void write(PacketBuffer buffer) {

	}

	@Override
	public final ResourceLocation getId() {
		return this.id;
	}
	
	@Override
	public String getGroup() {
		return this.getSerializer().getRegistryType().toString();
	}

	/**
	 * Don't use this
	 */
	@Override
	@Deprecated
	public ItemStack getCraftingResult(IInventory var1) {
		// Don't use this
		return ItemStack.EMPTY;
	}

	/**
	 * Don't use this
	 */
	@Override
	@Deprecated
	public ItemStack getRecipeOutput() {
		// Don't use this
		return ItemStack.EMPTY;
	}
	
	/**
	 * Don't use this
	 */
	@Override
	@Deprecated
	public boolean matches(IInventory var1, World var2) {
		// Don't use this
		return true;
	}

}
