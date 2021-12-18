package net.mrscauthd.beyond_earth.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public abstract class BeyondEarthRecipe implements Recipe<Container> {

	private final ResourceLocation id;

	public BeyondEarthRecipe(ResourceLocation id, JsonObject json) {
		this.id = id;
	}

	public BeyondEarthRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		this.id = id;
	}

	public BeyondEarthRecipe(ResourceLocation id) {
		this.id = id;
	}

	public void write(FriendlyByteBuf buffer) {

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
	public ItemStack assemble(Container var1) {
		// Don't use this
		return ItemStack.EMPTY;
	}

	/**
	 * Don't use this
	 */
	@Override
	@Deprecated
	public ItemStack getResultItem() {
		// Don't use this
		return ItemStack.EMPTY;
	}
	
	/**
	 * Don't use this
	 */
	@Override
	@Deprecated
	public boolean matches(Container p_44002_, Level p_44003_) {
		// Don't use this
		return true;
	}

}
