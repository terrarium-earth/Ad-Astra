package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, AdAstra.MOD_ID);
}

