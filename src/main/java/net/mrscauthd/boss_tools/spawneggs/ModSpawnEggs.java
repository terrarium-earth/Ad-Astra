package net.mrscauthd.boss_tools.spawneggs;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModSpawnEggs extends SpawnEggItem {
    //Todo In 1.18 is it Replaced with the ForgeSpawnEgg
    protected static final List<ModSpawnEggs> UNADDED_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public ModSpawnEggs(final RegistryObject<? extends EntityType<?>> typeIn, int primaryColorIn, int secondaryColorIn, Properties builder) {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier = Lazy.of(typeIn::get);
        UNADDED_EGGS.add(this);
    }

    public static void initSpawnEggs(){
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,null, "field_195987_b");
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior(){
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                type.spawn(source.getWorld(),stack,null,source.getBlockPos().offset(direction), SpawnReason.DISPENSER,direction != Direction.UP,false);
                stack.shrink(1);
                return stack;
            }
        };

        for (final SpawnEggItem spawnEggItem : UNADDED_EGGS){
            EGGS.put(spawnEggItem.getType(null),spawnEggItem);
            DispenserBlock.registerDispenseBehavior(spawnEggItem,defaultDispenseItemBehavior);
        }
        UNADDED_EGGS.clear();
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT nbt) {
        return this.entityTypeSupplier.get();
    }
}