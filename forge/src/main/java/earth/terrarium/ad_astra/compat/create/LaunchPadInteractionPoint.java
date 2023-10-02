package earth.terrarium.ad_astra.compat.create;

import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.content.trains.schedule.condition.LazyTickedScheduleCondition;
import earth.terrarium.ad_astra.common.block.launchpad.LaunchPad;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LaunchPadInteractionPoint extends ArmInteractionPointType {

    public LaunchPadInteractionPoint(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean canCreatePoint(Level level, BlockPos blockPos, BlockState blockState) {
        return blockState.is(ModBlocks.LAUNCH_PAD.get());
    }

    @Nullable
    @Override
    public ArmInteractionPoint createPoint(Level level, BlockPos blockPos, BlockState blockState) {
        return new Point(this, level, blockPos, blockState);
    }

    public static class ItemHandler implements IItemHandler {
        private final Rocket rocket;

        public ItemHandler(Rocket rocket) {
            this.rocket = rocket;
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int i) {
            return ItemStack.EMPTY;
        }

        private Optional<FluidHolder> getFuel(ItemStack arg) {
            return FluidHooks.safeGetItemFluidManager(arg)
                    .map(it -> it.getFluidInTank(0))
                    .filter(it -> it.is(ModTags.FUELS));
        }

        @Override
        public @NotNull ItemStack insertItem(int i, @NotNull ItemStack arg, boolean bl) {
            if (!isItemValid(i, arg)) return arg;
            getFuel(arg).ifPresent(fluid -> rocket.getTank().insertFluid(fluid, bl));
            return arg.getCraftingRemainingItem();
        }

        @Override
        public @NotNull ItemStack extractItem(int i, int j, boolean bl) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int i) {
            return 1;
        }

        @Override
        public boolean isItemValid(int i, @NotNull ItemStack arg) {
            return arg.getCount() == 1 && getFuel(arg)
                    .filter(it -> it.getFluidAmount() + rocket.getTankAmount() <= rocket.getTankSize())
                    .isPresent();
        }
    }

    public static class Point extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {

        private Optional<Rocket> locateRocket() {
            if (!(cachedState.getBlock() instanceof LaunchPad pad)) return Optional.empty();
            var center = pad.getMainPos(cachedState, pos);
            return level.getEntitiesOfClass(Rocket.class, new AABB(center).inflate(1.0), Rocket::hasLaunchPad)
                    .stream()
                    .findAny();
        }

        public Point(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        public void updateCachedState() {
            super.updateCachedState();
            cachedHandler = locateRocket()
                    .map(ItemHandler::new)
                    .map(it -> LazyOptional.<IItemHandler>of(() -> it))
                    .orElseGet(LazyOptional::empty);
        }
    }

}
