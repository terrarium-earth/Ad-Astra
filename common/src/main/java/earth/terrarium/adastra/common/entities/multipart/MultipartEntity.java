package earth.terrarium.adastra.common.entities.multipart;

import java.util.List;

public interface MultipartEntity {

    List<MultipartPartEntity<?>> getParts();
}
