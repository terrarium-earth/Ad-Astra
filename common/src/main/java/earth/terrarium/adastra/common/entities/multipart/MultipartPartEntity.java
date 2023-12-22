package earth.terrarium.adastra.common.entities.multipart;

import net.minecraft.world.entity.Entity;

public interface MultipartPartEntity<T extends Entity & MultipartEntity> {

    T getParent();
}
