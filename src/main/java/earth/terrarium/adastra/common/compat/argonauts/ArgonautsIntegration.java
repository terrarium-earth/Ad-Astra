package earth.terrarium.adastra.common.compat.argonauts;

import com.mojang.authlib.GameProfile;
import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfoUtils;
import earth.terrarium.argonauts.api.client.guild.GuildClientApi;
import earth.terrarium.argonauts.api.client.party.PartyClientApi;
import earth.terrarium.argonauts.api.guild.Guild;
import earth.terrarium.argonauts.api.party.Party;
import earth.terrarium.argonauts.common.handlers.base.members.Member;

import java.util.List;
import java.util.UUID;

public class ArgonautsIntegration {

    public static boolean argonautsLoaded() {
        return ModInfoUtils.isModLoaded("argonauts");
    }

    public static List<GameProfile> getClientPartyMembers(UUID player) {
        Party party = PartyClientApi.API.getPlayerParty(player);
        if (party == null) return List.of();
        return party.members().allMembers().stream().map(Member::profile).toList();
    }

    public static List<GameProfile> getClientGuildMembers(UUID player) {
        Guild guild = GuildClientApi.API.getPlayerGuild(player);
        if (guild == null) return List.of();
        return guild.members().allMembers().stream().map(Member::profile).toList();
    }
}
