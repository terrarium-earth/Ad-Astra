package earth.terrarium.adastra.common.menus.content;

import com.teamresourceful.resourcefullib.common.menu.MenuContent;
import com.teamresourceful.resourcefullib.common.menu.MenuContentSerializer;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public record EntityContent(int entityId) implements MenuContent<EntityContent> {
    public static final MenuContentSerializer<EntityContent> SERIALIZER = new Serializer();

    @Override
    public MenuContentSerializer<EntityContent> serializer() {
        return SERIALIZER;
    }

    private static class Serializer implements MenuContentSerializer<EntityContent> {

        @Override
        public @Nullable EntityContent from(FriendlyByteBuf buffer) {
            return new EntityContent(buffer.readVarInt());
        }

        @Override
        public void to(FriendlyByteBuf buffer, EntityContent content) {
            buffer.writeVarInt(content.entityId);
        }
    }
}
