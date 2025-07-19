package com.lypaka.lypakautils.Handlers;

import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public class BiomeHandler {

    public static List<String> ALL_BIOMES = new ArrayList<>();

    public static void load() {

        if (!ALL_BIOMES.isEmpty()) return; // prevents loading this shit more than once in the event more than one sidemod calls this method
        Registries.BIOME_SOURCE.getIds().forEach(biome -> {

            if (!ALL_BIOMES.contains(biome.toString())) ALL_BIOMES.add(biome.toString());

        });

    }

}
