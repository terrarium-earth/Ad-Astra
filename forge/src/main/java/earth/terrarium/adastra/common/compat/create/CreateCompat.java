package earth.terrarium.adastra.common.compat.create;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreateCompat {
    public static final DeferredRegister<ArmInteractionPointType> REGISTRY = DeferredRegister.create(CreateRegistries.ARM_INTERACTION_POINT_TYPE, AdAstra.MOD_ID);

    public static final Supplier<ArmInteractionPointType> LAUNCH_PAD = REGISTRY.register("launch_pad", LaunchPadInteractionPoint::new);

    public static void init() {
        REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}