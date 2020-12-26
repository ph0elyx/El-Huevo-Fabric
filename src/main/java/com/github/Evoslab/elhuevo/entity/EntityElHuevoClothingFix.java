package com.github.Evoslab.elhuevo.entity;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceFix;

public class EntityElHuevoClothingFix extends ChoiceFix {
    public EntityElHuevoClothingFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType, "EntityElHuevoClothingFix", TypeReferences.ENTITY, "elhueve:elhueve");
    }

    public Dynamic<?> fixClothingColor(Dynamic<?> dynamic) {
        return dynamic.update("ClothingColor", (dynamicx) -> {
            return dynamicx.createByte((byte)(15 - dynamicx.asInt(0)));
        });
    }

    protected Typed<?> transform(Typed<?> inputType) {
        return inputType.update(DSL.remainderFinder(), this::fixClothingColor);
    }
}
