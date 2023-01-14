package earth.terrarium.ad_astra.common.block.pipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface InteractablePipe<T> {

    boolean supportsAutoExtract();

    boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos);

    void insertInto(T consumer, Direction direction, BlockPos pos);

    T getInteraction(Level level, BlockPos pos, Direction direction);

    Node<T> getSource();

    void setSource(Node<T> source);

    void clearSource();

    List<Node<T>> getConsumers();

    Level getPipelevel();

    BlockPos getPipePos();

    int getWorkTime();

    long getTransferAmount();

    default void handleSourceFinding() {

    }

    default void pipeTick() {
        if (!getPipelevel().isClientSide()) {
            if (getPipelevel().getGameTime() % getWorkTime() == 0) {
                clearSource();
                this.getConsumers().clear();
                Set<BlockPos> visitedNodes = new HashSet<>();
                Set<BlockPos> availableNodes = new HashSet<>();
                if (supportsAutoExtract()) {
                    availableNodes.add(this.getPipePos());
                    for (Direction direction : Direction.values()) {
                        BlockPos offset = this.getPipePos().relative(direction);
                        T potentialSource = getInteraction(getPipelevel(), offset, direction);
                        if (potentialSource != null) {
                            this.setSource(new Node<>(potentialSource, direction, offset));
                            break;
                        }
                    }
                } else {
                    handleSourceFinding();
                }
                if (getSource() != null) {
                    while (!availableNodes.isEmpty()) {
                        List<BlockPos> temporaryOpenNodes = new ArrayList<>();

                        for (BlockPos node : availableNodes) {
                            BlockEntity current = getPipelevel().getBlockEntity(node);
                            for (Direction direction : Direction.values()) {
                                BlockPos offset = node.relative(direction);
                                BlockEntity entity = getPipelevel().getBlockEntity(offset);
                                if (!visitedNodes.contains(offset) && entity instanceof InteractablePipe<?> pipe) {
                                    // Additional if statement to optimize performance; if it's a pipe but can't connect, it shouldn't check if it is a consumer (because it's already a pipe)
                                    if (pipe.canConnectTo(current, direction, offset)) {
                                        temporaryOpenNodes.add(offset);
                                    }
                                } else {
                                    T potentialConsumer = getInteraction(getPipelevel(), offset, direction);
                                    if (potentialConsumer != null) {
                                        this.getConsumers().add(new Node<>(potentialConsumer, direction, node));
                                    }
                                }
                            }
                            visitedNodes.add(node);
                        }
                        availableNodes.clear();
                        availableNodes.addAll(temporaryOpenNodes);
                    }
                }
            } else if (getSource() != null && !getConsumers().isEmpty()) {
                for (Node<T> consumer : getConsumers()) {
                    insertInto(consumer.storage(), consumer.direction(), consumer.pos());
                }
            }
        }
    }

    record Node<T>(T storage, Direction direction, BlockPos pos) {
    }
}
