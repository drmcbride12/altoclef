package adris.altoclef.mixins;

import adris.altoclef.eventbus.EventBus;
import adris.altoclef.eventbus.events.BlockPlaceEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class WorldBlockModifiedMixin {

    private static boolean hasBlock(Level level, BlockState state, BlockPos pos) {
        return !state.isAir() && state.isRedstoneConductor(level, pos);
    }

    @Inject(
            method = "setBlocksDirty",
            at = @At("TAIL")
    )
    public void onBlockWasChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci) {
        Level level = (Level) (Object) this;
        if (!level.isClientSide() || Minecraft.getInstance().level != level) {
            return;
        }
        if (!hasBlock(level, oldBlock, pos) && hasBlock(level, newBlock, pos)) {
            BlockPlaceEvent evt = new BlockPlaceEvent(pos, newBlock);
            EventBus.publish(evt);
        }
    }
}
