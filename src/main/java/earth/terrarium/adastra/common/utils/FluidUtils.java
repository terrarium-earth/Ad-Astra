package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.common_storage_lib.context.impl.IsolatedSlotContext;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.item.ItemResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class FluidUtils {

    public static ResourceStack<FluidResource> getTank(ItemStack stack) {
        return getTank(stack, 0);
    }

    public static ResourceStack<FluidResource> getTank(ItemStack stack, int tank) {
        var container = new ModifyOnlyContext(stack).find(FluidApi.ITEM);

        if (container == null) return ResourceStack.EMPTY_FLUID;
        return container.getContents(tank);
    }

    public static boolean hasFluid(ItemStack stack) {
        return hasFluid(stack, 0);
    }

    public static boolean hasFluid(ItemStack stack, int tank) {
        return !getTank(stack, tank).isEmpty();
    }

    public static long getTankCapacity(ItemStack stack) {
        return getTankCapacity(stack, 0);
    }

    public static long getTankCapacity(ItemStack stack, int tank) {
        var container = new ModifyOnlyContext(stack).find(FluidApi.ITEM);
        if (container == null) return 0;
        return container.getLimit(tank, FluidResource.BLANK);
    }

    public static ItemStack fluidFilledItem(RegistryEntry<Item> item, RegistryEntry<? extends Fluid> fluid) {
        ModifyOnlyContext itemContext = new ModifyOnlyContext(item.get().getDefaultInstance());
        var container = itemContext.find(FluidApi.ITEM);
        if (container == null) return ItemStack.EMPTY;
        FluidResource resource = FluidResource.of(fluid.get());
        container.insert(resource, container.getLimit(0, resource), false);
        return itemContext.stack();
    }

    /**
     * Moves fluid from a stack to a fluid container
     *
     * @param container      The container that has the stack
     * @param fluidContainer The fluid container to move the fluid to
     * @param slot           The slot of the stack
     * @param resultSlot     The slot where the emptied stack will be moved to if successful
     * @param tank           The fluid container tank to insert the fluid to.
     */
    public static void moveItemToContainer(Container container, CommonStorage<FluidResource> fluidContainer, int slot, int resultSlot, int tank) {
        var stack = container.getItem(slot);
        ModifyOnlyContext itemContext = new ModifyOnlyContext(stack.copyWithCount(1));
        // Don't do anything if the stack is empty or doesn't contain a fluid container.
        if (stack.isEmpty() || !itemContext.isPresent(FluidApi.ITEM)) return;
        var resultStack = container.getItem(resultSlot);

        // Get the fluid container from the item
        var itemFluidContainer = itemContext.find(FluidApi.ITEM);
        if (itemFluidContainer == null) return;
        var fluidStack = itemFluidContainer.getContents(tank);
        if (fluidStack.isEmpty()) return;

        // Don't do anything if the resulting emptied stack can not be moved to the result slot.
        if (!resultStack.isEmpty()) {
            var emptyStack = getEmptyStack(new ResourceStack<>(ItemResource.of(stack), 1), fluidStack);
            if (!ItemUtils.canAddItem(emptyStack, resultStack)) return;
        }

        // Move the fluid from the item to the container
        if (TransferUtil.move(itemFluidContainer, fluidContainer, fluidStack.resource(), fluidStack.amount(), true) == 0)
            return;
        TransferUtil.move(itemFluidContainer, fluidContainer, fluidStack.resource(), fluidStack.amount(), false);

        var result = itemContext.stack();

        if (resultStack.isEmpty()) {
            // If the result slot is empty, move the item there.
            stack.shrink(1);
            container.setItem(resultSlot, result);
        } else if (ItemUtils.canAddItem(result, resultStack)) {
            // Or else increment the result slot.
            stack.shrink(1);
            resultStack.grow(1);
        }

        container.setChanged();
    }

    /**
     * Moves fluid from a fluid container to a stack
     *
     * @param container      The container that has the stack
     * @param fluidContainer The fluid container to move the fluid from
     * @param slot           The slot of the stack
     * @param resultSlot     The slot where the filled stack will be moved to if successful
     * @param tank           The fluid container tank to extract the fluid from
     */
    public static void moveContainerToItem(Container container, CommonStorage<FluidResource> fluidContainer, int slot, int resultSlot, int tank) {
        var stack = container.getItem(slot);
        ModifyOnlyContext itemContext = new ModifyOnlyContext(stack.copyWithCount(1));
        // Don't do anything if the stack is empty or doesn't contain a fluid container.
        if (stack.isEmpty() || !itemContext.isPresent(FluidApi.ITEM)) return;
        var resultStack = container.getItem(resultSlot);

        // Get the fluid container from the item
        var itemFluidContainer = itemContext.find(FluidApi.ITEM);
        if (itemFluidContainer == null) return;
        var fluidStack = fluidContainer.getContents(tank);
        if (fluidStack.isEmpty()) return;

        // Don't do anything if the resulting filled stack can not be moved to the result slot.
        if (!resultStack.isEmpty()) {
            var filledStack = getFilledStack(new ResourceStack<>(itemContext.getResource(), 1), fluidStack);
            if (!ItemUtils.canAddItem(filledStack, resultStack)) return;
        }

        if (TransferUtil.move(fluidContainer, itemFluidContainer, fluidStack.resource(), fluidStack.amount(), true) == 0)
            return;
        TransferUtil.move(fluidContainer, itemFluidContainer, fluidStack.resource(), fluidStack.amount(), false);

        var result = itemContext.stack();

        if (resultStack.isEmpty()) {
            // If the result slot is empty, move the item there.
            stack.shrink(1);
            container.setItem(resultSlot, result);
        } else if (ItemUtils.canAddItem(result, resultStack)) {
            // Or else increment the result slot.
            stack.shrink(1);
            resultStack.grow(1);
        }
    }

    /**
     * Gets the filled version of an item container by simulating the insertion of the fluid and returning the result stack.
     *
     * @param emptyStack The empty item stack
     * @param amount     The fluid to insert
     * @return The filled item stack
     */
    public static ItemStack getFilledStack(ResourceStack<ItemResource> emptyStack, ResourceStack<FluidResource> amount) {
        var copy = emptyStack.resource().toStack();
        IsolatedSlotContext itemContext = new IsolatedSlotContext(copy);
        var container = itemContext.find(FluidApi.ITEM);
        if (container == null) return ItemStack.EMPTY;
        container.insert(amount.resource(), amount.amount(), false);
        return itemContext.mainSlot().toItemStack();
    }

    /**
     * Gets the empty version of an item container by simulating the extraction of the fluid and returning the result stack.
     *
     * @param filledStack The filled item stack
     * @param amount      The fluid to extract
     * @return The empty item stack
     */
    public static ItemStack getEmptyStack(ResourceStack<ItemResource> filledStack, ResourceStack<FluidResource> amount) {
        var copy = filledStack.resource().toStack();
        IsolatedSlotContext itemContext = new IsolatedSlotContext(copy);
        var container = itemContext.find(FluidApi.ITEM);
        if (container == null) return ItemStack.EMPTY;
        container.extract(amount.resource(), amount.amount(), false);
        return itemContext.mainSlot().toItemStack();
    }
}