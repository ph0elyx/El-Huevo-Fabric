package com.github.Evoslab.elhuevo;

import com.github.Evoslab.elhuevo.entity.ElHuevoEntityRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class ElhuevoClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Elhuevo.EL_HUEVE, (dispatcher, context) -> {
            return new ElHuevoEntityRender(dispatcher);
        });
    }
}
