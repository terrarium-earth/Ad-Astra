package earth.terrarium.adastra.common.compat.create;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class CreateCompat {

    public static void init() {
        Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE,
            new ResourceLocation(AdAstra.MOD_ID, "launch_pad"),
            new LaunchPadInteractionPoint());
    }

}