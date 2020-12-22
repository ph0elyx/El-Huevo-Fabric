package com.github.Evoslab.elhuevo.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ElHuevoEntityRender extends MobEntityRenderer<ElHuevoEntity, ElHuevoEntityModel> {


    public ElHuevoEntityRender(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ElHuevoEntityModel(), 0.2f);
    }

    @Override
    public Identifier getTexture(ElHuevoEntity entity) {
        return new Identifier("elhuevo", "textures/entity/elhueve/elhueve.png");
    }
}
