// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.16
// Paste this class into your mod and generate all required imports

package com.github.Evoslab.elhuevo.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ElHuevoEntityModel extends EntityModel<ElHuevoEntity> {
    private final ModelPart leftleg;
    private final ModelPart rightleg;
    private final ModelPart bodyhead;
    private final ModelPart tail;

    private final ModelPart bb_main;
    public ElHuevoEntityModel() {
        textureWidth = 32;
        textureHeight = 32;
        leftleg = new ModelPart(this);
        leftleg.setPivot(-1.5F, 22.5F, 0.5F);
        leftleg.setTextureOffset(22, 6).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        rightleg = new ModelPart(this);
        rightleg.setPivot(1.5F, 22.5F, 0.5F);
        rightleg.setTextureOffset(22, 9).addCuboid(-1.0F, 0.5F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        bodyhead = new ModelPart(this);
        bodyhead.setPivot(0.0F, 18.4F, -0.2F);
        bodyhead.setTextureOffset(26, 3).addCuboid(2.0F, -4.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bodyhead.setTextureOffset(26, 0).addCuboid(-4.0F, -4.4F, 0.2F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bodyhead.setTextureOffset(0, 13).addCuboid(-2.0F, -0.4F, -2.8F, 4.0F, 2.0F, 1.0F, 0.0F, false);
        bodyhead.setTextureOffset(0, 0).addCuboid(-3.0F, -3.4F, -1.8F, 6.0F, 8.0F, 5.0F, 0.0F, false);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, 3.6F, 2.7F);
        bodyhead.addChild(tail);
        tail.setTextureOffset(17, 0).addCuboid(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 18).addCuboid(-3.0F, -9.0F, -2.0F, 6.0F, 8.0F, 5.0F, 0.1F, false);
    }

    @Override
    public void setAngles(ElHuevoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }
    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

        leftleg.render(matrixStack, buffer, packedLight, packedOverlay);
        rightleg.render(matrixStack, buffer, packedLight, packedOverlay);
        bodyhead.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    @Override
    public void animateModel(ElHuevoEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
    }

}