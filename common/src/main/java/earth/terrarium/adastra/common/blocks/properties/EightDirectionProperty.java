package earth.terrarium.adastra.common.blocks.properties;


import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class EightDirectionProperty extends EnumProperty<EightDirectionProperty.Direction> {

    public EightDirectionProperty() {
        super("facing", Direction.class, List.of(Direction.VALUES));
    }

    public enum Direction implements StringRepresentable {
        NORTH(new Vec3i(0, 0, -1)),
        NORTH_EAST(new Vec3i(1, 0, -1)),
        EAST(new Vec3i(1, 0, 0)),
        SOUTH_EAST(new Vec3i(1, 0, 1)),
        SOUTH(new Vec3i(0, 0, 1)),
        SOUTH_WEST(new Vec3i(-1, 0, 1)),
        WEST(new Vec3i(-1, 0, 0)),
        NORTH_WEST(new Vec3i(-1, 0, -1));

        private final Vec3i normal;

        Direction(Vec3i normal) {
            this.normal = normal;
        }

        public static final Direction[] VALUES = values();

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        public Vec3i normal() {
            return normal;
        }

        public Direction mirror(Mirror mirror) {
            return switch (mirror) {
                case NONE -> this;
                case FRONT_BACK -> rotate(Rotation.CLOCKWISE_180);
                case LEFT_RIGHT -> switch (this) {
                    case NORTH -> SOUTH;
                    case SOUTH -> NORTH;
                    case WEST -> WEST;
                    case EAST -> EAST;
                    case NORTH_EAST -> SOUTH_EAST;
                    case SOUTH_EAST -> NORTH_EAST;
                    case SOUTH_WEST -> NORTH_WEST;
                    case NORTH_WEST -> SOUTH_WEST;
                };
            };
        }

        public Direction rotate(Rotation rotation) {
            return switch (rotation) {
                case CLOCKWISE_180 -> switch (this) {
                    case NORTH -> SOUTH;
                    case SOUTH -> NORTH;
                    case EAST -> WEST;
                    case WEST -> EAST;
                    case NORTH_EAST -> SOUTH_WEST;
                    case SOUTH_WEST -> NORTH_EAST;
                    case NORTH_WEST -> SOUTH_EAST;
                    case SOUTH_EAST -> NORTH_WEST;
                };
                case COUNTERCLOCKWISE_90 -> switch (this) {
                    case NORTH -> NORTH_EAST;
                    case SOUTH -> SOUTH_WEST;
                    case EAST -> SOUTH_EAST;
                    case WEST -> NORTH_WEST;
                    case NORTH_EAST -> EAST;
                    case SOUTH_WEST -> WEST;
                    case NORTH_WEST -> NORTH;
                    case SOUTH_EAST -> SOUTH;
                };
                case CLOCKWISE_90 -> switch (this) {
                    case NORTH -> NORTH_WEST;
                    case SOUTH -> SOUTH_EAST;
                    case EAST -> NORTH_EAST;
                    case WEST -> SOUTH_WEST;
                    case NORTH_EAST -> NORTH;
                    case SOUTH_WEST -> SOUTH;
                    case NORTH_WEST -> WEST;
                    case SOUTH_EAST -> EAST;
                };
                default -> this;
            };
        }

        public int asRotation() {
            return switch (this) {
                case NORTH -> 0;
                case NORTH_WEST -> 45;
                case WEST -> 90;
                case SOUTH_WEST -> 135;
                case SOUTH -> 180;
                case SOUTH_EAST -> 225;
                case EAST -> 270;
                case NORTH_EAST -> 315;
            };
        }
    }
}