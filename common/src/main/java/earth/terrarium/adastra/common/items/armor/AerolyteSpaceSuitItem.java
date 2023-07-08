package earth.terrarium.adastra.common.items.armor;

import earth.terrarium.adastra.common.items.base.CustomGeoArmorItem;
import earth.terrarium.adastra.common.registry.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Set;

public class AerolyteSpaceSuitItem extends CustomGeoArmorItem {

    public static final RawAnimation IDLE_CLOSED = RawAnimation.begin().thenLoop("animation.model.idle.closed");
    public static final RawAnimation IDLE_OPEN = RawAnimation.begin().thenLoop("animation.model.idle.open");
    public static final RawAnimation OPEN = RawAnimation.begin().thenPlayAndHold("animation.model.open");
    public static final RawAnimation CLOSE = RawAnimation.begin().thenPlayAndHold("animation.model.close");

    public AerolyteSpaceSuitItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {

            Entity entity = state.getData(DataTickets.ENTITY);
            if (!(entity instanceof LivingEntity livingEntity)) return PlayState.STOP;

            state.getController().setAnimation(livingEntity.isFallFlying() ? OPEN : CLOSE);

            if (entity instanceof ArmorStand)
                return PlayState.CONTINUE;

            Set<Item> wornArmor = new ObjectOpenHashSet<>();

            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }

            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
                ModItems.AEROLYTE_SPACE_HELMET.get(),
                ModItems.AEROLYTE_SPACE_SUIT.get(),
                ModItems.AEROLYTE_SPACE_PANTS.get(),
                ModItems.AEROLYTE_SPACE_BOOTS.get()));

            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }
}
