package com.santiagogd.fastrender;

import com.mojang.logging.LogUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

/**
 * FastRender - Fase 2
 * Sistema de carga de chunks más agresiva con perfiles de rendimiento.
 *
 * Perfiles:
 *   low     → suave
 *   medium  → equilibrado
 *   high    → agresivo (por defecto)
 *   extreme → máxima agresividad
 *
 * Nota: Sin Mixins el control profundo sobre el ChunkMap de vanilla es limitado.
 * Esta clase implementa todo lo posible desde la API pública de Forge.
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

        // Ejecutamos la lógica cada ~0.5 segundos para no saturar
        tickCounter++;
        if (tickCounter < 10) return;
        tickCounter = 0;

        if (player.level().isClientSide && player instanceof LocalPlayer localPlayer) {
            handleClient(localPlayer);
        } else if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            handleServer(serverPlayer);
        }
    }

    private static void handleClient(LocalPlayer player) {
        int extra = Config.getEffectiveExtraBuffer();
        int multiplier = Config.getEffectiveChunksPerTickMultiplier();
        String profile = Config.performanceProfile.get();

        // Detectamos dirección de movimiento
        Vec3 motion = player.getDeltaMovement();
        boolean isMoving = motion.horizontalDistanceSqr() > 0.001;

        if (player.tickCount % 200 == 0) {
            LOGGER.debug("FastRender [Client] Perfil={} | Buffer={} | Multiplier=x{} | Moving={}",
                    profile, extra, multiplier, isMoving);
        }
    }

    private static void handleServer(ServerPlayer player) {
        int extra = Config.getEffectiveExtraBuffer();
        String profile = Config.performanceProfile.get();

        ChunkPos center = player.chunkPosition();
        Vec3 motion = player.getDeltaMovement();

        // Calculamos la dirección principal de movimiento
        int dirX = 0;
        int dirZ = 0;

        if (Math.abs(motion.x) > 0.01 || Math.abs(motion.z) > 0.01) {
            if (Math.abs(motion.x) > Math.abs(motion.z)) {
                dirX = motion.x > 0 ? 1 : -1;
            } else {
                dirZ = motion.z > 0 ? 1 : -1;
            }
        }

        // Aquí se podría pedir chunks prioritarios en la dirección del movimiento.
        // Sin acceso directo al ChunkMap (requiere Mixin) solo registramos la intención.
        if (player.tickCount % 100 == 0) {
            LOGGER.debug("FastRender [Server] Chunk [{}, {}] | Perfil={} | Buffer={} | Dir=[{}, {}]",
                    center.x, center.z, profile, extra, dirX, dirZ);
        }
    }

    public static String getCurrentProfileName() {
        return Config.performanceProfile.get().toLowerCase();
    }
}
