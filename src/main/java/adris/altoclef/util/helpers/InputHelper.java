package adris.altoclef.util.helpers;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public class InputHelper {

    public static boolean isKeyPressed(int code) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), code);
    }
}
