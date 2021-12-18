package net.mrscauthd.beyond_earth.flag;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import net.minecraft.Util;
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
import net.mrscauthd.beyond_earth.ModInnet;

public class FlagTileEntity extends BlockEntity {
    @Nullable
    private static GameProfileCache profileCache;

    @Nullable
    private static MinecraftSessionService sessionService;

    @Nullable
    private GameProfile owner;

    @Nullable
    private static Executor mainThreadExecutor;

    public FlagTileEntity(BlockPos pos, BlockState state) {
        super(ModInnet.FLAG.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag p_187518_) {
        super.saveAdditional(p_187518_);
        if (this.owner != null) {
            CompoundTag compoundtag = new CompoundTag();
            NbtUtils.writeGameProfile(compoundtag, this.owner);
            p_187518_.put("FlagOwner", compoundtag);
        }

    }

    @Override
    public void load(CompoundTag p_155745_) {
        super.load(p_155745_);
        if (p_155745_.contains("FlagOwner", 10)) {
            this.setOwner(NbtUtils.readGameProfile(p_155745_.getCompound("FlagOwner")));
        } else if (p_155745_.contains("ExtraType", 8)) {
            String s = p_155745_.getString("ExtraType");
            if (!StringUtil.isNullOrEmpty(s)) {
                this.setOwner(new GameProfile((UUID)null, s));
            }
        }

    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public GameProfile getPlayerProfile() {
        return this.owner;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }


    public void setOwner(@Nullable GameProfile p_59770_) {
        synchronized(this) {
            this.owner = p_59770_;
        }

        this.updateOwnerProfile();
    }

    private void updateOwnerProfile() {
        updateGameprofile(this.owner, (p_155747_) -> {
            this.owner = p_155747_;
            this.setChanged();
        });
    }

    @Nullable
    public static void updateGameprofile(@Nullable GameProfile p_155739_, Consumer<GameProfile> p_155740_) {
        if (p_155739_ != null && !StringUtil.isNullOrEmpty(p_155739_.getName()) && (!p_155739_.isComplete() || !p_155739_.getProperties().containsKey("textures")) && profileCache != null && sessionService != null) {
            profileCache.getAsync(p_155739_.getName(), (p_182470_) -> {
                Util.backgroundExecutor().execute(() -> {
                    Util.ifElse(p_182470_, (p_182479_) -> {
                        Property property = Iterables.getFirst(p_182479_.getProperties().get("textures"), (Property)null);
                        if (property == null) {
                            p_182479_ = sessionService.fillProfileProperties(p_182479_, true);
                        }

                        GameProfile gameprofile = p_182479_;
                        mainThreadExecutor.execute(() -> {
                            profileCache.add(gameprofile);
                            p_155740_.accept(gameprofile);
                        });
                    }, () -> {
                        mainThreadExecutor.execute(() -> {
                            p_155740_.accept(p_155739_);
                        });
                    });
                });
            });
        } else {
            p_155740_.accept(p_155739_);
        }
    }
}