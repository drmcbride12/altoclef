package adris.altoclef.mixins;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MultiPlayerGameMode.class)
public interface ClientPlayerInteractionAccessor {
    @Invoker("sendPlayerAction")
    void doSendPlayerAction(ServerboundPlayerActionPacket.Action action, BlockPos pos, Direction direction);

}
