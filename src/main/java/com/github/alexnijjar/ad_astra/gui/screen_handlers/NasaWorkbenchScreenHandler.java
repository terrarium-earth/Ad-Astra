package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.NasaWorkbenchBlockEntity;
import com.github.alexnijjar.beyond_earth.recipes.NasaWorkbenchRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class NasaWorkbenchScreenHandler extends AbstractMachineScreenHandler {

    private ItemStack output = ItemStack.EMPTY;
    private List<Integer> stackCounts = new ArrayList<>();

    public NasaWorkbenchScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (NasaWorkbenchBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public NasaWorkbenchScreenHandler(int syncId, PlayerInventory inventory, NasaWorkbenchBlockEntity entity) {
        super(ModScreenHandlers.NASA_WORKBENCH_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Nose.
                new Slot(entity, 0, 40, 18),

                // Body.
                new Slot(entity, 1, 31, 18 * 2), //
                new Slot(entity, 2, 49, 18 * 2), //
                new Slot(entity, 3, 31, 18 * 3), //
                new Slot(entity, 4, 49, 18 * 3), //
                new Slot(entity, 5, 31, 18 * 4), //
                new Slot(entity, 6, 49, 18 * 4), //

                // Left fin.
                new Slot(entity, 7, 13, 18 * 5),

                // Tank.
                new Slot(entity, 8, 31, 18 * 5), //
                new Slot(entity, 9, 49, 18 * 5), //

                // Right fin.
                new Slot(entity, 10, 67, 18 * 5),

                // Left fin.
                new Slot(entity, 11, 13, 18 * 6),

                // Engine.
                new Slot(entity, 12, 40, 18 * 6),

                // Right fin.
                new Slot(entity, 13, 67, 18 * 6),

                // Output.
                new Slot(entity, 14, 133, 74) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                } });
        this.updateContent();
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 58;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        NasaWorkbenchBlockEntity entity = (NasaWorkbenchBlockEntity) blockEntity;

        if (slotIndex == 14) {
            if (!entity.getItems().get(14).isEmpty()) {
                entity.spawnResultParticles();
                entity.spawnOutputAndClearInput(this.stackCounts, this.output);
            }
        } else {
            super.onSlotClick(slotIndex, button, actionType, player);
        }
        this.updateContent();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.updateContent();
    }

    public void updateContent() {

        NasaWorkbenchRecipe recipe = ModRecipes.NASA_WORKBENCH_RECIPE.findFirst(world, f -> f.test(this.blockEntity));

        ItemStack output = ItemStack.EMPTY;
        if (recipe != null) {
            output = recipe.getOutput();
            this.stackCounts = recipe.getStackCounts();
        }
        this.output = output;
        this.blockEntity.setStack(14, output);
        this.updateToClient();
    }
}