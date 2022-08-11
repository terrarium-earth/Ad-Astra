// package com.github.alexnijjar.beyond_earth.blocks.cables;

// import javax.annotation.Nullable;

// import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

// import net.minecraft.block.BlockState;
// import net.minecraft.block.entity.BlockEntity;
// import net.minecraft.nbt.NbtCompound;
// import net.minecraft.server.world.ServerWorld;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.Direction;
// import team.reborn.energy.api.EnergyStorage;
// import team.reborn.energy.api.base.SimpleSidedEnergyContainer;

// public class CableBlockEntity extends BlockEntity {

//     public CableBlockEntity(BlockPos pos, BlockState state) {
//         super(ModBlockEntities.CABLE, pos, state);
//     }

//     public final SimpleSidedEnergyContainer energyStorage = new SimpleSidedEnergyContainer() {
//         @Override
//         public long getCapacity() {
//             return getMaxGeneration();
//         }

//         @Override
//         public long getMaxInsert(@Nullable Direction side) {
//             return getMaxEnergyInsert();
//         }

//         @Override
//         public long getMaxExtract(@Nullable Direction side) {
//             return getMaxEnergyExtract();
//         }

//         @Override
//         protected void onFinalCommit() {
//             markDirty();
//         }
//     };

//     public EnergyStorage getSideEnergyStorage(@Nullable Direction side) {
//         return this.energyStorage.getSideStorage(side);
//     }

//     @Override
//     public void readNbt(NbtCompound nbt) {
//         super.readNbt(nbt);
//         this.energyStorage.amount = nbt.getLong("energy");
//     }

//     @Override
//     public void writeNbt(NbtCompound nbt) {
//         super.writeNbt(nbt);
//         nbt.putLong("energy", energyStorage.amount);
//     }

//     public long getMaxGeneration() {
//         return 0;
//     }

//     public long getMaxEnergyInsert() {
//         return 0;
//     }

//     public long getMaxEnergyExtract() {
//         return 0;
//     }

//     @Override
//     public void markDirty() {
//         super.markDirty();

//         if (this.world instanceof ServerWorld world) {
//             world.getChunkManager().markForUpdate(this.pos);
//         }
//     }

//     public void tick() {
//         if (!world.isClient) {

//         }
//     }
// }
