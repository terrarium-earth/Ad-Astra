package net.mrscauthd.boss_tools.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class FluidIngredient implements Predicate<FluidStack> {
	public static final FluidIngredient EMPTY = new Empty();

	public static final int MINAMOUNT = 1;
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_TAG = "tag";
	public static final String KEY_NAME = "name";
	public static final String KEY_NAMES = "names";

	private List<Fluid> cachedFluids;
	private int amount;

	private FluidIngredient(int amount) {
		this.amount = amount;
	}

	public boolean test(FluidStack stack) {
		return this.getFluids().stream().anyMatch(f -> f.isSame(stack.getFluid())) && stack.getAmount() >= this.amount;
	}

	public List<Fluid> getFluids() {
		if (this.cachedFluids == null) {
			this.cachedFluids = Collections.unmodifiableList(ForgeRegistries.FLUIDS.getValues().stream().filter(this::testFluid).collect(Collectors.toList()));
		}

		return this.cachedFluids;
	}

	public List<FluidStack> toStacks() {
		return this.getFluids().stream().map(f -> new FluidStack(f, this.getAmount())).collect(Collectors.toList());
	}

	public FluidStack toStack() {
		List<Fluid> fluids = this.getFluids();
		return fluids.size() == 0 ? FluidStack.EMPTY : new FluidStack(fluids.get(0), this.getAmount());
	}

	public int getAmount() {
		return this.amount;
	}

	public void write(FriendlyByteBuf buffer) {
		List<Fluid> fluids = this.getFluids();
		int size = fluids.size();
		buffer.writeInt(size);

		for (int i = 0; i < size; i++) {
			buffer.writeRegistryId(fluids.get(i));
		}

		buffer.writeInt(this.getAmount());
	}

	public static FluidIngredient deserialize(JsonObject json) {
		int amount = GsonHelper.getAsInt(json, KEY_AMOUNT);

		if (json.has(KEY_TAG)) {
			String tagName = GsonHelper.getAsString(json, KEY_TAG);
			return of(FluidTags.bind(tagName), amount);
		} else if (json.has(KEY_NAME)) {
			JsonElement nameJson = json.get(KEY_NAME);

			if (nameJson.isJsonArray()) {
				List<Fluid> fluids = Lists.newArrayList(nameJson.getAsJsonArray()).stream().map(j -> ForgeRegistries.FLUIDS.getValue(new ResourceLocation(j.getAsString()))).collect(Collectors.toList());
				return of(fluids, amount);
			} else {
				return of(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nameJson.getAsString())), amount);
			}
		}

		return EMPTY;
	}

	public static FluidIngredient read(FriendlyByteBuf buffer) {
		int size = buffer.readInt();
		List<Fluid> fluids = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			fluids.add(buffer.<Fluid>readRegistryId());
		}

		int amount = buffer.readInt();
		return of(fluids, amount);
	}

	public static FluidIngredient of(Fluid fluid) {
		return new FluidMatch(fluid, MINAMOUNT);
	}

	public static FluidIngredient of(Fluid fluid, int amount) {
		return new FluidMatch(fluid, amount);
	}

	public static FluidIngredient of(FluidStack stack) {
		return new FluidMatch(stack.getFluid(), stack.getAmount());
	}

	public static FluidIngredient of(List<Fluid> fluids, int amount) {
		return new FluidMatch(fluids, amount);
	}

	public static FluidIngredient of(Tag<Fluid> tag) {
		return new TagMatch(tag, MINAMOUNT);
	}

	public static FluidIngredient of(Tag<Fluid> tag, int amount) {
		return new TagMatch(tag, amount);
	}

	public abstract boolean testFluid(Fluid fluid);

	public static class Empty extends FluidIngredient {
		public Empty() {
			super(0);
		}

		@Override
		public boolean testFluid(Fluid fluid) {
			return false;
		}
	}

	public static class FluidMatch extends FluidIngredient {
		private final List<Fluid> fluids;

		public FluidMatch(Fluid fluid, int amount) {
			this(Lists.newArrayList(fluid), amount);
		}

		public FluidMatch(List<Fluid> fluids, int amount) {
			super(amount);
			this.fluids = Collections.unmodifiableList(fluids);
		}

		@Override
		public boolean testFluid(Fluid fluid) {
			return this.fluids.stream().anyMatch(f -> f.isSame(fluid));
		}

		public List<Fluid> getFluids() {
			return fluids;
		}
	}

	public static class TagMatch extends FluidIngredient {
		private final Tag<Fluid> tag;

		public TagMatch(Tag<Fluid> tag, int amount) {
			super(amount);
			this.tag = tag;
		}

		@Override
		public boolean testFluid(Fluid fluid) {
			return this.tag.getValues().contains(fluid);
		}

		public Tag<Fluid> getTag() {
			return this.tag;
		}

	}
}
