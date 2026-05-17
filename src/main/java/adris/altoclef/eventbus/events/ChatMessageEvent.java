package adris.altoclef.eventbus.events;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;

/**
 * Whenever chat appears
 */
public class ChatMessageEvent {
    public ChatType messageType;
    public Component message;

    public ChatMessageEvent(ChatType messageType, Component message) {
        this.messageType = messageType;
        this.message = message;
    }
}
