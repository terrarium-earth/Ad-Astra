package earth.terrarium.adastra.common.config.info;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;
import org.intellij.lang.annotations.Subst;

public class AdAstraConfigLinks {

    public static final ResourcefulConfigLink GITHUB = create(Type.GITHUB, "https://github.com/terrarium-earth/Ad-Astra");
    public static final ResourcefulConfigLink DISCORD = create(Type.DISCORD, "https://discord.gg/terrarium");
    public static final ResourcefulConfigLink MODRINTH = create(Type.MODRINTH, "https://modrinth.com/mod/ad-astra");
    public static final ResourcefulConfigLink CURSEFORGE = create(Type.CURSEFORGE, "https://www.curseforge.com/minecraft/mc-mods/ad-astra");

    public static final ResourcefulConfigLink[] LINKS = new ResourcefulConfigLink[] {
        DISCORD,
        GITHUB,
        MODRINTH,
        CURSEFORGE
    };

    private static ResourcefulConfigLink create(
        Type type,
        @Subst("https://example.com") String url
    ) {
        return ResourcefulConfigLink.create(
            url,
            type.icon(),
            type.text()
        );
    }

    private enum Type {
        GITHUB,
        DISCORD,
        MODRINTH,
        CURSEFORGE;

        public String icon() {
            return switch (this) {
                case GITHUB -> "github";
                case DISCORD -> "gamepad-2";
                case MODRINTH -> "modrinth";
                case CURSEFORGE -> "curseforge";
            };
        }

        public TranslatableValue text() {
            return new TranslatableValue(
                switch (this) {
                    case GITHUB -> "GitHub";
                    case DISCORD -> "Discord";
                    case MODRINTH -> "Modrinth";
                    case CURSEFORGE -> "CurseForge";
                },
                switch (this) {
                    case GITHUB -> "config.ad_astra.links.github";
                    case DISCORD -> "config.ad_astra.links.discord";
                    case MODRINTH -> "config.ad_astra.links.modrinth";
                    case CURSEFORGE -> "config.ad_astra.links.curseforge";
                }
            );
        }
    }
}
