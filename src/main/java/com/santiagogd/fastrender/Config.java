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

    // ==================== FASE 2 - CHUNK LOADING ====================
    public static final ForgeConfigSpec.ConfigValue<String> performanceProfile;
    public static final ForgeConfigSpec.BooleanValue enableAggressiveChunkLoading;
    public static final ForgeConfigSpec.IntValue extraChunkBuffer;
    public static final ForgeConfigSpec.IntValue chunksPerTickMultiplier;

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

        BUILDER.push("Chunk Loading - Fase 2");

        performanceProfile = BUILDER
                .comment(
                        "Perfil de rendimiento para la carga de chunks.",
                        "Opciones válidas: low, medium, high, extreme", 
                        "Por defecto: high (carga más agresiva de chunks)"
                )
                .define("performanceProfile", "high");

        enableAggressiveChunkLoading = BUILDER
                .comment("Activa o desactiva todo el sistema de carga agresiva de chunks.")
                .define("enableAggressiveChunkLoading", true);

        extraChunkBuffer = BUILDER
                .comment(
                        "Chunks extra que se intentarán mantener cargados alrededor del jugador (buffer oculto).",
                        "Valores más altos = menos pop-in pero más uso de CPU/RAM."
                )
                .defineInRange("extraChunkBuffer", 8, 0, 32);

        chunksPerTickMultiplier = BUILDER
                .comment(
                        "Multiplicador de cuántos chunks se procesan por tick (1 = vanilla, 2 = el doble, etc.).",
                        "Valores muy altos pueden causar lag de CPU."
                )
                .defineInRange("chunksPerTickMultiplier", 2, 1, 6);

        BUILDER.pop();
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();

    /**
     * Devuelve el multiplicador de chunks por tick según el perfil actual.
     */
    public static int getEffectiveChunksPerTickMultiplier() {
        if (!enableAggressiveChunkLoading.get()) return 1;

        return switch (performanceProfile.get().toLowerCase()) {
            case "low" -> 1;
            case "medium" -> 2;
            case "high" -> 3;
            case "extreme" -> 5;
            default -> chunksPerTickMultiplier.get();
        };
    }

    /**
     * Devuelve el buffer extra de chunks según el perfil.
     */
    public static int getEffectiveExtraBuffer() {
        if (!enableAggressiveChunkLoading.get()) return 0;

        return switch (performanceProfile.get().toLowerCase()) {
            case "low" -> 4;
            case "medium" -> 6;
            case "high" -> 10;
            case "extreme" -> 16;
            default -> extraChunkBuffer.get();
        };
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Se puede reaccionar a cambios de config aquí
    }
}
