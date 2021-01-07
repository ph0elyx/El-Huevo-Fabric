package com.github.Evoslab.elhuevo;

import com.github.Evoslab.elhuevo.misc.ElhuevoSpawner;
import net.minecraft.world.gen.Spawner;

import java.util.List;

public class MixinHooks {

    public static void serverWorldSpawnerHook(List<Spawner> spawnerList) {
        for (int i = 0; i < 20; i++)
            Elhuevo.LOGGER.debug("This piece of code has been called! Amazing, the mixin works");

        if (spawnerList != null) {
            Spawner spawner = new ElhuevoSpawner();
            /*
            Just to ensure the spawner list does not
            already contain an instance of our spawner.
             */
            if (spawnerList.stream().noneMatch((s) -> s instanceof ElhuevoSpawner))
                spawnerList.add(spawner);
        }
    }
}
