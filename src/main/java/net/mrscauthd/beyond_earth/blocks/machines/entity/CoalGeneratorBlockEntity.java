package net.mrscauthd.beyond_earth.blocks.machines.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.gui.screen_handlers.CoalGeneratorScreenHandler;
import net.mrscauthd.beyond_earth.recipes.GeneratingRecipe;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;
import net.mrscauthd.beyond_earth.registry.ModRecipeTypes;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlockEntity extends AbstractMachineBlockEntity {

    public static final long MAX_ENERGY = 9000L;
    public static final long ENERGY_PER_TICK = 2;

    private short burnTime;
    private short burnTimeTotal;

    public CoalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR_ENTITY, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CoalGeneratorScreenHandler(syncId, inv, this);
    }

    @Override
    public boolean useEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return MAX_ENERGY;
    }

    @Override
    public long getEnergyPerTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    public long getMaxEnergyExtract() {
        return ENERGY_PER_TICK * 2;
    }

    @Override
    public boolean hasInventory() {
        return true;
    }

    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.burnTime = nbt.getShort("BurnTime");
        this.burnTimeTotal = nbt.getShort("BurnTimeTotal");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", this.burnTime);
        nbt.putShort("BurnTimeTotal", this.burnTimeTotal);
    }

    public short getBurnTime() {
        return burnTime;
    }

    public short getBurnTimeTotal() {
        return burnTimeTotal;
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity blockEntity) {
        if (blockEntity.useEnergy()) {
            CoalGeneratorBlockEntity coalGeneratorBlockEntity = (CoalGeneratorBlockEntity) blockEntity;

            if (blockEntity.getEnergy() < blockEntity.getMaxGeneration()) {
                if (coalGeneratorBlockEntity.burnTime > 0) {
                    coalGeneratorBlockEntity.burnTime--;
                    blockEntity.cumulateEnergy();

                } else if (!blockEntity.getItems().get(0).isEmpty()) {

                    ItemStack input = blockEntity.getItems().get(0);
                    GeneratingRecipe recipe = world.getRecipeManager().getFirstMatch(ModRecipeTypes.GENERATING_RECIPE, blockEntity, world).orElse(null);

                    if (recipe != null) {
                        Item item = recipe.getInput();
                        if (input.getItem().equals(item)) {
                            input.decrement(1);
                            coalGeneratorBlockEntity.burnTime = recipe.getBurnTime();
                            coalGeneratorBlockEntity.burnTimeTotal = recipe.getBurnTime();
                        }
                    }
                }
            }
            blockEntity.energyOut();
        }
    }
}
