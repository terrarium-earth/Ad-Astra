package earth.terrarium.adastra.common.config.info;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColor;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;

public class AdAstraConfigInfo implements ResourcefulConfigInfo {

    public static final TranslatableValue TITLE = new TranslatableValue(
        "Ad Astra",
        "config.ad_astra.title"
    );

    public static final TranslatableValue TITLE_CLIENT = new TranslatableValue(
        "Ad Astra Client",
        "config.ad_astra.client.title"
    );

    public static final TranslatableValue DESCRIPTION = new TranslatableValue(
        "Live long and prosper, and may the force be with you.",
        "config.ad_astra.description"
    );

    private final boolean isClient;

    public AdAstraConfigInfo(String id) {
        this.isClient = id.endsWith("-client");
    }

    @Override
    public TranslatableValue title() {
        return isClient ? TITLE_CLIENT : TITLE;
    }

    @Override
    public TranslatableValue description() {
        return DESCRIPTION;
    }

    @Override
    public String icon() {
        return "planet";
    }

    @Override
    public ResourcefulConfigColor color() {
        return AdAstraConfigColor.INSTANCE;
    }

    @Override
    public ResourcefulConfigLink[] links() {
        return AdAstraConfigLinks.LINKS;
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
