package adris.altoclef.util.serialization;

import java.util.Arrays;
import java.util.Collection;
import net.minecraft.world.level.ChunkPos;

public class ChunkPosSerializer extends AbstractVectorSerializer<ChunkPos> {
    @Override
    protected Collection<String> getParts(ChunkPos value) {
        return Arrays.asList("" + value.x(), "" + value.z());
    }
}
