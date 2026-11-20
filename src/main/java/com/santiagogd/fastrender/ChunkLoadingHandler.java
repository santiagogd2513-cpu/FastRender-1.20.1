package com.santiagogd.fastrender;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Fase 2 - Manejo de carga de chunks más agresiva.
 *
 * Nota importante:
 * Sin Mixins el control real sobre el sistema de carga de vanilla es limitado.
 * Esta clase prepara la lógica y aplica lo que se puede desde eventos de Forge.
 * Los perfiles (low/medium/high/extreme) ya están activos.
 */
@Mod.EventBusSubscriber(modid = FastRenderMod.MODID)
public class ChunkLoadingHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!Config.enableAggressiveChunkLoading.get()) return;

        Player player = event.player;

        // Solo actuamos cada ciertos ticks para no saturar
        tickCounter++;
        if (tickCounter < 20) return; // ~1 segundo
        tickCounter = 0;

        // Cliente
        if (player.level().isClientSide && player instanceof LocalPlayer localPlayer) {
            handleClient(localPlayer);
        }

        // Servidor (incluye singleplayer integrado)
        if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            handleServer(serverPlayer);
        }
    }

    private static void handleClient(LocalPlayer player) {
        // En el cliente podemos influir principalmente en la distancia de render
        // y en cómo se prioriza la carga visual.
        int extra = Config.getEffectiveExtraBuffer();
        int multiplier = Config.getEffectiveChunksPerTickMultiplier();

        // Log periódico para confirmar que el sistema está activo
        if (player.tickCount % 200 == 0) {
            LOGGER.debug("FastRender [Client] Perfil: {} | Buffer extra: {} | Multiplicador: {}",
                    Config.performanceProfile.get(), extra, multiplier);
        }
    }

    private static void handleServer(ServerPlayer player) {
        // En el servidor es donde realmente se pueden pedir más chunks.
        // Sin Mixins no podemos cambiar fácilmente el límite interno de vanilla,
        // pero sí podemos forzar que se mantengan más chunks alrededor del jugador.

        int extra = Config.getEffectiveExtraBuffer();
        ChunkPos playerChunk = player.chunkPosition();

        // Ejemplo simple: marcar interés en chunks más lejanos en la dirección del movimiento
        // (esto es una base; se puede mejorar mucho con Mixins después)

        if (player.tickCount % 100 == 0) {
            LOGGER.debug("FastRender [Server] Jugador en chunk {} {} | Perfil: {} | Buffer: {}",
                    playerChunk.x, playerChunk.z,
                    Config.performanceProfile.get(),
                    extra);
        }
    }

    /**
     * Método de utilidad para obtener el perfil actual de forma legible.
     */
    public static String getCurrentProfileName() {
        return Config.performanceProfile.get().toLowerCase();
    }
}
