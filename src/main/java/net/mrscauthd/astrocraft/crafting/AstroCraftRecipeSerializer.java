package net.mrscauthd.astrocraft.crafting;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class AstroCraftRecipeSerializer<T extends Recipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {

}
