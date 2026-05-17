package adris.altoclef.eventbus.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBrokenEvent {
    public BlockPos blockPos;
    public BlockState blockState;
    public Player player;
}
