package earth.terrarium.adastra.common.utils;

import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import earth.terrarium.adastra.common.config.AdAstraConfig;

public class ImageHostUtils {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
        "png", "jpeg", "jpg", "webp"
    );

    public static Set<String> getAllowedHosts() {
        return Arrays.stream(AdAstraConfig.allowedFlagHosts
            .split(",")
        ).map(String::trim).collect(Collectors.toUnmodifiableSet());
    }   

    public static Set<String> getAllowedExtensions() {
        return ImageHostUtils.ALLOWED_EXTENSIONS;
    }

    public static boolean isValidFlagImageURL(String url) {
        try {
            URI uri = URI.create(url);
            if (!"https".equals(uri.getScheme())) return false;
            if (getAllowedHosts().contains(uri.getHost())) return false;
            String path = uri.getPath().toLowerCase();
            return ALLOWED_EXTENSIONS.stream().anyMatch(ext -> path.endsWith("." + ext));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
