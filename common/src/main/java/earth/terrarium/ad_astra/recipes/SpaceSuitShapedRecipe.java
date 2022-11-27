package earth.terrarium.ad_astra.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import earth.terrarium.ad_astra.items.FluidContainingItem;
import earth.terrarium.ad_astra.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.material.Fluid;

public class SpaceSuitShapedRecipe extends ShapedRecipe {

    public SpaceSuitShapedRecipe(ShapedRecipe internal) {
        super(internal.getId(), internal.getGroup(), internal.getWidth(), internal.getHeight(),
                internal.getIngredients(), internal.getResultItem());
    }

    public static Consumer<FinishedRecipe> wrap(Consumer<FinishedRecipe> exporter) {
        return r -> exporter.accept(new WrappedFinishedRecipe(r));
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack assemble = super.assemble(inv).copy();
        CompoundTag assemblingTag = null;
        Map<Fluid, FluidHolder> assemblingOxygens = new HashMap<>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item.isEmpty()) {
                continue;
            } else if (item.getItem() instanceof ArmorItem) {
                assemblingTag = item.getTag();
            } else {
                FluidHooks.safeGetItemFluidManager(item).ifPresent(fluidManager -> {
                    this.mergeOxygen(assemblingOxygens, fluidManager.getFluidInTank(0));
                });
            }
        }

        if (assemblingTag != null) {
            assemble.setTag(assemblingTag.copy());
        }

        if (assemblingOxygens.size() > 0 && assemble.getItem() instanceof FluidContainingItem fluidContaining) {
            ItemStackHolder itemHolder = new ItemStackHolder(assemble);

            Fluid primaryFluid = assemblingOxygens.entrySet().stream().max(this::compareAmount).map(e -> e.getKey()).orElse(null);
            long totalOxygen = assemblingOxygens.values().stream().collect(Collectors.summingLong(e -> e.getFluidAmount()));
            fluidContaining.insert(itemHolder, FluidHooks.newFluidHolder(primaryFluid, totalOxygen, null));

            if (itemHolder.isDirty()) assemble = itemHolder.getStack();
        }

        return assemble.copy();
    }

    @SuppressWarnings("deprecation")
    private void mergeOxygen(Map<Fluid, FluidHolder> map, FluidHolder merging) {
        Fluid fluid = merging.getFluid();
        if (fluid.is(ModTags.OXYGEN)) {
            FluidHolder current = map.computeIfAbsent(fluid, f -> FluidHooks.newFluidHolder(f, 0L, null));
            current.setAmount(current.getFluidAmount() + merging.getFluidAmount());
        }
    }

    private int compareAmount(Entry<Fluid, FluidHolder> entry1, Entry<Fluid, FluidHolder> entry2) {
        return Long.compare(entry1.getValue().getFluidAmount(), entry2.getValue().getFluidAmount());
    }

    public static class WrappedFinishedRecipe implements FinishedRecipe {

        private final FinishedRecipe internal;

        public WrappedFinishedRecipe(FinishedRecipe internal) {
            this.internal = internal;
        }

        public FinishedRecipe getInternal() {
            return this.internal;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.getInternal().getAdvancementId();
        }

        @Override
        public ResourceLocation getId() {
            return this.getInternal().getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.SPACE_SUIT_CRAFTING_SERIALIZER.get();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.getInternal().serializeAdvancement();
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            this.getInternal().serializeRecipeData(json);
        }
    }

    public static class Serializer implements RecipeSerializer<SpaceSuitShapedRecipe> {

        @Override
        public SpaceSuitShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            return new SpaceSuitShapedRecipe(this.getWrappedSerializer().fromJson(recipeId, json));
        }

        @Override
        public SpaceSuitShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            return new SpaceSuitShapedRecipe(this.getWrappedSerializer().fromNetwork(recipeId, buffer));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpaceSuitShapedRecipe recipe) {
            this.getWrappedSerializer().toNetwork(buffer, recipe);
        }

        private RecipeSerializer<ShapedRecipe> getWrappedSerializer() {
            return RecipeSerializer.SHAPED_RECIPE;
        }
    }
}
