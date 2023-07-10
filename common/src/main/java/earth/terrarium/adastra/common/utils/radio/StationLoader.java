package earth.terrarium.adastra.common.utils.radio;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.utils.WebUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StationLoader {

    private static final Codec<List<StationInfo>> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        StationInfo.CODEC.listOf().fieldOf("stations").forGetter(Function.identity())
    ).apply(instance, Function.identity()));

    private static final List<StationInfo> STATIONS = new ArrayList<>();

    public static void init() {
        JsonObject object = WebUtils.getJson("https://gist.githubusercontent.com/ThatGravyBoat/12d552eb254bd8c007603d046b9b6024/raw/b8a3bf312a065a41237434321d1fab969329c385/stations.json");
        if (object == null) return;
        STATIONS.clear();
        CODEC.parse(JsonOps.INSTANCE, object)
            .result()
            .ifPresent(STATIONS::addAll);
    }

    public static List<StationInfo> stations() {
        return STATIONS;
    }

    public static boolean hasStation(String url) {
        return STATIONS.stream().anyMatch(station -> station.url().equals(url));
    }
}
