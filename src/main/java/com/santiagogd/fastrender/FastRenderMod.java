package com.santiagogd.fastrender;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * FastRender
 * Fase 1: Render Distance alta (hasta 128)
 * Fase 2: Carga de chunks más agresiva + perfiles de rendimiento
 */
@Mod(FastRenderMod.MODID)
public class FastRenderMod {

    public static final String MODID = "fastrender";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FastRenderMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        // Registramos el bus de eventos de Forge (incluye ChunkLoadingHandler)
        MinecraftForge.EVENT_BUS.register(this);

        // Config (client + opciones de Fase 2)
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);

        LOGGER.info("FastRender cargado | Fase 1 + Fase 2 iniciada");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("========================================");
        LOGGER.info("FastRender - Configuración actual:");
        LOGGER.info("  Render Distance objetivo : {} chunks", Config.maxRenderDistance.get());
        LOGGER.info("  Carga agresiva activada  : {}", Config.enableAggressiveChunkLoading.get());
        LOGGER.info("  Perfil de rendimiento    : {}", Config.performanceProfile.get().toUpperCase());
        LOGGER.info("  Multiplicador efectivo   : x{}", Config.getEffectiveChunksPerTickMultiplier());
        LOGGER.info("  Buffer extra de chunks   : {}", Config.getEffectiveExtraBuffer());
        LOGGER.info("========================================");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                try {
                    Minecraft mc = Minecraft.getInstance();
                    if (mc == null || mc.options == null) {
                        LOGGER.warn("FastRender: No se pudo acceder a las opciones del cliente aún.");
                        return;
                    }

                    Options options = mc.options;
                    int target = Config.maxRenderDistance.get();
                    int current = options.renderDistance().get();

                    if (current < target) {
                        options.renderDistance().set(target);
                        options.save();
                        LOGGER.info("FastRender: Distancia de render aumentada de {} → {} chunks", current, target);
                    } else {
                        LOGGER.info("FastRender: Distancia de render ya es suficiente ({} ≥ {})", current, target);
                    }

                } catch (Exception e) {
                    LOGGER.error("FastRender: Error al intentar cambiar la distancia de render", e);
                }
            });
        }
    }
}
