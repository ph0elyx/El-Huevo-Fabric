package com.github.Evoslab.elhuevo;


import com.github.Evoslab.elhuevo.entity.ElHuevoEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Elhuevo implements ModInitializer {

	public static final String MODID = "elhuevo";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final EntityType<ElHuevoEntity> EL_HUEVE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("elhuevo", "elhueve"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ElHuevoEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());


	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(EL_HUEVE, ElHuevoEntity.createMobAttributes());
	}
	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}