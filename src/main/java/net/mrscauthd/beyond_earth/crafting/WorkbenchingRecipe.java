package net.mrscauthd.beyond_earth.crafting;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.beyond_earth.ModInnet;
import net.mrscauthd.beyond_earth.inventory.RocketPartsItemHandler;

public class WorkbenchingRecipe extends BeyondEarthRecipe implements BiPredicate<RocketPartsItemHandler, Boolean> {

	private final Map<RocketPart, List<Ingredient>> parts;
	private final ItemStack output;

	public WorkbenchingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		JsonObject inputJson = GsonHelper.getAsJsonObject(json, "input");
		JsonObject partsJson = GsonHelper.getAsJsonObject(inputJson, "parts");

		Map<RocketPart, List<Ingredient>> map = new HashMap<>();

		for (Entry<String, JsonElement> entry : partsJson.entrySet()) {
			RocketPart part = ModInnet.ROCKET_PARTS_REGISTRY.getValue(new ResourceLocation(entry.getKey()));
			JsonArray slotsJson = entry.getValue().getAsJsonArray();
			List<Ingredient> ingredients = Lists.newArrayList(slotsJson).stream().map(Ingredient::fromJson).collect(Collectors.toList());
			map.put(part, Collections.unmodifiableList(ingredients));
		}

		this.parts = Collections.unmodifiableMap(map);
		this.output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
	}

	public WorkbenchingRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		int partsSize = buffer.readInt();
		Map<RocketPart, List<Ingredient>> map = new HashMap<>();

		for (int i = 0; i < partsSize; i++) {
			RocketPart part = buffer.readRegistryId();
			int ingredientsSize = buffer.readInt();
			List<Ingredient> ingredients = Arrays.stream(new Ingredient[ingredientsSize]).map(in -> Ingredient.fromNetwork(buffer)).collect(Collectors.toList());
			map.put(part, Collections.unmodifiableList(ingredients));
		}

		this.parts = Collections.unmodifiableMap(map);
		this.output = buffer.readItem();
	}

	public WorkbenchingRecipe(ResourceLocation id, Map<RocketPart, List<Ingredient>> parts, ItemStack output) {
		super(id);

		Map<RocketPart, List<Ingredient>> map = new HashMap<>();

		for (Entry<RocketPart, List<Ingredient>> entry : parts.entrySet()) {
			map.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
		}

		this.parts = Collections.unmodifiableMap(map);
		this.output = output.copy();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeInt(this.parts.size());

		for (Entry<RocketPart, List<Ingredient>> entry : this.parts.entrySet()) {
			buffer.writeRegistryId(entry.getKey());

			List<Ingredient> ingredients = entry.getValue();
			buffer.writeInt(ingredients.size());
			ingredients.forEach(i -> i.toNetwork(buffer));
		}

		buffer.writeItem(this.output);
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = super.getIngredients();
		ingredients.addAll(this.parts.values().stream().flatMap(l -> l.stream()).collect(Collectors.toList()));
		return ingredients;
	}

	@Override
	public boolean canCraftInDimensions(int var1, int var2) {
		return false;
	}

	public ItemStack getOutput() {
		return this.output.copy();
	}

	public Map<RocketPart, List<Ingredient>> getParts() {
		return this.parts;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_WORKBENCHING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return BeyondEarthRecipeTypes.WORKBENCHING;
	}

	/**
	 * Part order is not important in find recipe
	 */
	public boolean test(RocketPartsItemHandler itemHandler) {
		return this.test(itemHandler, false);
	}

	/**
	 * Part order is not important in find recipe
	 */
	@Override
	public boolean test(RocketPartsItemHandler itemHandler, Boolean ignoreAir) {
		for (Entry<RocketPart, List<Ingredient>> entry : this.getParts().entrySet()) {
			RocketPart part = entry.getKey();
			IItemHandlerModifiable subHandler = itemHandler.getSubHandlers().get(part);

			if (subHandler == null) {
				return false;
			}

			int subHandlerSlots = subHandler.getSlots();
			List<Ingredient> ingredients = entry.getValue();

			if (subHandlerSlots < ingredients.size()) {
				return false;
			}

			for (int i = 0; i < subHandlerSlots; i++) {
				ItemStack stack = subHandler.getStackInSlot(i);
				Ingredient ingredient = ingredients.get(i);

				if (ignoreAir && stack.isEmpty()) {
					continue;
				} else if (!ingredient.test(stack)) {
					return false;
				}
			}
		}

		return true;
	}

}
