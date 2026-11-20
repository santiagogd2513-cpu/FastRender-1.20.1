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
 * FastRender - Fase 1
 * Objetivo principal: permitir y forzar distancias de render más altas (hasta 128).
 */
@Mod(FastRenderMod.MODID)
public class FastRenderMod {

    public static final String MODID = "fastrender";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FastRenderMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Setup común
        modEventBus.addListener(this::commonSetup);

        // Registramos el bus de eventos de Forge
        MinecraftForge.EVENT_BUS.register(this);

        // Config client-side (render distance + flags experimentales)
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);

        LOGGER.info("FastRender cargado - Fase 1 (Render Distance)");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("FastRender | Target render distance: {} chunks", Config.maxRenderDistance.get());
        LOGGER.info("FastRender | Aggressive chunk loading (experimental): {}", Config.enableAggressiveChunkLoading.get());
    }

    /**
     * Cliente: forzamos la distancia de render cuando el juego termina de cargar el cliente.
     */
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // enqueueWork asegura que se ejecute en el hilo principal del cliente
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
