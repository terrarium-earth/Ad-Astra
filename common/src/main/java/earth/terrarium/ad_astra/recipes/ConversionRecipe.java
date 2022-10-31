package earth.terrarium.ad_astra.recipes;

import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

public abstract class ConversionRecipe extends ModRecipe {

    private final Fluid output;
    private final double conversionRatio;
    private HolderSet<Fluid> input;

    public ConversionRecipe(ResourceLocation id, HolderSet<Fluid> input, Fluid output, double conversionRatio) {
        super(id);
        this.input = input;
        this.output = output;
        this.conversionRatio = conversionRatio;
    }

    public ConversionRecipe(ResourceLocation id, Ingredient input, Fluid output, double conversionRatio) {
        super(id);
        this.inputs.add(input);
        this.output = output;
        this.conversionRatio = conversionRatio;
    }

    public boolean matches(Fluid input) {
        return this.input.contains(input.builtInRegistryHolder());
    }

    public HolderSet<Fluid> getFluidInput() {
        return this.input;
    }

    public Fluid getFluidOutput() {
        return this.output;
    }

    public double getConversionRatio() {
        return this.conversionRatio;
    }
}