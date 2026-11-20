package com.santiagogd.fastrender;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = FastRenderMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // ==================== RENDER DISTANCE ====================
    public static final ForgeConfigSpec.IntValue maxRenderDistance;

    // ==================== FUTURE (Fase 2) ====================
    // Por ahora solo dejamos el flag. Los perfiles de rendimiento
    // (Bajo / Medio / Alto / Extremo) se implementarán en Fase 2.
    // El usuario pidió que el perfil por defecto sea "Alto".
    public static final ForgeConfigSpec.BooleanValue enableAggressiveChunkLoading;

    static {
        BUILDER.push("Render Distance");

        maxRenderDistance = BUILDER
                .comment(
                        "Distancia de render máxima que el mod intentará aplicar.",
                        "Vanilla normalmente limita a 32. Valores altos (64-128) necesitan mucha RAM y un PC fuerte.",
                        "128 es extremo y puede causar lag o crashes si no tienes recursos suficientes."
                )
                .defineInRange("maxRenderDistance", 32, 2, 128);

        BUILDER.pop();

        BUILDER.push("Chunk Loading (Experimental - Fase 2)");

        enableAggressiveChunkLoading = BUILDER
                .comment(
                        "Si está activado, el mod intentará cargar chunks de forma más agresiva.",
                        "Esta funcionalidad se desarrollará completamente en la Fase 2.",
                        "Por defecto estará orientado a rendimiento Alto."
                )
                .define("enableAggressiveChunkLoading", true);

        BUILDER.pop();
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Aquí se pueden aplicar cambios cuando se recarga el config
    }
}
