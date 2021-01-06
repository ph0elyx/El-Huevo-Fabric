package com.github.Evoslab.elhuevo.mixin;

import com.github.Evoslab.elhuevo.misc.ElhuevoSpawner;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @ModifyVariable(method = "<init>", at = @At(value = "FIELD", target = "net/minecraft/server/world/ServerWorld.spawners:Ljava/util/List;", shift = At.Shift.BEFORE), index = 12)
    private List<Spawner> changeSpawnersList(List<Spawner> spawnerList) {
        List<Spawner> newList = new ArrayList<>();
        newList.add(new ElhuevoSpawner());
        newList.addAll(spawnerList);
        spawnerList = newList;
        return spawnerList;
    }
}
