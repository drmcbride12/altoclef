package adris.altoclef.mixins;

import adris.altoclef.eventbus.EventBus;
import adris.altoclef.eventbus.events.ChatMessageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;


@Mixin(ChatComponent.class)
public final class ChatReadMixin {

    @Inject(
            method = "addClientSystemMessage",
            at = @At("HEAD")
    )
    private void onClientSystemMessage(Component message, CallbackInfo ci) {
        EventBus.publish(new ChatMessageEvent(null, message));
    }

    @Inject(
            method = "addServerSystemMessage",
            at = @At("HEAD")
    )
    private void onServerSystemMessage(Component message, CallbackInfo ci) {
        EventBus.publish(new ChatMessageEvent(null, message));
    }

    @Inject(
            method = "addPlayerMessage",
            at = @At("HEAD")
    )
    private void onPlayerMessage(Component message, MessageSignature signature, GuiMessageTag tag, CallbackInfo ci) {
        EventBus.publish(new ChatMessageEvent(null, message));
    }
}
