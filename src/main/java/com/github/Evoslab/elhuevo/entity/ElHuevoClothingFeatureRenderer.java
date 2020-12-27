package com.github.Evoslab.elhuevo.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ElHuevoClothingFeatureRenderer extends FeatureRenderer<ElHuevoEntity, ElHuevoEntityModel> {

    public static final Identifier SKIN_BLACK = new Identifier("elhuevo","textures/entity/elhueve/black.png");
    public static final Identifier SKIN_RED = new Identifier("elhuevo","textures/entity/elhueve/red.png");
    public static final Identifier SKIN_GREEN = new Identifier("elhuevo","textures/entity/elhueve/green.png");
    public static final Identifier SKIN_BROWN = new Identifier("elhuevo","textures/entity/elhueve/brown.png");
    public static final Identifier SKIN_BLUE = new Identifier("elhuevo","textures/entity/elhueve/blue.png");
    public static final Identifier SKIN_PURPLE = new Identifier("elhuevo","textures/entity/elhueve/purple.png");
    public static final Identifier SKIN_CYAN = new Identifier("elhuevo","textures/entity/elhueve/cyan.png");
    public static final Identifier SKIN_LIGHT_GRAY  = new Identifier("elhuevo","textures/entity/elhueve/light_gray.png");
    public static final Identifier SKIN_GRAY  = new Identifier("elhuevo","textures/entity/elhueve/gray.png");
    public static final Identifier SKIN_PINK  = new Identifier("elhuevo","textures/entity/elhueve/pink.png");
    public static final Identifier SKIN_LIME  = new Identifier("elhuevo","textures/entity/elhueve/lime.png");
    public static final Identifier SKIN_YELLOW  = new Identifier("elhuevo","textures/entity/elhueve/yellow.png");
    public static final Identifier SKIN_LIGHT_BLUE  = new Identifier("elhuevo","textures/entity/elhueve/light_blue.png");
    public static final Identifier SKIN_MAGENTA  = new Identifier("elhuevo","textures/entity/elhueve/magenta.png");
    public static final Identifier SKIN_ORANGE  = new Identifier("elhuevo","textures/entity/elhueve/orange.png");
    public static final Identifier SKIN_WHITE  = new Identifier("elhuevo","textures/entity/elhueve/white.png");

    public ElHuevoClothingFeatureRenderer(FeatureRendererContext<ElHuevoEntity, ElHuevoEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ElHuevoEntity elHuevoEntity, float f, float g, float h, float j, float k, float l) {
        if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.RED) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_RED, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.BLACK) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_BLACK, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.GREEN) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_GREEN, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.BROWN) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_BROWN, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.BLUE) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_BLUE, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.PURPLE) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_PURPLE, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.CYAN) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_CYAN, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.LIGHT_GRAY) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_LIGHT_GRAY, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.GRAY) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_GRAY, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.PINK) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_PINK, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.LIME) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_LIME, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.YELLOW) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_YELLOW, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.LIGHT_BLUE) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_LIGHT_BLUE, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.MAGENTA) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_MAGENTA, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.ORANGE) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_ORANGE, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        } else if (elHuevoEntity.isTamed() && !elHuevoEntity.isInvisible() && elHuevoEntity.getClothingColor() == DyeColor.WHITE) {
            float[] fs = elHuevoEntity.getClothingColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN_WHITE, matrixStack, vertexConsumerProvider, i, elHuevoEntity, fs[0], fs[1], fs[2]);
        }

    }

}
