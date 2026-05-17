package adris.altoclef.tasks.entity;

import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;

/**
 * Kill all entities of a type
 */
public class KillEntitiesTask extends DoToClosestEntityTask {

    public KillEntitiesTask(Predicate<Entity> shouldKill, Class... entities) {
        super(KillEntityTask::new, shouldKill, entities);
    }

    public KillEntitiesTask(Class... entities) {
        super(KillEntityTask::new, entities);
    }
}
