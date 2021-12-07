package net.mrscauthd.boss_tools.flag;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;

import java.util.UUID;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.ModInnet;

public class FlagTileEntity extends BlockEntity {
    @Nullable
    private static GameProfileCache profileCache;

    @Nullable
    private static MinecraftSessionService sessionService;

    @Nullable
    private GameProfile playerProfile;

    @Nullable
    private static Executor mainThreadExecutor;

    public FlagTileEntity(BlockPos pos, BlockState state) {
        super(ModInnet.FLAG.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (this.playerProfile != null) {
            CompoundTag compoundnbt = new CompoundTag();
            NbtUtils.writeGameProfile(compoundnbt, this.playerProfile);
            compound.put("FlagOwner", compoundnbt);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("FlagOwner", 10)) {
            this.setPlayerProfile(NbtUtils.readGameProfile(nbt.getCompound("FlagOwner")));
        } else if (nbt.contains("ExtraType", 8)) {
            String s = nbt.getString("ExtraType");
            if (!StringUtil.isNullOrEmpty(s)) {
                this.setPlayerProfile(new GameProfile((UUID)null, s));
            }
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    public void setPlayerProfile(@Nullable GameProfile p_195485_1_) {
        this.playerProfile = p_195485_1_;
        this.updatePlayerProfile();
    }

    private void updatePlayerProfile() {
        this.playerProfile = updateGameProfile(this.playerProfile);
        this.setChanged();
    }

    @Nullable
    public static GameProfile updateGameProfile(@Nullable GameProfile input) {
        if (input != null && !StringUtil.isNullOrEmpty(input.getName())) {
            if (input.isComplete() && input.getProperties().containsKey("textures")) {
                return input;
            } else if (profileCache != null && sessionService != null) {
                GameProfile gameprofile = profileCache.get(input.getName()).get();
                if (gameprofile == null) {
                    return input;
                } else {
                    Property property = Iterables.getFirst(gameprofile.getProperties().get("textures"), (Property)null);
                    if (property == null) {
                        gameprofile = sessionService.fillProfileProperties(gameprofile, true);
                    }

                    return gameprofile;
                }
            } else {
                return input;
            }
        } else {
            return input;
        }
    }
}