package earth.terrarium.adastra.common.compat.create;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.resources.ResourceLocation;

public class CreateCompat {

    public static void init() {
        ArmInteractionPointType.register(new LaunchPadInteractionPoint(new ResourceLocation(AdAstra.MOD_ID, "launch_pad")));
    }

}