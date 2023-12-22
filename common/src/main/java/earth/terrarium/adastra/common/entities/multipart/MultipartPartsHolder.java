package earth.terrarium.adastra.common.entities.multipart;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;

public interface MultipartPartsHolder {

    Int2ObjectMap<Entity> adastra$getParts();
}
