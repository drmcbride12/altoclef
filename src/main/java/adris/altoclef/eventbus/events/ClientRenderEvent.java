package adris.altoclef.eventbus.events;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public class ClientRenderEvent {
    public GuiGraphicsExtractor graphics;

    public ClientRenderEvent(GuiGraphicsExtractor graphics) {
        this.graphics = graphics;
    }
}
